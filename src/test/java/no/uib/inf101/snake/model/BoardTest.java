package no.uib.inf101.snake.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testConstructor() {
        // Test that a new board is created with the correct number of rows and columns
        int rows = 5;
        int cols = 10;
        SnakeBoard snakeBoard = new SnakeBoard(rows, cols);
        assertEquals(rows, snakeBoard.rows());
        assertEquals(cols, snakeBoard.cols());
    }

}
