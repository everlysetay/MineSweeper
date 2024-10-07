package test.java.org.ever;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.org.ever.Board;
import main.java.org.ever.Field;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(4, 2); // 4x4 board with 2 mines
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(4, board.getSize());
        assertEquals(2, board.getTotalMines());
        assertFalse(board.isGameOver());
        assertFalse(board.isWin());
    }

    @Test
    public void testRevealMine() {
        // Manually set a mine for testing purposes
        Field mineField = board.mineField.get("A1");
        mineField.setMine(true);
        mineField.setValue("*");

        board.reveal(0, 0); // Reveal the mine
        assertTrue(board.isGameOver());
    }

    @Test
    public void testRevealSafeCell() {
        // Reveal a safe cell and check if the game is not over
        board.reveal(1, 1);
        assertFalse(board.isGameOver());
        // Verify if the field is revealed
        assertTrue(board.mineField.get("B2").isRevealed());
    }

    @Test
    public void testFlaggingCell() {
        board.flag(0, 0); // Flag A1
        assertTrue(board.mineField.get("A1").isFlagged());

        board.flag(0, 0); // Unflag A1
        assertFalse(board.mineField.get("A1").isFlagged());
    }

    @Test
    public void testCountAdjacentMines() {
        // Manually set mines to test the adjacent mine count
        board.mineField.get("A1").setMine(true);
        board.mineField.get("A1").setValue("*");
        board.mineField.get("A2").setMine(true);
        board.mineField.get("A2").setValue("*");

        assertEquals(2, board.countAdjacentMines(1, 1)); // B2
    }
}

