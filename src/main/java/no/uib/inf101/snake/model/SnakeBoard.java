package no.uib.inf101.snake.model;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

/**
 * Represents the game board for the snake game.
 * This class extends {@link Grid} to manage the grid cells specifically for the
 * snake game.
 */
public class SnakeBoard extends Grid<Character> {

    /**
     * Constructs a new SnakeBoard with the specified number of rows and columns.
     * Initially, all positions on the board are set to a default character
     * indicating empty spaces.
     *
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public SnakeBoard(int rows, int cols) {
        super(rows, cols, '-');
    }

    /**
     * Clears the board by setting all cells to the default empty character.
     * This method iterates over each cell in the board and resets its value.
     */
    public void clearBoard() {
        for (GridCell<Character> snakeBoardTile : this) {
            this.set(snakeBoardTile.pos(), '-');
        }
    }
}