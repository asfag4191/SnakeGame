package no.uib.inf101.snake.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class SnakeBoard extends Grid<Character> {

    /**
     * ////
     * @param rows The rows of the board. 
     * @param cols The columns of the board. 
     */
    public SnakeBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Clears the board
     */
    public void clearBoard() {
        for (GridCell<Character> snakeBoardTile : this) {
            this.set(snakeBoardTile.pos(), '-');
        }
    }
}