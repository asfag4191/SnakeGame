package no.uib.inf101.snake.controller;

import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;

/**
 * The interface defines the methods that the controller
 * components of the Snake game needs in order to control the current state of
 * the game. The controller is responsible for starting, pausing, and resetting,
 * and managing the timers of the game.
 */
public interface ControlleableSnake {

    /**
     * Retrieves the current state of the game.
     * 
     * @return The current Game state. Indicating if it's game over, paused,
     *         running, or which mode is selected.
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
     * Moves the snake in the specified direction if the move is valid.
     * 
     * @param direction The direction in which to move the snake.
     */
    public void moveSnake(Direction direction);

    /**
     * Returns the delay time between each clock tick of the snake.
     * 
     * @return The delay time.
     */
    public int delayTimer();

    /**
     * Sets the direction of the snake and prevents moving in the opposite
     * direction.
     * 
     * @param direction The direction to set.
     */
    public void setDirection(Direction direction);

    /**
     * Sets the game screen state to the specified GameState.
     * 
     * @param gameScreen The GameState to set.
     */
    public void setGameScreen(GameState gameScreen);

    /**
     * Returns the delay time between the placement of obstacles on the board.
     * 
     * @return The delay time.
     */
    public int obstacleTimer();

    /**
     * Returns the delay time between the placement of poisonous apples on the
     * board.
     * 
     * @return The delay time.
     */
    public int pappleTimer();

    /**
     * Called on each clock tick to move the snake according to the current state of
     * the game.
     */
    public void clockTickDelay();

    /**
     * Places obstacles on the board on each clock tick when in hard mode.
     */
    public void clockTickObstacle();

    /**
     * Places poisonous apples on the board on each clock tick when in hard mode.
     */
    public void clockTickPapple();

    /**
     * Checks if the game is currently in hard mode.
     * 
     * @return True if in hard mode, false otherwise.
     */
    public boolean isHardMode();
}
