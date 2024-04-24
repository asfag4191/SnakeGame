package no.uib.inf101.snake.snake;


import java.util.Objects;
import java.util.Iterator;
import java.util.LinkedList;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.snake.model.Direction;

public class Snake implements Iterable<GridCell<Character>> {

    private char symbol;
    private LinkedList<GridCell<Character>> body;

  
    //new snake with given head positions
    public Snake(char symbol, CellPosition headPos) {
       this.body= new LinkedList<>();
       this.symbol=symbol;
       this.body.add(new GridCell<>(headPos, symbol));

    }

    public Snake ShiftedBy(int deltaRow, int deltaCol) {
        CellPosition newHeadPos = new CellPosition(getHeadPos().row() + deltaRow, getHeadPos().col() + deltaCol);
        return new Snake(symbol,newHeadPos);
    }
    public LinkedList<GridCell<Character>> getSnake() {
        return this.body;
    }


    @Override
    public Iterator<GridCell<Character>> iterator() {
        return body.iterator();
    }


    //the length is set to 1, does not change 
    public int getLength() { 
        return body.size();
    }


    //get the snake position, list over the positions to the snake segments 
    public CellPosition getHeadPos() {
        return body.getFirst().pos();
    }

    public CellPosition getTailPos() {
        return body.getLast().pos();
    }

    public void move(Direction direction) {
        CellPosition currentHeadPos = getHeadPos();
        CellPosition newHeadPos = direction.move(currentHeadPos);
      
        GridCell<Character> cell = new GridCell<>(newHeadPos, this.symbol);

        // You'll need to adjust this to handle the snake's body and ensure you're not just moving the head.
        this.body.pollLast();
        this.body.addFirst(cell);
    }

    public void grow(CellPosition pos){
        body.addFirst(new GridCell<>(pos, 'S'));
        
    }

    /**
     * Compares the GamePlayer object with the specified object for equality.
     *
     * Returns true if the specified object is also a GamePlayer object and
     * has the same symbol and position as this GamePlayer object.
     *
     * @param o the object to be compared for equality with this GamePlayer
     * @return true if the specified object is equal to this GamePlayer; false
     *         otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Snake)) {
            return false;
        }
        Snake playerO = (Snake) o;
        boolean playerBool = playerO.symbol == this.symbol;
        return playerBool
                && Objects.equals(getHeadPos(), playerO.getHeadPos());
    }

    /**
     * Returns the symbol that represents the player on the game board.
     *
     * @return The symbol that represents the player on the game board.
     */
    public char getPlayerChar() {
        return this.symbol;
    }


    /**
     * Sets the symbol that represents the player on the game board to a given
     * value.
     *
     * @param value The new value to set the player symbol to.
     */
    public void setCharToPlayer(char value) {
        this.symbol = value;
    }

    }



    
