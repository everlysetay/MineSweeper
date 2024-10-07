package main.java.org.ever;

import java.util.HashMap;
import java.util.Random;

public class Board {

	private int size;
	private int totalMines;
	private boolean gameOver;
	private int totalRevealed;
	private boolean win;
	public HashMap<String, Field> mineField = new HashMap<>();
	
	public Board(int size, int totalMines) {
		this.size = size;
		this.totalMines = totalMines;
		this.gameOver = false;
		this.win = false;
		this.totalRevealed = 0;
		
		initializeBoard();
	}
	
	private void initializeBoard() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
            	String name = (char) ('A' + r)+Integer.toString(c+1);
            	Field field = new Field(name);
                mineField.put(name, field);
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

            Field field = mineField.get((char) ('A' + r)+Integer.toString(c+1));
            if (!field.getValue().equals("*")) {
            	field.setMine(true);
                field.setValue("*"); // Place mine
                minesPlaced++;
                System.out.println(field.getName());
            }
        }
    }
	
	public void reveal(int r, int c) {
		Field field = mineField.get((char) ('A' + r)+Integer.toString(c+1));
		if (this.gameOver || field.isRevealed() || field.isFlagged()) {
			return;
		}
	    field.setRevealed(true);
	    this.totalRevealed++;
	    
	    if (field.isMine()) {
	    	this.gameOver = true; // Player hit a mine 
	        return;
	    }
	    
	    if (this.totalRevealed + this.totalMines == size*size) {
	    	this.win = true;
	    	return;
	    }

	    // If the cell is empty, reveal adjacent cells
	    if (field.getValue().equals("0")) {
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

	public void flag(int r, int c) {
		Field field = mineField.get((char) ('A' + r)+Integer.toString(c+1));
		
        if (!field.isRevealed()) {
        	field.setFlagged(!field.isFlagged());
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
            	Field field = mineField.get((char) ('A' + r)+Integer.toString(c+1));
                if (field.isFlagged()) {
                    System.out.print("F ");
                } else if (!field.isRevealed()) {
                    System.out.print("_ ");
                } else {
                    System.out.print(field.getValue() + " ");
                }
            }
            System.out.println();
        }
    }
	
	private void calculateNumbers() {
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Field field = mineField.get((char) ('A' + r)+Integer.toString(c+1));
                if (field.getValue().equals("*")) {
                    continue; // Skip mines
                }
                int mineCount = countAdjacentMines(r, c);
                field.setValue(Integer.toString(mineCount));
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

                if (isInBounds(newRow, newCol) && mineField.get((char) ('A' + newRow)+Integer.toString(newCol+1)).getValue().equals("*")) {
                    count++;
                }
            }
        }
        return count;
    }
	
	public boolean isInBounds(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size;
    }
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotalMines() {
		return totalMines;
	}
	public void setTotalMines(int totalMines) {
		this.totalMines = totalMines;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public boolean isWin() {
		return win;
	}
}
