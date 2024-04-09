package no.uib.inf101.snake.model;

public enum GameState {
    /**
     *
     */
    START_GAME,
    /**
     *Initialize when the game is active
     */
    ACTIVE_GAME,
    /**
     *Initialize when the game is paused
     */
    PAUSE_GAME,
    /**
     *
     */
    GAME_OVER, 

    NORMAL_MODE_SELECTED,

    HARD_MODE_SELECTED,
}