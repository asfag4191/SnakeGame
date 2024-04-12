package no.uib.inf101.snake.model.Object;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class Item {
    public char character;
    public CellPosition cellPosition;
    public Random random = new Random();

    public Item(char character) {
        this.character = character;
        random = new Random();
    }

    public char getCharacter() {
        return character;
    }

    public CellPosition generateRandomPosition(Grid<Character> board) {
        int row;
        int col;
        do {
            row = random.nextInt(board.rows());
            col = random.nextInt(board.cols());
            cellPosition = new CellPosition(row, col); // Update the cellPosition field
        } while (board.get(cellPosition) != '-'); // Check if the position is free
        return cellPosition;
    }


   public void placeOnBoard(Grid<Character> board, char c) {
        board.set(cellPosition, c);

    }
         //int row;
        //int col;
       // do {
            //row = random.nextInt(board.rows());
            //col = random.nextInt(board.cols());
            //cellPosition = new CellPosition(row, col);
        //} while (board.get(cellPosition) != '-'); // Chech if the position is free
        //return cellPosition;
    //}
}
