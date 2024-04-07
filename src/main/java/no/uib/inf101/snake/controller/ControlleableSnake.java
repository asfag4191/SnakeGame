package no.uib.inf101.snake.controller;




import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;

/**
 * The interface defines the methods that the controller
 * components of the Tetris game needs in order to control the current state of
 * the game.
 * Managing moving, rotating, dropping the tetrominos and
 * the game timing and controlling the game state.
 */
public interface ControlleableSnake {



    /**
     * Advances the internal clock by one tick.
     */
    public void clockTick();

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
     * @param direction
     */
    public void moveSnake(Direction direction);


    /**
     * @return
     */
    public int delayTimer();


    /**
     * Sets the direction of the snake.
     * 
     * @param direction
     */
    public void setDirection(Direction direction);

    /**
     * Sets the game screen.
     * 
     * @param gameScreen
     */
    public void setGameScreen(GameState gameScreen);
    
}
    