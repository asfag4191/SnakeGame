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

     MainMenu menu = new MainMenu();
  
 



    //Snakemodel snakeModel = new Snakemodel(null, null); // Oppretter modellen
    //SnakeView view = new SnakeView(snakeModel);
    //SnakeController controller = new SnakeController(snakeModel, view);
    //SnakeView view = new SnakeView(snakeBoard, Snake);
    
    //Snakemodel snakeModel= new Snakemodel(snakeBoard);
    //JFrame frame = new JFrame(WINDOW_TITLE);
    
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // Here we set which component to view in our window
    //frame.setContentPane(view);
    
    // Call these methods to actually display the window
    //frame.pack();
    //frame.setVisible(true);
  }
  
}
