package no.uib.inf101.snake.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import no.uib.inf101.snake.midi.SnakeSong;
import no.uib.inf101.snake.model.Direction;
import no.uib.inf101.snake.model.GameState;
import no.uib.inf101.snake.view.SnakeView;
import no.uib.inf101.snake.view.Screens.MainMenu;


/**
 * Controller for Snake game, tracking user input and updating the model
 * accordingly.
 * Implements KeyListener and ActionListener.
 */
public class SnakeController implements KeyListener, ActionListener {
    private final ControlleableSnake snakeModel;
    private final SnakeView snakeView;
    private final Timer timer;
    private final Timer timerObstacle;
    private final Timer timerPapple;
    private MainMenu mainMenu;
    private SnakeSong snakeSong;



    /**
     * Constructs a SnakeController with the specified parameters.
     * 
     * @param controller The snake controller.
     * @param view       The snake view.
     * @param mainMenu   The main menu of the game.
     */
    public SnakeController(ControlleableSnake controller, SnakeView view, MainMenu mainMenu) {
        this.snakeModel = controller; // Use the 'controller' parameter
        this.snakeView = view;
        this.mainMenu = mainMenu;

        this.snakeSong = new SnakeSong();
        snakeView.addKeyListener(this);
        snakeView.setFocusable(true);

        this.timer = new Timer(snakeModel.delayTimer(), this::clockTickDelay);
        this.timerObstacle = new Timer(snakeModel.obstacleTimer(), this::clockTickObstacle);
        this.timerPapple = new Timer(snakeModel.pappleTimer(), this::clockTickPapple);

        this.timer.start();
        this.timerObstacle.start();
        this.timerPapple.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (snakeModel.getGameState()) {
            case ACTIVE_GAME:
                handleActive(e);
                break;
            case GAME_OVER:
                handleGameOver(e);
                handleQuit(e);
                showMainMenu(e);
                break;
            case PAUSE_GAME:
                handlePause(e);
                handleStart(e);
                handleQuit(e);
                break;
            case START_GAME:
                handleStart(e);
                break;
            case HARD_MODE_SELECTED:
                break;
            case NORMAL_MODE_SELECTED:
                break;
            default:
                break;

        }
    }

    private void handleActive(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                snakeModel.setDirection(Direction.WEST);
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
            case KeyEvent.VK_P:
                handlePause(e);
                break;
            case KeyEvent.VK_Q:
                handleQuit(e);
                break;
            case KeyEvent.VK_M:
                showMainMenu(e);
                break;
        }
        snakeView.repaint();
    }

    /**
     * Method for handling the start function.
     * 
     * @param e The KeyEvent.
     */
    private void handleStart(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snakeSong.run();
            snakeModel.setGameScreen(GameState.ACTIVE_GAME);
            snakeView.repaint();
        }
    }

    /**
     * Method for handling the quit function.
     * 
     * @param e The KeyEvent.
     */
    private void handleQuit(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
        snakeView.repaint();
    }

    /**
     * Method for handling the game over function.
     * 
     * @param e The KeyEvent.
     */
    private void handleGameOver(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            snakeModel.resetGame();
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_M) {
            showMainMenu(e);
        }
        snakeView.repaint();
    }

    /**
     * Method for showing the main menu, and also close the game window.
     * 
     * @param e The KeyEvent.
     */
    private void showMainMenu(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            if (mainMenu != null) {
                mainMenu.setVisible(true); // Viser hovedmenyen
                snakeView.setVisible(false);
                snakeView.closeWindow(); // Lukker spillvinduet
                snakeModel.setGameScreen(GameState.START_GAME);
            }
        }
    }
    

    /**
     * Method for handling the pause function. 
     * 
     * @param e
     */
    public void handlePause(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            snakeModel.setGameScreen(GameState.PAUSE_GAME);
            snakeSong.doPauseMidiSounds();
            snakeView.repaint();
        }
    }

    /**
     * Method for updating the timer delay.
     */
    private void updateTimerDelay() {
        int newDelay = snakeModel.delayTimer();
        if (timer.getDelay() != newDelay) {
            timer.setDelay(newDelay);
            timer.setInitialDelay(0);
        }
    }

    /**
     * Method for updating the obstacle timer.
     */
    private void updateObstacleTimer() {
        int newDelay = snakeModel.obstacleTimer();
        if (timerObstacle.getDelay() != newDelay) {
            timerObstacle.setDelay(newDelay);
            timerObstacle.setInitialDelay(0);
        }
    }

    /**
     * Method for updating the poisonous apple timer.
     */
    private void updatePappleTimer() {
        int newDelay = snakeModel.pappleTimer();
        if (timerPapple.getDelay() != newDelay) {
            timerPapple.setDelay(newDelay);
            timerPapple.setInitialDelay(0);
        }
    }

    /**
     * Method for handling the clock tick delay when the game is active.
     * 
     * @param e The ActionEvent.
     */
    private void clockTickDelay(ActionEvent e) {
        if (e.getSource() == timer) {
            if (snakeModel.getGameState() == GameState.ACTIVE_GAME) {
                snakeModel.clockTickDelay();
                updateTimerDelay();
                snakeView.repaint();
            }
        }
    }

    /**
     * Method for handling the clock tick obstacle when the game is active, and
     * the hard mode is selected.
     * 
     * @param e The ActionEvent.
     */
    private void clockTickObstacle(ActionEvent e) {
        if (e.getSource() == timerObstacle && snakeModel.getGameState() == GameState.ACTIVE_GAME
                && snakeModel.isHardMode()) {
            snakeModel.clockTickObstacle();
            updateObstacleTimer();
            snakeView.repaint();
        }
    }

    /**
     * Method for handling the clock tick poisonous apple when the game is active,
     * and
     * the hard mode is selected.
     * 
     * @param e The ActionEvent.
     */
    private void clockTickPapple(ActionEvent e) {
        if (e.getSource() == timerPapple && snakeModel.getGameState() == GameState.ACTIVE_GAME
                && snakeModel.isHardMode()) {
            snakeModel.clockTickPapple();
            updatePappleTimer();
            snakeView.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}