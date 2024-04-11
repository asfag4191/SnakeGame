package no.uib.inf101.snake.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;
import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;
import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.SnakeModel;
import no.uib.inf101.snake.model.Object.Item;
import no.uib.inf101.snake.snake.Snake;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class TestSnakeModel {

    private SnakeBoard snakeBoard;
    private SnakeModel snakeModel;


    @Test
    void testMoveReturnsTrueWhenSuccessful() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(10, 10)); 
        snakeModel = new SnakeModel(snakeBoard, snake);
    
        snakeModel.moveSnake(Direction.SOUTH);
        assertEquals('S', snakeBoard.get(new CellPosition(11, 10)), "Snake head should be at new position");

        snakeModel.moveSnake(Direction.EAST);
        assertEquals('S', snakeBoard.get(new CellPosition(11, 11)), "Snake head should be at new position");

        snakeModel.moveSnake(Direction.NORTH);
        assertEquals('S', snakeBoard.get(new CellPosition(10, 11)), "Snake head should be at new position");

        snakeModel.moveSnake(Direction.WEST);
        assertEquals('S', snakeBoard.get(new CellPosition(10, 10)), "Snake head should be at new position");
    }

    @Test
    void testIllegalMoveEndsGame() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(0, 0));
        snakeModel = new SnakeModel(snakeBoard, snake);
    
        snakeModel.moveSnake(Direction.NORTH); //moves the snake out of the board(upwards)
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after illegal move");

        snakeModel.resetGame();
        snake = new Snake('S', new CellPosition(0, 0));
        snakeModel = new SnakeModel(snakeBoard, snake);
        snakeModel.moveSnake(Direction.WEST); //moves the snake out of the board(to the left)
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after illegal move");

    }
    
    @Test
    void testMoveIntoSelfEndsGame() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(0, 0));
        snakeModel = new SnakeModel(snakeBoard, snake);
    
        //set two snake cells
        snakeBoard.set(new CellPosition(10, 10), 'S');
        snakeBoard.set(new CellPosition(10, 9), 'S');
    
        snakeModel.setDirection(Direction.NORTH); //Into itself
        //snakeModel.clockTickDelay(); //game updates.
    
        // Check if the game state is GAME_OVER after the move.
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after moving into itself");
    }
    @Test
    public void testGenerateApple() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(0, 0));
        snakeModel = new SnakeModel(snakeBoard, snake);
        
        //cleans the board for apples, before placing a new apple
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'A') {
                    snakeBoard.set(new CellPosition(row, col), '-');
                }
            }
        }
        snakeModel.GenerateApple('A');

        //goes thru all the cells and counts the apples
        int appleCount = 0;
        CellPosition applePosition = null;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'A') {
                    appleCount++;
                    applePosition = new CellPosition(row, col);
                }
            }
        }
        
        assertEquals(1, appleCount, "There should be exactly one apple on the board");
        assertNotNull(applePosition, "The apple has a position");
        assertTrue(applePosition.row() >= 0 && applePosition.row() < snakeBoard.rows(),"The apple is within the board");
        assertTrue(applePosition.col() >= 0 && applePosition.col() < snakeBoard.cols(),"The apple is within the board");
    }

    @Test
public void testApplePlacedOnEmptyTile() {
    SnakeBoard snakeBoard = new SnakeBoard(20, 20);
    Snake snake = new Snake('S', new CellPosition(0, 0));
    SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
    
    // Plasser slangen på brettet
    snakeBoard.set(new CellPosition(0, 0), 'S');
    
    // Generer eplet
    snakeModel.GenerateApple('A');
    
    // Hent posisjonen til eplet fra snakeModel
    CellPosition applePosition = snakeModel.getApplePosition();

    // Sjekk at eplet ikke er plassert på samme posisjon som slangen
    char tileContent = snakeBoard.get(applePosition);
    assertNotEquals( 'S', tileContent);
}

}
    
    

