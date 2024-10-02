package main.java.org.ever;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
	private final int size;
    private final int totalMines;
    private final char[][] board;
    private final boolean[][] revealed;
    private final boolean[][] flagged;
    private boolean gameOver;

    public MineSweeper(int size, int totalMines) {
        this.size = size;
        this.totalMines = totalMines;
        this.board = new char[size][size];
        this.revealed = new boolean[size][size];
        this.flagged = new boolean[size][size];
        this.gameOver = false;

        initializeBoard();
    }

    private void initializeBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = '0'; // Initialize cells with '0'
            }
        }

        placeMines();
        calculateNumbers();
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int r = random.nextInt(size);
            int c = random.nextInt(size);

            if (board[r][c] != '*') {
                board[r][c] = '*'; // Place mine
                minesPlaced++;
            }
        }
    }

    private void calculateNumbers() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board[r][c] == '*') {
                    continue; // Skip mines
                }
                int mineCount = countAdjacentMines(r, c);
                board[r][c] = (char) ('0' + mineCount); // Convert to char
            }
        }
    }

    public int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int newRow = r + i;
                int newCol = c + j;

                if (isInBounds(newRow, newCol) && board[newRow][newCol] == '*') {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }

    public void reveal(int r, int c) {
        if (gameOver || revealed[r][c] || flagged[r][c]) {
            return;
        }
        revealed[r][c] = true;

        if (board[r][c] == '*') {
            gameOver = true; // Player hit a mine
            return;
        }

        // If the cell is empty, reveal adjacent cells
        if (board[r][c] == '0') {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = r + i;
                    int newCol = c + j;
                    if (isInBounds(newRow, newCol)) {
                        reveal(newRow, newCol);
                    }
                }
            }
        }
    }
    
    public void printBoard() {
        System.out.print("  ");
        for (int c = 0; c < size; c++) {
            System.out.print((c+1) + " ");
        }
        System.out.println();
        for (int r = 0; r < size; r++) {
            System.out.print((char) ('A' + r) + " ");
            for (int c = 0; c < size; c++) {
                if (flagged[r][c]) {
                    System.out.print("F ");
                } else if (!revealed[r][c]) {
                    System.out.print("_ ");
                } else {
                    System.out.print(board[r][c] + " ");
                }
            }
            System.out.println();
        }
    }

    public void flag(int r, int c) {
        if (!revealed[r][c]) {
            flagged[r][c] = !flagged[r][c];
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isRevealed(int r, int c) {
        return revealed[r][c];
    }

    public boolean isFlagged(int r, int c) {
        return flagged[r][c];
    } 
    
    public boolean isGameOver() {
        return gameOver;
    }

    public static int populateGrid(Scanner scanner) {
		System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
		if (scanner.hasNextInt()) {
			try {
				int size = scanner.nextInt();
				if (size > 1)
					return size;
				else {
					System.out.println("Requires a postive number");
					populateGrid(scanner);
				}
			} catch (Exception e) {
				System.out.println("Problem encounterd when parsing int");
				populateGrid(scanner);
			}
		} else {
			scanner.nextLine();
			System.out.println("Please type an integer");
			populateGrid(scanner);
		}
		return 4; //default to 4
	}
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = populateGrid(scanner);
		System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
		int totalMines = scanner.nextInt();

        MineSweeper game = new MineSweeper(size, totalMines);

        while (!game.isGameOver()) {
            game.printBoard();
            System.out.print("Select a square to reveal (e.g. A1) or flag (e.g. fA1): ");
            String input = scanner.next();

            boolean isFlag = false;
            
            if (input.charAt(0) == 'f') {
            	input = input.replace("f", "");
            	isFlag = true;
            } 
            
            if (input.length() < 2) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            char rowChar = input.charAt(0);
            int r = rowChar - 'A'; // Convert 'A' to index 0
            int c = Integer.parseInt(input.substring(1)) - 1; // Convert to 0-based index

            if (!game.isInBounds(r, c)) {
                System.out.println("Coordinates out of bounds. Please try again.");
                continue;
            }

            System.out.println("This square contains " + game.countAdjacentMines(r, c) + " adjacent mines.");

            if (isFlag) {
                // Flagging
                game.flag(r, c);
            } else {
                // Revealing
                game.reveal(r, c);
            }
        }

        System.out.println("Oh no, you detonated a mine! Game over.");
        game.printBoard();
        scanner.close();
    }
}
