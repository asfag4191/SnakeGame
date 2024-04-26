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
 * Controller for the Snake game, managing the interaction between user inputs
 * and the game model.
 * Implements {@link KeyListener} to handle keyboard events and
 * {@link ActionListener}.
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
     * Constructs a SnakeController with specified game model, view, and menu.
     * 
     * @param controller The snake controller, manipulates based on user input.
     * @param view       The snake view, updates as the model changes.
     * @param mainMenu   The main menu of the game, navigating game states.
     */
    public SnakeController(ControlleableSnake controller, SnakeView view, MainMenu mainMenu) {
        this.snakeModel = controller;
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
                showMainMenu(e);
                break;
            case START_GAME:
                handleStart(e);
                break;
            default:
                break;

        }
    }

    /**
     * Handles key press events during the game's active state. Such as moving the
     * snake in different directions, pausing the game, quitting the game, or
     * returning to the main menu.
     * 
     * @param e Keyevent triggered by user interaction.
     */
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
     * Method for handling the start of the game, start the theme song and set the
     * state to Active game.
     * 
     * @param e The KeyEvent triggered when the user press space.
     */
    private void handleStart(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snakeSong.run();
            snakeModel.setGameScreen(GameState.ACTIVE_GAME);
        }
    }

    /**
     * Method for handling the quit function, closing the game window.
     * 
     * @param e The KeyEvent triggered when pressed 'q'.
     */
    private void handleQuit(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }
        snakeView.repaint();
    }

    /**
     * Method for handling the game over state, allowing the user to either resume
     * the game, quit the game or return to the main menu.
     * 
     * @param e The KeyEvent triggered by the user, "m" for main menu, "q" for quit,
     *          "Enter" for "restart game".
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
     * Displays the main menu and hides the game view when the 'M' key is pressed.
     * It sets the game state to START_GAME,
     * making the main menu visible and hiding the current game view.
     * 
     * @param e The KeyEvent "m" triggered by the user.
     */
    private void showMainMenu(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_M) {
            if (mainMenu != null) {
                mainMenu.setVisible(true);
                snakeView.setVisible(false);
                snakeView.closeWindow();
                snakeModel.setGameScreen(GameState.START_GAME);
            }
        }
    }

    /**
     * Method for handling the pause function, pausing the game and the theme song.
     * 
     * @param e The KeyEvent triggered by the user, "p" for pause.
     */
    private void handlePause(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            snakeModel.setGameScreen(GameState.PAUSE_GAME);
            snakeSong.doPauseMidiSounds();
            snakeView.repaint();
        }
    }

    /**
     * Updates the main game timer delay based on the current settings from the game
     * model.
     */
    private void updateTimerDelay() {
        int newDelay = snakeModel.delayTimer();
        if (timer.getDelay() != newDelay) {
            timer.setDelay(newDelay);
            timer.setInitialDelay(0);
        }
    }

    /**
     * Updates the obstacle timer delay to align with the current game state.
     */
    private void updateObstacleTimer() {
        int newDelay = snakeModel.obstacleTimer();
        if (timerObstacle.getDelay() != newDelay) {
            timerObstacle.setDelay(newDelay);
            timerObstacle.setInitialDelay(0);
        }
    }

    /**
     * Updates the timer delay for generating poisonous apples in the game.
     */
    private void updatePappleTimer() {
        int newDelay = snakeModel.pappleTimer();
        if (timerPapple.getDelay() != newDelay) {
            timerPapple.setDelay(newDelay);
            timerPapple.setInitialDelay(0);
        }
    }

    /**
     * Method for handling the clock tick delay when the game is active, updating
     * the game state and the timer delay. (moving the snake).
     * 
     * @param e The ActionEvent triggered by the timer.
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
     * Handles updates for obstacle generation based on the obstacle timer ticks
     * when the game
     * is in hard mode and active.
     * 
     * @param e The ActionEvent triggered by the obstacle timer.
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
     * Handles the timing for generating poisonous apples in the game when it is in
     * hard mode
     * and active.
     * 
     * @param e The ActionEvent triggered by the poisonous apple timer.
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