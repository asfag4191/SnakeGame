package no.uib.inf101.snake.model.object;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.model.SnakeBoard;

/**
 * Represents an item in the snake game that can be placed on the game board.
 * Items are placed on random positions on the board and may represent various
 * objects
 * in the game such as apples or obstacles.
 */
public class Item {
    public char character;
    public CellPosition cellPosition;
    public SnakeBoard snakeBoard;
    public Random random = new Random();

    /**
     * Constructs an item with a specified character.
     *
     * @param character The character that represents this item on the game board.
     */
    public Item(char character) {
        this.character = character;
        random = new Random();
    }

    /**
     * Generates a random position for the item on the specified game board.
     * The position is determined such that it does not overlap with existing items
     * on the board.
     *
     * @param snakeBoard The game board on which the item will be placed.
     * @return The new CellPosition where the item will be located.
     */
    public CellPosition generateRandomPosition(SnakeBoard snakeBoard) {
        int row;
        int col;
        do {
            row = random.nextInt(snakeBoard.rows());
            col = random.nextInt(snakeBoard.cols());
            cellPosition = new CellPosition(row, col); // Update the cellPosition field
        } while (snakeBoard.get(cellPosition) != '-'); // Check if the position is free
        return cellPosition;
    }

    /**
     * Places the item on the board at the previously generated position.
     * This method sets the item's character on the board at the item's cell
     * position.
     *
     * @param snakeBoard The game board on which the character is set.
     * @param c          The character to be placed on the board.
     */
    public void placeOnBoard(SnakeBoard snakeBoard, char c) {
        snakeBoard.set(cellPosition, c);

    }

    /**
     * Removes all instances of a specific item from the game board.
     *
     * @param snakeBoard   The game board from which the item is to be removed.
     * @param itemToRemove The character representing the item to be removed.
     */
    public void removeItem(SnakeBoard snakeBoard, char itemToRemove) {
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == itemToRemove) {
                    snakeBoard.set(new CellPosition(row, col), '-');
                }
            }
        }
    }

    /**
     * Gets the current position of this item on the game board.
     *
     * @return The CellPosition of this item.
     */

    public CellPosition getItemPosition() {
        return cellPosition;
    }

    /**
     * Retrieves the character representing the item.
     *
     * @return The character of this item.
     */
    public char getCharacter() {
        return character;
    }

}
