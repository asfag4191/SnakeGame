package no.uib.inf101.snake.controller;

import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;

/**
 * The interface defines the methods that the controller
 * components of the Snake game needs in order to control the current state of
 * the game.
 * Responsible for starting, pausing, resetting,
 * and managing the timers of the game.
 */
public interface ControlleableSnake {

    /**
     * Moves the snake in the specified direction if the move is valid,
     * (not into walls, itself or any objects).
     * 
     * @param direction the direction in which to move the snake.
     */
    public void moveSnake(Direction direction);

    /**
     * Retrieves the current state of the game indicating whether the game is
     * active,
     * paused, over, or in a specific mode.
     * 
     * @return the current state of the game.
     */
    public GameState getGameState();

    /**
     * Resets the game to the initial state. Clears the board, before setting the
     * score to 0, and the snake to its initial position. Set the game mode to
     * the selected mode, and the game state to active.
     */
    public void resetGame();

    /**
     * Sets the direction of the snake and prevents moving in the opposite
     * direction.
     * 
     * @param direction the new direction.
     */
    public void setDirection(Direction direction);

    /**
     * Sets the current screen to the new state.
     * 
     * @param gameScreen the new state to set.
     */
    public void setGameScreen(GameState gameScreen);

    /**
     * Sets the game mode to either normal or hard based on input, what
     * the player choose.
     * 
     * @param gameState the new game state indicating the desired mode
     */
    public void setGameMode(GameState gameState);

    /**
     * Returns the delay time between each clock tick of the snake.
     * 
     * @return the delay time in milliseconds.
     */
    public int delayTimer();

    /**
     * Returns the delay time between the placement of obstacles on the board,
     * relevant in hard mode.
     * 
     * @return the time in milliseconds between each placement of obstacles on the
     *         board.
     */
    public int obstacleTimer();

    /**
     * Returns the delay time between the placement of poisonous apples on the
     * board, relevant in hard mode.
     * 
     * @return the time in milliseconds between each placement of the poisonous
     *         apple on the board.
     */
    public int pappleTimer();

    /**
     * Updates the game at each tick based on the delayTimer, moving the
     * snake.
     */
    public void clockTickDelay();

    /**
     * Places new obstacles on the game board at each tick, applicable in hard mode.
     */
    public void clockTickObstacle();

    /**
     * Places new poisonous apples on the game board at each tick, applicable in
     * hard mode.
     */
    public void clockTickPapple();

    /**
     * Determines if the game is currently set to hard mode.
     *
     * @return true if hard mode is active, false otherwise.
     */
    public boolean isHardMode();
}
