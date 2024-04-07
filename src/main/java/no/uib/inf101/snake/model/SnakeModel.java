package no.uib.inf101.snake.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
    private GameState gameState = GameState.START_GAME;
    //private GameState gameState = GameState.ACTIVE_GAME;
    private Random random = new Random();
    private Direction direction;
    private int newscore;
    // private Direction direction;
    // public LinkedList<GridCell
    private Item item;

    public SnakeModel(SnakeBoard snakeBoard, Snake snake) {
        // if (snakeBoard == null || Snake == null){
        // throw new IllegalArgumentException("SnakeBoard and Snake can't be null");
        // }
        this.snakeBoard = snakeBoard;
        //this.gameState = GameState.ACTIVE_GAME;
        this.snake = snake;
        //his.gameState = GameState.ACTIVE_GAME;
        this.direction=direction.NORTH;//start
        GenerateApple('A');
        // this.gameState = GameState.WELCOME_SCREEN;

        // 4this.currentSnake = new Snake(new CellPosition(midRow, midCol));

    }

    // need to make something here to chech if the move is legal

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
        //if (gameState == GameState.ACTIVE_GAME) {
            moveSnake(this.direction);
        
            // attachSnaketoBoard();
        }


  

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void startGame() {
        newscore=0;
    }

    @Override
    public void resetGame() {
        // Clear the board
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                snakeBoard.set(new CellPosition(row, col), '-');
            }
        newscore = 0;
       CellPosition startPosition = new CellPosition( 10,10);
        snake = new Snake('S', startPosition);
        snakeBoard.set(new CellPosition(5, 10), 'A');
        this.direction=direction.NORTH;
        // Set the game state to active or whatever the initial state should be
        
        this.gameState = GameState.ACTIVE_GAME;
        }
    }

    @Override
    public int delayTimer() {
        return 180;
    }
    public void increaseScore() {
        this.newscore += 10; // Assuming each apple is worth 10 points, adjust as necessary
    }
    private boolean legalMove(Direction direction) {
        CellPosition headPos = this.snake.getHeadPos();
        CellPosition newPos = direction.move(headPos);

        if (!this.snakeBoard.positionIsOnGrid(newPos)) {
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
            this.snakeBoard.set(newPos,'-'); 
            this.snake.grow(newPos);
            GenerateApple('A');
            increaseScore();
            System.out.println("Score: " + getscore() + " points");
        } else {
            this.snakeBoard.set(this.snake.getTailPos(), '-');
            this.snake.move(direction);
            //direction=this.direction;
        }
       
        this.snakeBoard.set(this.snake.getHeadPos(), 'S');
      
    }

   
    // happens when the snake can't be moved anymore
    private void attachSnaketoBoard() {
        for (GridCell<Character> cell : this.snake) {
            snakeBoard.set(cell.pos(), cell.value());
        }
    }
    public void GenerateApple(char c){
        int row = random.nextInt(snakeBoard.rows());
        int col = random.nextInt(snakeBoard.cols());
        CellPosition applePosition = new CellPosition(row, col);

        //generate a new position if the apple is not placed on a empty tile
        while (!snakeBoard.get(applePosition).equals('-')) {
            row = random.nextInt(snakeBoard.rows());
            col = random.nextInt(snakeBoard.cols());
            applePosition = new CellPosition(row, col);
        }
        Item apple=new Item('A', applePosition);
        this.item=apple;
        snakeBoard.set(applePosition, c);



    }
    //public void mainMenu(){
        //this.gameState=GameState.MAIN_MENU;

    
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
}

