package no.uib.inf101.snake.view.Screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import no.uib.inf101.snake.view.Inf101Graphics;
import no.uib.inf101.snake.view.SnakeView;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.controller.SnakeController;
import no.uib.inf101.snake.model.GameState;
import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.SnakeModel;
import no.uib.inf101.snake.snake.Snake;

/**
 * The MainMenu class creates and displays the main menu screen
 * for the Snake game. This screen allows players to choose between
 * different game modes before starting the game.
 */
public class MainMenu extends JFrame implements ActionListener {

	private final JButton playNormal;
	private final JButton playHard;

	/**
	 * Constructs the MainMenu frame with all UI components necessary
	 * for starting the game. Initializes buttons to select game mode
	 * and customizes the panel's appearance.
	 */
	public MainMenu() {
		this.setTitle("Welcome to Snake!");

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		Color bcg = new Color(181, 201, 154);
		contentPane.setBackground(bcg);
		contentPane.add(Box.createVerticalGlue());

		playNormal = addButton(contentPane, "Start Normal Game");
		playNormal.setAlignmentX(Component.CENTER_ALIGNMENT);

		playHard = addButton(contentPane, "Start Hard Game");
		playHard.setAlignmentX(Component.CENTER_ALIGNMENT);

		contentPane.add(Box.createVerticalGlue());

		ImageIcon originalIcon = new ImageIcon(Inf101Graphics.loadImageFromResources("/Snake.png"));
		Image originalImage = originalIcon.getImage();

		int Width = 600;
		int Height = 400;
		Image resizedImage = originalImage.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);

		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		JLabel imageLabel = new JLabel(resizedIcon);
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		contentPane.add(imageLabel);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(contentPane);
		this.setPreferredSize(new Dimension(800, 600));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Adds a button to the specified {@link JPanel} with a given name.
	 * Sets up the alignment, font, and action listener for the button.
	 *
	 * @param buttons The panel to which the button will be added.
	 * @param name    The text displayed on the button.
	 * @return The newly created button.
	 */
	JButton addButton(JPanel buttons, String name) {
		JButton button = new JButton();
		button.setText(name);
		button.setFont(new Font("Arial", Font.PLAIN, 25));
		button.addActionListener(this);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttons.add(button);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playNormal) {
			startGame(GameState.NORMAL_MODE_SELECTED);
		} else if (e.getSource() == playHard) {
			startGame(GameState.HARD_MODE_SELECTED);

		}
	}

	/**
	 * Starts a new game with selected mode. Hides the main menu and
	 * initializes the game with the selected game mode.
	 *
	 * @param mode The game mode to start, represented by a {@link GameState}.
	 */
	public void startGame(GameState mode) {
		this.setVisible(false); // Closes MainMenu

		Snake snake = new Snake('S', new CellPosition(10, 10));
		SnakeBoard board = new SnakeBoard(15, 15);
		SnakeModel model = new SnakeModel(board, snake);

		model.setGameMode(mode);

		SnakeView view = new SnakeView(model);
		SnakeController controller = new SnakeController(model, view, this);

		view.getFrame();
	}
}
