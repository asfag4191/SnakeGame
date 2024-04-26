package no.uib.inf101.snake.model.Object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.object.Item;

/**
 * This class tests the functionality of the Item class within the snake game model.
 * It ensures that items can be randomly generated on a board, placed, and removed correctly.
 */
public class TestItem {

    @Test
    public void testGenerateRandomPosition() {
        SnakeBoard snakeBoard = new SnakeBoard(10, 10);
        snakeBoard.set(new CellPosition(0, 0), 'S'); // Snake
        snakeBoard.set(new CellPosition(0, 1), 'A'); // Apple
        snakeBoard.set(new CellPosition(1, 0), 'P'); // Poison Apple
        snakeBoard.set(new CellPosition(2, 2), 'O'); // Obstacle

        Item itemA = new Item('A');
        for (int i = 0; i < 100; i++) { // Test multiple times to ensure randomness
            CellPosition randomPosition = itemA.generateRandomPosition(snakeBoard);
            
            assertTrue(randomPosition.row() >= 0 && randomPosition.row() < snakeBoard.rows(), "Row is within bounds.");
            assertTrue(randomPosition.col() >= 0 && randomPosition.col() < snakeBoard.cols(), "Column is within bounds.");
            assertEquals('-', snakeBoard.get(randomPosition), "Random position is on an empty cell.");
            assertFalse(randomPosition.equals(new CellPosition(0, 0)), "Random position is not the same as the snake's position.");
        }

        Item itemO = new Item('O');
        for (int i = 0; i < 100; i++) {
            CellPosition randomPosition = itemO.generateRandomPosition(snakeBoard);

            assertTrue(randomPosition.row() >= 0 && randomPosition.row() < snakeBoard.rows(), "Row is within bounds.");
            assertTrue(randomPosition.col() >= 0 && randomPosition.col() < snakeBoard.cols(), "Column is within bounds.");
            assertEquals('-', snakeBoard.get(randomPosition), "Random position is on an empty cell.");
            assertFalse(randomPosition.equals(new CellPosition(0, 0)), "Random position is not the same as the snake's position or the apple.");
        }

        Item itemP = new Item('P');
        for (int i = 0; i < 100; i++) {
            CellPosition randomPosition = itemP.generateRandomPosition(snakeBoard);

            assertTrue(randomPosition.row() >= 0 && randomPosition.row() < snakeBoard.rows(), "Row is within bounds.");
            assertTrue(randomPosition.col() >= 0 && randomPosition.col() < snakeBoard.cols(), "Column is within bounds.");
            assertEquals('-', snakeBoard.get(randomPosition), "Random position is on an empty cell.");
            assertFalse(randomPosition.equals(new CellPosition(0, 0)), "Random position is not the same as the snake's position or the apple.");
        }
    }

    @Test
    public void testPlaceOnBoard() {
        SnakeBoard snakeBoard = new SnakeBoard(10, 10);
        snakeBoard.clearBoard(); 

        char itemChar = 'A';
        Item item = new Item(itemChar);
        CellPosition positionToPlace = item.generateRandomPosition(snakeBoard);
        item.placeOnBoard(snakeBoard, itemChar);

        assertEquals(itemChar, snakeBoard.get(positionToPlace), "The board should have the item character at the specified position.");
    }

    @Test
    public void testRemoveItem() {
        SnakeBoard snakeBoard = new SnakeBoard(3, 3);
        snakeBoard.set(new CellPosition(0, 0), 'P');
        snakeBoard.set(new CellPosition(0, 1), 'O');
        snakeBoard.set(new CellPosition(1, 0), 'P');
        snakeBoard.set(new CellPosition(2, 2), 'O');

        Item item = new Item('A');
        item.removeItem(snakeBoard, 'O'); 

        assertEquals('-', snakeBoard.get(new CellPosition(0, 1)), "Cell should be empty after removing the item.");
        assertEquals('-', snakeBoard.get(new CellPosition(2, 2)), "Cell should be empty after removing the item.");
        assertEquals('P', snakeBoard.get(new CellPosition(0, 0)), "Other cells should remain unchanged.");
        assertEquals('P', snakeBoard.get(new CellPosition(1, 0)), "Other cells should remain unchanged.");

        item.removeItem(snakeBoard, 'P');
        assertEquals('-', snakeBoard.get(new CellPosition(0, 0)), "Cell should be empty after removing the item.");
        assertEquals('-', snakeBoard.get(new CellPosition(1, 0)), "Cell should be empty after removing the item.");
    }
}