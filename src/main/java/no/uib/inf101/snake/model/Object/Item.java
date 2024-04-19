package no.uib.inf101.snake.model.Object;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.model.SnakeBoard;

public class Item {
    public char character;
    public CellPosition cellPosition;
    public SnakeBoard snakeBoard;
    public Random random = new Random();

    public Item(char character) {
        this.character = character;
        random = new Random();
    }

    public char getCharacter() {
        return character;
    }

    /**
     * Generates the items on random positions on the board. 
     * 
     * @param snakeBoard The board the items gets generated on
     * @return The new cellPosition to the Item.
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
     * Set the characters on the board, that is random generated. 
     * @param snakeBoard The board the character is set on. 
     * @param c The character to be set on the board. 
     */
    public void placeOnBoard(SnakeBoard snakeBoard, char c) {
        snakeBoard.set(cellPosition, c);

    }

    public void removeItem(SnakeBoard snakeBoard, char itemToRemove) {
        for (int row = 0; row < snakeBoard.rows(); row++) {
            for (int col = 0; col < snakeBoard.cols(); col++) {
                if (snakeBoard.get(new CellPosition(row, col)) == itemToRemove) {
                    snakeBoard.set(new CellPosition(row, col), '-');
                }
            }
        }
    }

    public CellPosition getItemPosition() {
        return cellPosition;
    }
}

