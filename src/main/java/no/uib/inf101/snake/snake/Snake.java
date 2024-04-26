package no.uib.inf101.snake.snake;

import java.util.Iterator;
import java.util.LinkedList;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.snake.model.Direction;

/**
 * Represents the snake in the Snake game, managing the segments that make up
 * the snake's body.
 * This class provides methods to move, grow, and manage the snake's position on
 * the game board.
 */
public class Snake implements Iterable<GridCell<Character>> {

    private char symbol;
    private LinkedList<GridCell<Character>> body;

    /**
     * Creates a new snake with a given symbol and initial head position.
     *
     * @param symbol  The character symbol representing the snake on the game board.
     * @param headPos The initial position of the snake's head on the game board.
     */
    public Snake(char symbol, CellPosition headPos) {
        this.body = new LinkedList<>();
        this.symbol = symbol;
        this.body.add(new GridCell<>(headPos, symbol));
    }

    /**
     * Shifts the position of the snake's head by the given row and column deltas.
     *
     * @param deltaRow The row delta to shift the head's position.
     * @param deltaCol The column delta to shift the head's position.
     * @return A new Snake instance with the head shifted by the specified deltas.
     */
    public Snake ShiftedBy(int deltaRow, int deltaCol) {
        CellPosition newHeadPos = new CellPosition(getHeadPos().row() + deltaRow, getHeadPos().col() + deltaCol);
        return new Snake(symbol, newHeadPos);
    }

    /**
     * Returns the snake's body as a linked list of grid cells.
     * Each grid cell represents a segment of the snake's body, containing
     * its position on the game board and the symbol that visually represents
     * the snake segment.
     *
     * @return LinkedList of {@link <GridCell<Character>} representing the snake's
     *         body.
     */
    public LinkedList<GridCell<Character>> getSnake() {
        return this.body;
    }

    /**
     * Get the length of the snake.
     * 
     * @return The length of the snake.
     */
    public int getLength() {
        return body.size();
    }

    /**
     * Gets the position of the snake's head on the game board.
     *
     * @return the {@link CellPosition} corresponding to the location of the snake's
     *         head.
     */
    public CellPosition getHeadPos() {
        return body.getFirst().pos();
    }

    /**
     * Gets the position of the snake's tail on the game board.
     *
     * @return the {@link CellPosition} corresponding to the location of the snake's
     *         tail.
     */
    public CellPosition getTailPos() {
        return body.getLast().pos();
    }

    /**
     * Moves the snake in the specified direction. This updates the head's position
     * to a new location
     * according to the direction and removes the last segment to simulate forward
     * movement.
     *
     * @param direction the {@link Direction} in which to move the snake's head,
     *                  influencing the
     *                  new position of the snake on the game board.
     */
    public void move(Direction direction) {
        CellPosition currentHeadPos = getHeadPos();
        CellPosition newHeadPos = direction.move(currentHeadPos);

        GridCell<Character> cell = new GridCell<>(newHeadPos, this.symbol);

        this.body.pollLast();
        this.body.addFirst(cell);
    }

    /**
     * Grows the snake by adding a new segment at a given position. This new segment
     * becomes the new head
     * of the snake, increasing its length by one.
     *
     * @param pos the {@link CellPosition} where the new head segment is to be
     *            added.
     */
    public void grow(CellPosition pos) {
        body.addFirst(new GridCell<>(pos, 'S'));
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        return body.iterator();
    }

}
