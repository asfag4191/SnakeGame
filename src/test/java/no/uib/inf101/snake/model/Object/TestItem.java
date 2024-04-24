package no.uib.inf101.snake.model.Object;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import no.uib.inf101.grid.CellPosition;

import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.object.Item;

public class TestItem{

@Test
public void testGenerateRandomPosition() {
    // Setup
    SnakeBoard snakeBoard = new SnakeBoard(10, 10);
    // Pre-occupy some positions with different items
    snakeBoard.set(new CellPosition(0, 0), 'S'); // Snake
    snakeBoard.set(new CellPosition(0, 1), 'A'); // Apple
    snakeBoard.set(new CellPosition(1, 0), 'P'); // Poison Apple
    snakeBoard.set(new CellPosition(2, 2), 'O'); // Obstacle

    // Tests the generateRandomPosition multiple times 
    Item itemA = new Item('A');
    for (int i = 0; i < 100; i++) { // Test multiple times to ensure randomness
        CellPosition randomPosition = itemA.generateRandomPosition(snakeBoard);

        // Asserts
        assertTrue(randomPosition.row() >= 0 && randomPosition.row() < snakeBoard.rows(),
                "Row is within bounds.");
        assertTrue(randomPosition.col() >= 0 && randomPosition.col() < snakeBoard.cols(),
                "Column is within bounds.");
        assertEquals('-', snakeBoard.get(randomPosition),
                "Random position is on an empty cell.");

        assertFalse(randomPosition.equals(new CellPosition(0, 0)),
                "Random position is not the same as the snake's position."); 
    }
}

@Test
public void testPlaceOnBoard() {
    // Setup
    SnakeBoard snakeBoard = new SnakeBoard(10, 10);
    snakeBoard.clearBoard(); //the board is empty, fillled with ('-')
    
    char itemChar = 'A'; 
    Item item = new Item(itemChar);

    // Execute
    CellPosition positionToPlace = item.generateRandomPosition(snakeBoard); 
    item.placeOnBoard(snakeBoard, itemChar); 

    // Verify
    assertEquals(itemChar, snakeBoard.get(positionToPlace),
            "The board should have the item character at the specified position.");
}

@Test
public void testRemoveItem() {
    // Setup
    SnakeBoard snakeBoard = new SnakeBoard(3, 3);
    snakeBoard.set(new CellPosition(0, 0), 'P');
    snakeBoard.set(new CellPosition(0, 1), 'O');
    snakeBoard.set(new CellPosition(1, 0), 'P');
    snakeBoard.set(new CellPosition(2, 2), 'O');

    // Create an instance of Item
    Item item = new Item('A'); 

    // Execute the method to remove 'O' from the board
    item.removeItem(snakeBoard, 'O');

    // Verify that 'O' has been replaced with '-'
    assertEquals('-', snakeBoard.get(new CellPosition(0, 1)));
    assertEquals('-', snakeBoard.get(new CellPosition(2, 2)));

    // Verify that other cells remain unchanged
    assertEquals('P', snakeBoard.get(new CellPosition(0, 0)));
    assertEquals('P', snakeBoard.get(new CellPosition(1, 0)));

    item.removeItem(snakeBoard, 'P');

    assertEquals('-', snakeBoard.get(new CellPosition(0, 0)));
assertEquals('-', snakeBoard.get(new CellPosition(1, 0)));
}
}
    
    

