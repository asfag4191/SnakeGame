package no.uib.inf101.snake.controller;

import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;

/**
 * The interface defines the methods that the controller
 * components of the Snake game needs in order to control the current state of
 * the game.
 */
public interface ControlleableSnake {

    /**
     * Advances the internal clock by one tick.
     */
    //public void clockTick();

    /**
     * Retrieves the current state of the game.
     * 
     * @return The current Game state. Indicating if it's over, active or in
     *         another state.
     */
    public GameState getGameState();

    /**
     * Starts a new game.
     */
    public void startGame();

    /**
     * Resets the game to the initial state.
     */
    public void resetGame();

    /**
     * 
     * @param direction The direction in which to move the snake.
     */
    public void moveSnake(Direction direction);

    /**
     * 
     * @return The delay time between each clock tick.
     */
    public int delayTimer();

    /**
     * 
     * @param direction Sets the direction of the snake.
     */
    public void setDirection(Direction direction);

    /**
     * 
     * @param gameScreen The Gamestate to set the gamesreen to. 
     */
    public void setGameScreen(GameState gameScreen);

    public int obstacleTimer();

    public int pappleTimer();

    public void clockTickDelay();

    public void clockTickObstacle();

    public void clockTickPapple();

    public boolean isHardMode();

 

}
