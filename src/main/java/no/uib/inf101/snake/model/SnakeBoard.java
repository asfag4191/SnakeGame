package no.uib.inf101.snake.model;


import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.GridCell;

public class SnakeBoard extends Grid<Character>{

    public SnakeBoard(int rows, int cols) {
        super(rows, cols,'-');
        //for (GridCell<Character> snakeBoardTile : this) {
            //this.set(snakeBoardTile.pos(), '-');
        //}

}

public void clearBoard() {
    for (GridCell<Character> snakeBoardTile : this) {
        this.set(snakeBoardTile.pos(), '-');
    }
}

}