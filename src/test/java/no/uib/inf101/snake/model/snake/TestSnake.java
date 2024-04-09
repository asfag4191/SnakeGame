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
    public void testMoveIncreasesLengthWhenEatingAnApple() {
        CellPosition startPos = new CellPosition(2, 4);
        Snake snake = new Snake('S', startPos);
        CellPosition applePosition = new CellPosition(1, 4); 
        assertEquals(1, snake.getLength(), "Initial snake length should be 1");
        
        // Simuler at snake "spiser" et eple og vokser
        snake.grow(applePosition);
        assertEquals(2, snake.getLength(), "Snake length should increase after eating an apple");
    }
}       
