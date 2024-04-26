package no.uib.inf101.snake.model;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.snake.snake.Snake;
import no.uib.inf101.snake.view.ViewableSnakeView;
import no.uib.inf101.snake.controller.ControlleableSnake;
import no.uib.inf101.snake.model.object.Item;

/**
 * Represents the model component of the snake game, managing the state and
 * logic of the game.
 * This includes handling game progression, movements, and interactions within
 * the game grid.
 */
public class SnakeModel implements ViewableSnakeView, ControlleableSnake {

    private SnakeBoard snakeBoard;
    private Snake snake;
    private Direction direction;
    private int newscore;
    private Item item;

    private GameState gameState = GameState.START_GAME;
    private Random random = new Random();
    private boolean hardMode = false;

    /**
     * Constructor to initialize the SnakeModel with a specified board and a snake.
     * 
     * @param snakeBoard the game board used for snakes enviorment.
     * @param snake      the snake object to be used in the game.
     */
    public SnakeModel(SnakeBoard snakeBoard, Snake snake) {
        this.snakeBoard = snakeBoard;
        this.snake = snake;
        this.direction = Direction.NORTH;
        GenerateApple('A');
    }

    @Override
    public void setGameMode(GameState gameState) {
        switch (gameState) {
            case NORMAL_MODE_SELECTED:
                hardMode = false;
                break;
            case HARD_MODE_SELECTED:
                hardMode = true;
                startHardModeFeatures();
                break;
            default:
        }
    }

    /**
     * Checks whether the snake's next move is legal. A move is considered illegal
     * if it results in the snake moving into a wall, itself, or any objects if the
     * game is in hard mode.
     *
     * @param direction the intended direction of the snake's movement.
     * @return true if the move is legal, false otherwise.
     */
    private boolean legalMove(Direction direction) {
        CellPosition headPos = this.snake.getHeadPos();
        CellPosition newPos = direction.move(headPos);

        if (!this.snakeBoard.positionIsOnGrid(newPos)) {
            return false;
        }
        if (this.snakeBoard.get(newPos) == 'O') {
            return false;
        }
        if (this.snakeBoard.get(newPos) == 'S') {
            return false;
        }
        return true;
    }

    @Override
    public void moveSnake(Direction direction) {
        if (!legalMove(direction)) {
            this.gameState = GameState.GAME_OVER;
            return;
        }
        CellPosition newPos = direction.move(this.snake.getHeadPos());
        if (this.snakeBoard.get(newPos) == 'A') {
            this.snakeBoard.set(newPos, '-');
            this.snake.grow(newPos);
            GenerateApple('A');
            increaseScore();
        } else if (this.snakeBoard.get(newPos) == 'P') {
            this.snakeBoard.set(newPos, '-');
            this.snakeBoard.set(this.snake.getTailPos(), '-');
            this.snake.move(direction);
            generatePoisonousApple('P');
            decreaseScore();
        } else {
            this.snakeBoard.set(this.snake.getTailPos(), '-');
            this.snake.move(direction);
        }
        this.snakeBoard.set(this.snake.getHeadPos(), 'S');
    }

    /**
     * Checks if there is already an apple on the board, removes it if present, and
     * then generates a new apple at a random position on the board.
     *
     * @param c the character representing an apple, used for placing on the board.
     */
    private void GenerateApple(char c) {
        if (item != null) {
            item.removeItem(snakeBoard, 'A');
        }
        Item apple = new Item(c);
        apple.generateRandomPosition(snakeBoard);
        apple.placeOnBoard(snakeBoard, 'A');
        this.item = apple;
    }

    /**
     * Starts the hard mode features which include generating obstacles and a
     * poisonous apple, along with setting their respective timers for updates.
     */
    private void startHardModeFeatures() {
        createObstacles('O');
        generatePoisonousApple('P');
        obstacleTimer();
        pappleTimer();
    }

    /**
     * Removes the obstacles from the board and generate new ones.
     * Generates a obstacles which is a random number from 1 to 10.
     * 
     * @param c the character representing obstacles.
     */
    private void createObstacles(char c) {
        int numberOfObstacles = random.nextInt(10) + 1;
        if (item != null) {
            item.removeItem(snakeBoard, 'O');
        }
        for (int i = 0; i < numberOfObstacles; i++) {
            Item obstacle = new Item(c);
            obstacle.generateRandomPosition(snakeBoard);
            obstacle.placeOnBoard(snakeBoard, 'O');
        }
    }

    /**
     * Remove the poisonous apple from the board and generate a new one,
     * places the new poisinous apple on a random position on the board.
     * 
     * @param c
     */
    private void generatePoisonousApple(char c) {
        if (item != null) {
            item.removeItem(snakeBoard, 'P');
        }
        Item applePoisinous = new Item(c);
        applePoisinous.generateRandomPosition(snakeBoard);
        applePoisinous.placeOnBoard(snakeBoard, 'P');
        this.item = applePoisinous;
    }

    @Override
    public void setDirection(Direction newDirection) {
        if ((this.direction == Direction.NORTH && newDirection != Direction.SOUTH) ||
                (this.direction == Direction.SOUTH && newDirection != Direction.NORTH) ||
                (this.direction == Direction.WEST && newDirection != Direction.EAST) ||
                (this.direction == Direction.EAST && newDirection != Direction.WEST)) {
            this.direction = newDirection;
        }
    }

    @Override
    public void resetGame() {
        snakeBoard.clearBoard();
        newscore = 0;
        CellPosition startPosition = new CellPosition(10, 10);
        this.snake = new Snake('S', startPosition);
        snakeBoard.set(new CellPosition(5, 10), 'A');
        this.direction = Direction.NORTH;

        if (hardMode) {
            setGameMode(GameState.HARD_MODE_SELECTED);
        } else {
            setGameMode(GameState.NORMAL_MODE_SELECTED);
        }
        this.gameState = GameState.ACTIVE_GAME;
    }

    @Override
    public int delayTimer() {
        int delay;
        if (newscore < 100) {
            delay = 200;
        } else if (newscore < 200) {
            delay = 150;
        } else if (newscore < 250) {
            delay = 120;
        } else {
            delay = 90;
        }
        return delay;

    }

    /**
     * increases the score by 10.
     */
    private void increaseScore() {
        this.newscore += 10;
    }

    /**
     * Decreases the score by 10.
     */
    private void decreaseScore() {
        this.newscore -= 10;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    @Override
    public GridDimension getDimension() {
        return this.snakeBoard;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return this.snakeBoard;
    }

    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    @Override
    public Iterable<GridCell<Character>> getSnake() {
        return this.snake;
    }

    @Override
    public int getscore() {
        return this.newscore;
    }

    @Override
    public void setGameScreen(GameState gameScreen) {
        this.gameState = gameScreen;
    }

    @Override
    public CellPosition getHeadPos() {
        return this.snake.getHeadPos();
    }

    @Override
    public int pappleTimer() {
        return 1000;
    }

    @Override
    public int obstacleTimer() {
        return 2000;
    }

    @Override
    public void clockTickDelay() {
        moveSnake(this.direction);
    }

    @Override
    public void clockTickObstacle() {
        createObstacles('O');
    }

    @Override
    public void clockTickPapple() {
        generatePoisonousApple('P');
    }

    @Override
    public boolean isHardMode() {
        return hardMode;
    }
}
