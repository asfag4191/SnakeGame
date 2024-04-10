package no.uib.inf101.snake.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    private GameState gameState = GameState.START_GAME;
    private Random random = new Random();
    private Direction direction;
    private int newscore;
    private Item item;
    private Timer timer;

    private boolean hardMode = false;
    private Timer obstacleTimer;
    private Timer appleTimer;

    public SnakeModel(SnakeBoard snakeBoard, Snake snake) {
        this.snakeBoard = snakeBoard;
        this.snake = snake;
        this.direction = direction.NORTH;// start
        GenerateApple('A');
    }
    

    public void setGameMode(GameState gameState) {
        switch (gameState) {
            case NORMAL_MODE_SELECTED:
                hardMode = false;
                break;
            case HARD_MODE_SELECTED:
                hardMode = true;
                ObstacleFeatures();
                PoisonousAppleMode();
                break;
            default:
        }
    }

    @Override
    public int obstacleTimer() {
        return 200;

    }

    private void ObstacleFeatures() {
        obstacleTimer = new Timer();
        obstacleTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateObstacles();
                // updatePoisonusApple();
            }
        }, 0, 2000);
    }

    private void updateObstacles() {
        removeObstacles();
        createObstacles();
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
        for (int i = 0; i < 5; i++) {
            int row = random.nextInt(snakeBoard.rows());
            int col = random.nextInt(snakeBoard.cols());
            CellPosition position = new CellPosition(row, col);

            // Sørg for at hindringene ikke overlapper med slangen eller hverandre
            while (snakeBoard.get(position) != '-' || snake.getSnake().contains(position)) {
                row = random.nextInt(snakeBoard.rows());
                col = random.nextInt(snakeBoard.cols());
                position = new CellPosition(row, col);
            }
            snakeBoard.set(position, 'O');
        }
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
    public void clockTick() {
        moveSnake(this.direction);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void startGame() {
        newscore = 0;
    }

    @Override
    public void resetGame() {
        // Clear the board
        snakeBoard.clearBoard();
        newscore = 0;
        CellPosition startPosition = new CellPosition(10, 10);
        snake = new Snake('S', startPosition);
        snakeBoard.set(new CellPosition(5, 10), 'A');
        this.direction = direction.NORTH;
        if (hardMode) {
            setGameMode(GameState.HARD_MODE_SELECTED);
        } else {
            setGameMode(GameState.NORMAL_MODE_SELECTED);
        }

        // Start spillet
        this.gameState = GameState.ACTIVE_GAME;
    }

    @Override
    public int delayTimer() {
        if (newscore < 80) {
            return 180;
        } else if (newscore < 110) {
            return 120;
        } else if (newscore < 150) {
            return 120;
        } else {
            return 90;
        }
    }

    public void increaseScore() {
        this.newscore += 10;
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
        if (this.snakeBoard.get(newPos) == 'P') {
            return true;
        }

        return true;
    }
    private void stopTimers() {
        if (obstacleTimer != null) {
            obstacleTimer.cancel();
            obstacleTimer = null;
        }
        if (appleTimer != null) {
            appleTimer.cancel();
            appleTimer = null;
        }
    }

    @Override
    public void moveSnake(Direction direction) {
        if (!legalMove(direction)) {
            this.gameState = GameState.GAME_OVER;
            stopTimers();
            return;
        }
        CellPosition newPos = direction.move(this.snake.getHeadPos());
        if (this.snakeBoard.get(newPos) == 'A') {
            this.snakeBoard.set(newPos, '-');
            this.snake.grow(newPos);
            GenerateApple('A');
            increaseScore();
        }  else if (this.snakeBoard.get(newPos) == 'P') {
            this.snakeBoard.set(newPos, '-');
            this.snakeBoard.set(this.snake.getTailPos(), '-'); //removes the tail from the board
            this.snake.move(direction); // Flytt slangen
            GeneratePoisonousApple('P');
            increaseScore();
            increaseScore();
        } else {
            this.snakeBoard.set(this.snake.getTailPos(), '-');
            this.snake.move(direction);
        }

        this.snakeBoard.set(this.snake.getHeadPos(), 'S');

    }

    

    public void GenerateApple(char c) {
        int row = random.nextInt(snakeBoard.rows());
        int col = random.nextInt(snakeBoard.cols());
        CellPosition applePosition = new CellPosition(row, col);

        // generate a new position if the apple is not placed on a empty tile
        while (!snakeBoard.get(applePosition).equals('-')) {
            row = random.nextInt(snakeBoard.rows());
            col = random.nextInt(snakeBoard.cols());
            applePosition = new CellPosition(row, col);
        }
        Item apple = new Item('A', applePosition);
        this.item = apple;
        snakeBoard.set(applePosition, c);
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

    private void PoisonousAppleMode() {
        appleTimer = new Timer();
        appleTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updatePoisonusApple();
            }
        }, 0, 3000);
    }

    public void updatePoisonusApple() {
        removePoisonpusApple();
        GeneratePoisonousApple('P');

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
        int row = random.nextInt(snakeBoard.rows());
        int col = random.nextInt(snakeBoard.cols());
        CellPosition applePPosition = new CellPosition(row, col);

        // generate a new position if the apple is not placed on a empty tile
        while (!snakeBoard.get(applePPosition).equals('-')) {
            row = random.nextInt(snakeBoard.rows());
            col = random.nextInt(snakeBoard.cols());
            applePPosition = new CellPosition(row, col);
        }
        Item appleP = new Item('P', applePPosition);
        this.item = appleP;
        snakeBoard.set(applePPosition, c);
    }

    // Improved version of temporarilyIncreasePace
   // private void temporarilyIncreasePace() {
      
   // }

}

