package no.uib.inf101.snake.view.Screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import no.uib.inf101.snake.view.SnakeView;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.snake.controller.SnakeController;
import no.uib.inf101.snake.model.SnakeBoard;
import no.uib.inf101.snake.model.SnakeModel;
import no.uib.inf101.snake.snake.Snake;


public class MainMenu extends JFrame implements ActionListener {

    private final JButton play;
    private final JFrame frame;

    public MainMenu() {
		frame = new JFrame();
		frame.setTitle("Welcome to Snake!");

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(new EmptyBorder(20, 20,20, 20)); 

		Color bcg = new Color(181, 201, 154);
		contentPane.setBackground(bcg);

		// buttons
        contentPane.add(Box.createVerticalGlue());

		play = addButton(contentPane, "Start game");
	  play.setAlignmentX(Component.CENTER_ALIGNMENT);

		// spacing
		contentPane.add(Box.createVerticalGlue());

		// image
		//ImageIcon imageIcon = new ImageIcon(Inf101Graphics.loadImageFromResources("/Snake.png"));
		//JLabel imageLabel = new JLabel(imageIcon);
		//imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //contentPane.add(imageLabel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(contentPane);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Adds buttons with a fixed style
	 * 
	 * @param buttons
	 * @param name
	 * @return
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
		if (e.getSource() == play) {
			frame.dispose(); // closes the current screen to avoid having multiple screens open at the same time

			Snake snake = new Snake('S', new CellPosition(10, 10));
  
			SnakeBoard board = new SnakeBoard(15, 15);
			SnakeModel model = new SnakeModel(board, snake);
            SnakeView view = new SnakeView(model);
			SnakeController controller = new SnakeController(model, view);
            view.getFrame();
        } 
	
        
	}

	/**
	 * Shows the frame.
	 * Is called from the other screens in order to go back to the main menu.
	 */
	public void show() {
		frame.setVisible(true);
	}

    
}
