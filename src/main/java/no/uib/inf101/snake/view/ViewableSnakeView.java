package no.uib.inf101.snake.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.snake.model.GameState;

/*
 * Interface defining the viewable aspects of the Snake game,
 * providing methods to access the game state, board dimension,
 * board tiles, snake tiles, score, and the snake's head position.
 */
public interface ViewableSnakeView {

  /**
   * @return the current game state, whether is active, paused, over, or in a
   *         specific mode.
   */
  GameState getGameState();

  /**
   * Retrieves the dimension of the game board, defining its size.
   * 
   * @return the dimension of the game board.
   */
  GridDimension getDimension();

  /**
   * Retrieves all the tiles on the game board, including both static elements
   * and movable items like apples or obstacles.
   * 
   * @return an iterable collection of grid cells representing the tiles on the
   *         game board.
   */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /**
   * Retrieves the tiles occupied by the snake on the game board,
   * detailing each segment of the snake's body.
   * 
   * @return an iterable collection of grid cells representing the snake's tiles
   *         on the game board.
   */
  Iterable<GridCell<Character>> getSnake();

  /**
   * Retrieves the current score, which typically increases by specific actions
   * such as eating food or achieving certain milestones within the game.
   *
   * @return the current score as an integer.
   */
  int getscore();

  /**
   * Retrieves the position of the head of the snake, which is critical for
   * determining movement and collision outcomes.
   *
   * @return the current position of the snake's head as an instance of
   *         {@link CellPosition}.
   */
  CellPosition getHeadPos();

}
