package no.uib.inf101.snake.model.snake;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.snake.Snake;

import static org.junit.jupiter.api.Assertions.*;

public class TestSnake {

    @Test
    public void testSnakeInitialization() {
        CellPosition startPos = new CellPosition(5, 5);
        Snake snake = new Snake('S', startPos);
        assertEquals(1, snake.getLength(), "Initial snake length should be 1");
        assertEquals(startPos, snake.getHeadPos(), "Snake head position should match the initial position");
    }

    @Test
public void testSnakeMovesInDirection() {
    Snake snake = new Snake('S', new CellPosition(5, 5));
    Direction direction = Direction.EAST;
    
    snake.move(direction);
    
    // Check that the snake's head position has been updated correctly
    CellPosition newHeadPos = snake.getHeadPos();
    assertEquals(6, newHeadPos.col(), "Column should be updated moving to the right");
    assertEquals(5,newHeadPos.row(), "Row should be unchanged");

    }

}
      
