package no.uib.inf101.snake;

import no.uib.inf101.snake.view.Screens.MainMenu;

/**
 * Main class for the Snake game, launching the user interface and initializing game components.
 * This class contains the main method which serves as the entry point for the application,
 * setting up the initial user interface and making the game ready for player interaction.
 */
public class SnakeMain {
    
    public static final String WINDOW_TITLE = "INF101 Snake Game";

    /**
     * Main method that starts the Snake game by initializing and displaying the main menu.
     * This is where the game loop starts, from the display of the main menu to the start
     * of game play.
     *
     * @param args Command-line arguments passed during the start of the program.
     */
    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
    }
}
