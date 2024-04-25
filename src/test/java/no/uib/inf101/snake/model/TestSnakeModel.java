package no.uib.inf101.snake.model;

import org.junit.jupiter.api.Test;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.model.object.Item;

import no.uib.inf101.snake.snake.Snake;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        snakeModel.moveSnake(Direction.NORTH); // moves the snake out of the board(upwards)
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after illegal move");

        snakeModel.resetGame();
        snake = new Snake('S', new CellPosition(0, 0));
        snakeModel = new SnakeModel(snakeBoard, snake);
        snakeModel.moveSnake(Direction.WEST); // moves the snake out of the board(to the left)
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after illegal move");

    }

    @Test
    void testSetDirection() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(5, 5));
        snakeModel = new SnakeModel(snakeBoard, snake);

        snakeModel.setDirection(Direction.NORTH);
        snakeModel.moveSnake(Direction.NORTH);
        CellPosition newHeadPos = snake.getHeadPos();
        assertEquals(4, newHeadPos.row(), "Row should be updated to moving upwards by one row.");

        snakeModel.setDirection(Direction.NORTH);
        snakeModel.moveSnake(Direction.SOUTH);
        assertEquals(4, newHeadPos.row(), "The snake can't change direction.");
    }

    @Test
    void testMoveIntoSelfEndsGame() {
        snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(5, 5));
        snakeModel = new SnakeModel(snakeBoard, snake);

        snakeBoard.set(new CellPosition(6, 5), 'S');
        snakeModel.moveSnake(Direction.SOUTH); // moves the snake into one of their own cell
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after moving into itself");
    }

    @Test
    public void testMoveEatingApple() {
        Snake snake = new Snake('S', new CellPosition(2, 4));
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        CellPosition applePosition = new CellPosition(1, 4);

        snakeBoard.set(applePosition, 'A');

        assertEquals(1, snake.getLength(), "Initial snake length should be 1");
        snakeModel.setDirection(Direction.NORTH);
        snakeModel.moveSnake(Direction.NORTH); // Simulate the snake moving north to eat the apple

        assertEquals(applePosition, snake.getHeadPos(), "Snake should move to the apple position");
        assertEquals(2, snake.getLength(), "Snake length should increase after eating an apple");

        assertEquals('S', snakeBoard.get(applePosition), "Original apple position should be cleared");

        // Verify that a new apple is generated and it's the only one on the board
        int appleCount = 0;
        CellPosition newApplePosition = null;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'A') {
                    appleCount++;
                    newApplePosition = new CellPosition(row, col);
                }
            }
        }

        assertEquals(1, appleCount, "There should be exactly one apple on the board");
        assertNotNull(newApplePosition, "A new apple should be generated");
        assertNotEquals(applePosition, newApplePosition, "New apple should be at a different position");

        int score = snakeModel.getscore();
        assertEquals(10, score, "The score should increase by 10 after eating an apple.");
    }

    @Test
    public void testMoveEatingAnPoisinousApple() {
        Snake snake = new Snake('S', new CellPosition(2, 4));
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        CellPosition applePposition = new CellPosition(1, 4);

        assertEquals(1, snake.getLength(), "Initial snake length should be 1");

        snakeBoard.set(applePposition, 'P');

        snakeModel.setDirection(Direction.NORTH);
        snakeModel.moveSnake(Direction.NORTH);

        assertEquals(applePposition, snake.getHeadPos(),
                "Snake should move to the apple position");
        assertEquals(1, snake.getLength(),
                "Snake length should increase after eating an apple");

        assertEquals('S', snakeBoard.get(applePposition), "Original poisinous apple position should be cleared");

        // Verify that a new apple is generated and it's the only one on the board
        int applePCount = 0;
        CellPosition newApplePosition = null;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'P') {
                    applePCount++;
                    newApplePosition = new CellPosition(row, col);
                }
            }
        }

        assertEquals(1, applePCount, "There should be exactly one apple on the board");
        assertNotNull(newApplePosition, "A new apple should be generated");
        assertNotEquals(applePposition, newApplePosition, "New apple should be at a different position");

        int score = snakeModel.getscore();
        assertEquals(-10, score, "The score should decrease by 10 after eating an poisinous apple.");
    }

    @Test
    public void testMoveIntoObjectEndsGame() {
        SnakeBoard snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(5, 5));
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);

        snakeBoard.set(new CellPosition(6, 5), 'O');
        snakeModel.moveSnake(Direction.SOUTH); // moves the snake into an obstacle
        assertEquals(GameState.GAME_OVER, snakeModel.getGameState(), "Game should end after moving into an obstacle");
    }

    // https://www.baeldung.com/java-unit-test-private-methods source for reflection
    // on test on private classes
    @Test
    public void testGenerateApple() throws Exception {
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        Snake snake = new Snake('S', new CellPosition(2, 2));
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        Item item = new Item('A');

        item.removeItem(snakeBoard, 'A');

        Method generateApple = SnakeModel.class.getDeclaredMethod("GenerateApple", char.class);
        generateApple.setAccessible(true);
        generateApple.invoke(snakeModel, 'A');

        Item apple = snakeModel.getItem();
        CellPosition applePosition = apple.getItemPosition();

        int appleCount = 0;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'A') {
                    appleCount++;
                }
            }
        }
        assertNotNull(applePosition, "The apple position should not be null.");
        assertEquals('A', snakeBoard.get(applePosition),
                "The apple should be placed on the board at the apple's position.");

        assertEquals(1, appleCount, "There should be exactly one apple on the board");
        assertNotNull(applePosition, "The apple has a position");
        assertTrue(applePosition.row() >= 0 && applePosition.row() < snakeBoard.rows(),
                "The apple is within the board");
        assertTrue(applePosition.col() >= 0 && applePosition.col() < snakeBoard.cols(),
                "The apple is within the board");
    }

    @Test
    public void testCreateObstacles() throws Exception {
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        Snake snake = new Snake('S', new CellPosition(2, 2));
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        Item item = new Item('O');

        item.removeItem(snakeBoard, 'O');

        Method createObstacles = SnakeModel.class.getDeclaredMethod("createObstacles", char.class);
        createObstacles.setAccessible(true);
        createObstacles.invoke(snakeModel, 'O');

        Item obstacle = snakeModel.getItem();
        CellPosition obstaclePosition = obstacle.getItemPosition();

        int obstacleCount = 0;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'O') {
                    obstacleCount++;
                }
            }
        }

        assertTrue(obstacleCount >= 1 && obstacleCount <= 10,
                "There should be between 1 and 10 obstacles on the board");
        assertNotNull(obstaclePosition, "The obstacle has a position");
        assertTrue(obstaclePosition.row() >= 0 && obstaclePosition.row() < snakeBoard.rows(),
                "The obstacles is within the board");
        assertTrue(obstaclePosition.col() >= 0 && obstaclePosition.col() < snakeBoard.cols(),
                "The obstacles is within the board");
    }

    @Test
    public void testGeneratePoisonousApple() throws Exception {
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        Snake snake = new Snake('S', new CellPosition(2, 2));
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        Item item = new Item('P');

        item.removeItem(snakeBoard, 'P');

        Method generatePoisonousApple = SnakeModel.class.getDeclaredMethod("generatePoisonousApple", char.class);
        generatePoisonousApple.setAccessible(true);
        generatePoisonousApple.invoke(snakeModel, 'P');

        Item papple = snakeModel.getItem();
        CellPosition papplePosition = papple.getItemPosition();

        int applePCount = 0;
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'P') {
                    applePCount++;
                }
            }
        }

        assertEquals('P', snakeBoard.get(papplePosition),
                "The apple should be placed on the board at the poisinous apple's position.");
        assertEquals(1, applePCount, "There should be exactly one poisonous apple on the board");
        assertNotNull(papplePosition, "The poisonous apple should have a valid position");
        assertTrue(papplePosition.row() >= 0 && papplePosition.row() < snakeBoard.rows(),
                "The apple is within the row bounds of the board");
        assertTrue(papplePosition.col() >= 0 && papplePosition.col() < snakeBoard.cols(),
                "The apple is within the column bounds of the board");
    }

    @Test
    public void testDelayTimer() throws Exception {
        SnakeBoard snakeBoard = new SnakeBoard(5, 5);
        Snake snake = new Snake('S', new CellPosition(2, 2));
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);

        // Access the increaseScore method using reflection
        Method increaseScore = SnakeModel.class.getDeclaredMethod("increaseScore");
        increaseScore.setAccessible(true);

        assertEquals(200, snakeModel.delayTimer(), "Initial delay should be 200");

        for (int i = 0; i < 10; i++) {
            increaseScore.invoke(snakeModel);
        }
        assertEquals(150, snakeModel.delayTimer(), "Delay should be 150 after eating 10 apples");

        for (int i = 0; i < 10; i++) {
            increaseScore.invoke(snakeModel);
        }
        assertEquals(120, snakeModel.delayTimer(), "Delay should be 120 after eating 20 apples");

        for (int i = 0; i < 5; i++) {
            increaseScore.invoke(snakeModel);
        }
        assertEquals(90, snakeModel.delayTimer(), "Delay should be 90 after eating 25 apples");
    }

    @Test
    public void testNormalGameModeSelection() {
        SnakeBoard snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(5, 5));
        SnakeModel model = new SnakeModel(snakeBoard, snake);

        model.setGameMode(GameState.NORMAL_MODE_SELECTED);
        assertFalse(model.isHardMode(), "Game should not be in hard mode when normal mode is selected.");
        assertEquals(GameState.START_GAME, model.getGameState(),
                "Game state should remain START_GAME after setting normal mode.");
    }

    @Test
    public void testHardGameModeSelection() {
        SnakeBoard snakeBoard = new SnakeBoard(20, 20);
        Snake snake = new Snake('S', new CellPosition(5, 5));
        SnakeModel model = new SnakeModel(snakeBoard, snake);

        model.setGameMode(GameState.HARD_MODE_SELECTED);
        assertTrue(model.isHardMode(), "Game should be in hard mode when hard mode is selected.");
        assertEquals(GameState.START_GAME, model.getGameState(),
                "Game state should remain START_GAME after setting hard mode.");
    }

    @Test
    public void testResetGame() {
        SnakeBoard snakeBoard = new SnakeBoard(20, 20);
        CellPosition initialPosition = new CellPosition(10, 10);
        Snake snake = new Snake('S', initialPosition);
        SnakeModel model = new SnakeModel(snakeBoard, snake);

        model.moveSnake(Direction.NORTH);
        model.resetGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState(), "Game state should be ACTIVE_GAME after reset.");
        assertEquals(initialPosition, model.getHeadPos(),
                "Snake should be reset to the starting position after reset.");
        assertEquals('A', snakeBoard.get(new CellPosition(5, 10)),
                "There should be an apple at position (5, 10) after reset.");

    }
}
