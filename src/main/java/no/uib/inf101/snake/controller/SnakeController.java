package no.uib.inf101.snake.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.text.View;

import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;
import no.uib.inf101.snake.model.SnakeModel;
import no.uib.inf101.snake.view.SnakeView;

public class SnakeController implements KeyListener, ActionListener{
    private final ControlleableSnake snakeModel;
    private final SnakeView snakeView;
    private final Timer timer;

    public SnakeController(ControlleableSnake controller, SnakeView view) {
        this.snakeModel = controller; // Use the 'controller' parameter
        this.snakeView = view; // Use the 'view' parameter
        snakeView.addKeyListener(this);
        snakeView.setFocusable(true);
        this.timer = new Timer(snakeModel.delayTimer(), this::clockTick);
        timer.start();

        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (snakeModel.getGameState()) {  // endret fra ControlleableSnake.getGameScreen()
            case ACTIVE_GAME:
                handleActive(e);
                break;
            case GAME_OVER:
                handleGameOver(e);
                break;
            case PAUSE:
                handlePause(e);
                break;
            case START_GAME:
                handleStart(e);
                break;

        }
    }



    private void handleStart(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snakeModel.setGameScreen(GameState.ACTIVE_GAME);
            snakeView.repaint();
        }
    }
    

//@Override
public void handleActive(KeyEvent e) {
    switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            snakeModel.setDirection(Direction.WEST); // Change this to moveSnake(Direction.WEST
            break;
        case KeyEvent.VK_RIGHT:

            snakeModel.setDirection(Direction.EAST);
            break;
        case KeyEvent.VK_DOWN:
            snakeModel.setDirection(Direction.SOUTH);
            break;
        case KeyEvent.VK_UP:
            snakeModel.setDirection(Direction.NORTH);
            break;
    }
    snakeView.repaint();
}

public void handleGameOver(KeyEvent e) {
    switch (e.getKeyCode()) {
        // Restart
        case KeyEvent.VK_ENTER:
            snakeModel.resetGame();
            break;

        // Quit
        case KeyEvent.VK_Q:
            System.exit(0);
            break;
    }
    snakeView.repaint();
}

public void handlePause(KeyEvent e){
    snakeModel.setGameScreen(GameState.PAUSE);
}


    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void updateTimerDelay() {
        int delay = snakeModel.delayTimer();
        timer.setDelay(delay);
        timer.setInitialDelay(delay);
    }
    
    public void clockTick(ActionEvent e) {
        if (snakeModel.getGameState() == GameState.ACTIVE_GAME) {
            snakeModel.clockTick();
            snakeView.repaint();
            updateTimerDelay();
        }

    }

}