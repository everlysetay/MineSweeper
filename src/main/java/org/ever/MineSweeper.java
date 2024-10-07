package main.java.org.ever;

import java.util.Scanner;

public class MineSweeper {
	public static int populateNumOfMines(Scanner scanner, int size) {
		System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
		if (scanner.hasNextInt()) {
			try {
				int numOfMines = scanner.nextInt();
				int totalSquares = size * size;
				if (numOfMines > totalSquares) {
					System.out.println("Numbers of mine entered more than number of grids available");
					populateNumOfMines(scanner, size);
				} else if (numOfMines > 0.35 * totalSquares) {
					System.out.println("Integer inputted exceeded 35% of total play area");
					populateNumOfMines(scanner, size);
				} else if (numOfMines < 1) {
					System.out.println("Requires a postive number");
					populateNumOfMines(scanner, size);
				}else {
					return numOfMines;
				}
			} catch (Exception e) {
				System.out.println("Problem encounterd when parsing int");
				populateNumOfMines(scanner, size);
			}
		} else {
			scanner.nextLine();
			System.out.println("Please type an integer");
			populateNumOfMines(scanner, size);
		}
		return 3; //set default = 3
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
		int totalMines = populateNumOfMines(scanner,size);

        Board game = new Board(size, totalMines);

        while (!game.isGameOver() && !game.isWin()) {
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

        game.printBoard();
        
        if (game.isGameOver())
        	System.out.println("Oh no, you detonated a mine! Game over.");
        if (game.isWin())
        	System.out.println("Congratulations, you have won the game!");
        
        scanner.close();
    }
}
