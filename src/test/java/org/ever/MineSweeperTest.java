package test.java.org.ever;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import main.java.org.ever.MineSweeper;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//Require to refactor of the MineSweeper Class to test input function
public class MineSweeperTest {

    private Scanner mockScanner;

    @BeforeEach
    public void setUp() {
        mockScanner = Mockito.mock(Scanner.class);
    }

    @Test
    public void testPopulateGrid_ValidInput() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(4);

        int result = MineSweeper.populateGrid(mockScanner);
        assertEquals(4, result);
    }

    @Test
    public void testPopulateGrid_NegativeInput() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(-1);
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(5);

        int result = MineSweeper.populateGrid(mockScanner);
        assertEquals(5, result); // Should return valid input after retry
    }

    @Test
    public void testPopulateGrid_NonIntegerInput() {
        when(mockScanner.hasNextInt()).thenReturn(false);
        when(mockScanner.nextLine()).thenReturn("not a number");
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(3);

        int result = MineSweeper.populateGrid(mockScanner);
        assertEquals(3, result); // Should return the valid input after retry
    }

    @Test
    public void testPopulateNumOfMines_ValidInput() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(5);

        int result = MineSweeper.populateNumOfMines(mockScanner, 4);
        assertEquals(5, result);
    }

    @Test
    public void testPopulateNumOfMines_TooManyMines() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(20); // More than 16 for a 4x4 grid
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(5);

        int result = MineSweeper.populateNumOfMines(mockScanner, 4);
        assertEquals(5, result); // Should return valid input after retry
    }

    @Test
    public void testPopulateNumOfMines_Exceeding35Percent() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(10); // More than 5.6 for a 4x4 grid
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(3); // Valid input

        int result = MineSweeper.populateNumOfMines(mockScanner, 4);
        assertEquals(3, result); // Should return valid input after retry
    }

    @Test
    public void testPopulateNumOfMines_NegativeInput() {
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(-1); // Negative input
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(2); // Valid input

        int result = MineSweeper.populateNumOfMines(mockScanner, 4);
        assertEquals(2, result); // Should return valid input after retry
    }
}


