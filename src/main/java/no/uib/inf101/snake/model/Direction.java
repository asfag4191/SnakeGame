package no.uib.inf101.snake.model;

import no.uib.inf101.grid.CellPosition;

/**
 * The Direction enum represents the four possible directions that the snake
 * can move in: North, South, East, and West.
 */
public enum Direction {

    /**
     * The North direction represents moving upwards.
     */
    NORTH,

    /**
     * The South direction represents moving downwards.
     */
    SOUTH,

    /**
     * The East direction represents moving towards the right.
     */
    EAST,

    /**
     * The West direction represents moving towards the left.
     */
    WEST;

    /**
     * Moves a cell position in the specified direction.
     * 
     * @param pos The current position.
     * @return The new position after moving in the specified direction.
     */
    public CellPosition move(CellPosition pos) {
        switch (this) {
            case NORTH:
                return new CellPosition(pos.row() - 1, pos.col());
            case SOUTH:
                return new CellPosition(pos.row() + 1, pos.col());
            case EAST:
                return new CellPosition(pos.row(), pos.col() + 1);
            case WEST:
                return new CellPosition(pos.row(), pos.col() - 1);
            default:
                return pos;
        }
    }
}
