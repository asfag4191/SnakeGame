package no.uib.inf101.snake.view;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;
import no.uib.inf101.snake.model.GameState;

public interface ViewableSnakeView {

  /**
   * @return the current game state.
   */
  GameState getGameState();

  /**
   * @return the dimension of the game board.
   */
  GridDimension getDimension();

  /**
   * @return the tiles on the game board.
   */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /**
   * 
   * @return the tiles of the snake on the game board.
   */
  Iterable<GridCell<Character>> getSnake();

  /**
   * @return the current score, from removing full rows.
   */
  int getscore();

  /**
   * @return the position of the head of the snake.
   */
  CellPosition getHeadPos();

 


}
