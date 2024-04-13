package no.uib.inf101.snake.model;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.snake.snake.Snake;
import no.uib.inf101.snake.view.ViewableSnakeView;
import no.uib.inf101.snake.controller.ControlleableSnake;
import no.uib.inf101.snake.model.Object.Item;

public class SnakeModel implements ViewableSnakeView, ControlleableSnake {

    private SnakeBoard snakeBoard;
    private Snake snake;
    private Direction direction;
    private int newscore;
    private Item item;

    private GameState gameState = GameState.START_GAME;
    private Random random = new Random();
    private boolean hardMode = false;


    public SnakeModel(SnakeBoard snakeBoard, Snake snake) {
        this.snakeBoard = snakeBoard;
        this.snake = snake;
        this.direction = Direction.NORTH;// start
        GenerateApple('A');
    }

    public void setGameMode(GameState gameState) {
        switch (gameState) {
            case NORMAL_MODE_SELECTED:
                hardMode = false;
                // stopTimers();
                break;
            case HARD_MODE_SELECTED:
                hardMode = true;
                startHardModeFeatures();
                break;
            default:
        }
    }

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
            // stopTimers();
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
            this.snakeBoard.set(this.snake.getTailPos(), '-'); // removes the tail from the board
            this.snake.move(direction); 
            GeneratePoisonousApple('P');

        } else {
            this.snakeBoard.set(this.snake.getTailPos(), '-');
            this.snake.move(direction);
        }

        this.snakeBoard.set(this.snake.getHeadPos(), 'S');

    }

    @Override
    public void setDirection(Direction newDirection) {
        // Chech if the new direction is the opposite of current
        if ((this.direction == Direction.NORTH && newDirection != Direction.SOUTH) ||
                (this.direction == Direction.SOUTH && newDirection != Direction.NORTH) ||
                (this.direction == Direction.WEST && newDirection != Direction.EAST) ||
                (this.direction == Direction.EAST && newDirection != Direction.WEST)) {
            this.direction = newDirection;
        }
    }

    public void GenerateApple(char c) {
        Item apple = new Item(c);
        CellPosition applePosition = apple.generateRandomPosition(snakeBoard);
        apple.placeOnBoard(snakeBoard, c);
        this.item = apple; // Hvis du trenger å beholde referansen til eplet
    }

    private void startHardModeFeatures() {
        ObstacleFeatures();
        PoisonousAppleMode();
        obstacleTimer();
        pappleTimer();
    }

    @Override
    public int obstacleTimer() {
        return 2000;
    }

    private void ObstacleFeatures() {
        updateObstacles();
    }

    private void updateObstacles() {
        if (hardMode) {
            removeObstacles();
            createObstacles();
        }
    }

    private void removeObstacles() {
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'O') {
                    snakeBoard.set(new CellPosition(row, col), '-');
                }
            }
        }
    }

    private void createObstacles() {
        int numberOfObstacles = random.nextInt(10) + 1;

        for (int i = 0; i < numberOfObstacles; i++) {
            Item obstacle = new Item('O');
            CellPosition position = obstacle.generateRandomPosition(snakeBoard);
            obstacle.placeOnBoard(snakeBoard, 'O');
        }
    }

    private void PoisonousAppleMode() {
        updatePoisonusApple();
    }

    private void updatePoisonusApple() {
        if (hardMode) {
            removePoisonpusApple();
            GeneratePoisonousApple('P');
        }
    }

    private void removePoisonpusApple() {
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == 'P') {
                    snakeBoard.set(new CellPosition(row, col), '-');
                }
            }
        }
    }

    public void GeneratePoisonousApple(char c) {
        Item applePoisinous = new Item(c);
        CellPosition applePposition = applePoisinous.generateRandomPosition(snakeBoard);
        applePoisinous.placeOnBoard(snakeBoard, c);
        this.item = applePoisinous; // Hvis du trenger å beholde referansen til eplet
    }

    @Override
    public void resetGame() {
        snakeBoard.clearBoard();
        newscore = 0;
        CellPosition startPosition = new CellPosition(10, 10);
        snake = new Snake('S', startPosition);
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
        } System.out.println("Delay: " + delay);
        return delay;

    }

    public void increaseScore() {
        this.newscore += 10;
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

    public SnakeBoard getBoard() {
        return snakeBoard;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public void startGame() {
        newscore = 0;
    }


    @Override
    public int pappleTimer() {
        return 3000;
    }


    @Override
    public void clockTickDelay() {
        moveSnake(this.direction);
    }

    @Override
    public void clockTickObstacle() {
        updateObstacles();
    }

    @Override
    public void clockTickPapple() {
        updatePoisonusApple();
    }

    @Override
    public boolean isHardMode() {
        return hardMode;
    }

    public CellPosition getApplePosition() {
        return item.cellPosition;

    }
}
