package no.uib.inf101.snake.model;

/**
 * Represents the different states of the Snake game.
 */
public enum GameState {
    /**
     * Represents the state when the game is about to start.
     */
    START_GAME,

    /**
     * Represents the state when the game is currently active.
     */
    ACTIVE_GAME,

    /**
     * Represents the state when the game is paused.
     */
    PAUSE_GAME,

    /**
     * Represents the state when the game is over.
     */
    GAME_OVER,

    /**
     * Represents the state when the normal mode is selected.
     */
    NORMAL_MODE_SELECTED,

    /**
     * Represents the state when the hard mode is selected.
     */
    HARD_MODE_SELECTED,
}