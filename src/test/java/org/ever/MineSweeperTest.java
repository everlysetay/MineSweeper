package test.java.org.ever;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.org.ever.MineSweeper;

import static org.junit.jupiter.api.Assertions.*;

class MineSweeperTest {
    private MineSweeper game;

    private MineSweeper mineSweeper;

    @BeforeEach
    void setUp() {
        mineSweeper = new MineSweeper(4, 3); // 4x4 grid with 3 mines
    }

    @Test
    void testInitialization() {
        assertEquals(4, mineSweeper.getBoard().length);
        for (char[] row : mineSweeper.getBoard()) {
            for (char cell : row) {
                assertEquals('0', cell); // Ensure all cells are initialized to '0'
            }
        }
    }

    @Test
    void testMinePlacement() {
        int mineCount = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (mineSweeper.getBoard()[r][c] == '*') {
                    mineCount++;
                }
            }
        }
        assertEquals(3, mineCount); // Ensure exactly 3 mines are placed
    }

    @Test
    void testRevealMine() {
        mineSweeper.reveal(0, 0); // Try to reveal a cell
        assertTrue(mineSweeper.isGameOver()); // The game should be over if a mine is revealed
    }

    @Test
    void testFlagging() {
        mineSweeper.flag(1, 1); // Flag a cell
        assertTrue(mineSweeper.isFlagged(1, 1)); // Ensure the cell is flagged

        mineSweeper.flag(1, 1); // Unflag the same cell
        assertFalse(mineSweeper.isFlagged(1, 1)); // Ensure the cell is unflagged
    }

    @Test
    void testCountAdjacentMines() {
        // Assuming there's a mine at (0, 0) and (0, 1), 
        // (1, 1) should have 2 adjacent mines
        mineSweeper.flag(0, 0);
        mineSweeper.flag(0, 1);
        
        assertEquals(2, mineSweeper.countAdjacentMines(1, 1));
    }

    @Test
    void testRevealEmptyCell() {
        // Before revealing, ensure the cell is not revealed
        assertFalse(mineSweeper.isRevealed(2, 2));
        
        // Reveal a cell that does not contain a mine
        mineSweeper.reveal(2, 2);
        
        // The cell should now be revealed
        assertTrue(mineSweeper.isRevealed(2, 2));
    }

    @Test
    void testGameOverState() {
        mineSweeper.reveal(0, 0); // Try revealing a mine
        assertTrue(mineSweeper.isGameOver()); // Check if the game is over
    }
}

