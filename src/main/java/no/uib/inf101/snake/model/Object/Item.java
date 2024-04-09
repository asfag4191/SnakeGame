package no.uib.inf101.snake.model.Object;

import java.util.Random;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.Grid;

public class Item {
    public char character;
    public CellPosition cellPosition;
    public Random random = new Random();

    public Item(char character, CellPosition cellPosition) {
        this.character = character;
        this.cellPosition = cellPosition;
        random=new Random();
    }

    public CellPosition getObjectPosition() {
        return cellPosition;
    }
    
}
