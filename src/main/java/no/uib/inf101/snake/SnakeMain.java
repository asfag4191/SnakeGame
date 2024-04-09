package no.uib.inf101.snake;

import javax.swing.JFrame;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.controller.SnakeController;
import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.SnakeModel;
import no.uib.inf101.snake.view.SnakeView;
import no.uib.inf101.snake.view.Screens.MainMenu;
import no.uib.inf101.snake.snake.Snake;

public class SnakeMain {
    public static final String WINDOW_TITLE = "INF101 Snake Game";

    public static void main(String[] args) {
        // First, display the main menu
        MainMenu menu = new MainMenu();

        // Assuming you want to start the game directly for testing,
        // you should initialize the model, view, and controller here correctly.
        // Uncomment and correct the following lines based on your actual constructors.

        /*
        // Create the game board
        SnakeBoard snakeBoard = new SnakeBoard(20, 20);
        
        // Create the snake
        Snake snake = new Snake('S', new CellPosition(10, 10));
        
        // Create the model
        SnakeModel snakeModel = new SnakeModel(snakeBoard, snake);
        
        // Create the view
        SnakeView view = new SnakeView(snakeModel);
        
        // Create the controller
        SnakeController controller = new SnakeController(snakeModel, view);
        
        // Setup the game window
        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
        */
    }
}

