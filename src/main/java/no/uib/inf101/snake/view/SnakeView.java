package no.uib.inf101.snake.view;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Color;
import java.awt.BorderLayout;

import java.awt.Dimension;

import java.awt.Graphics;

import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import no.uib.inf101.grid.GridCell;
import no.uib.inf101.snake.model.GameState;

/**
 * This class represents the visual component of the Snake game, displaying the
 * game board, snake, and game states such as starting, pausing, and game over.
 */

public class SnakeView extends JPanel {
    private ViewableSnakeView snakeModel;
    private ColorTheme colorTheme;
    private JFrame mainFrame;
    private static final int INNERMARGIN = 2;
    private static final int OUTERMARGIN = 2;

    /**
     * Constructs a SnakeView instance with a specified model for game data.
     * It initializes the game interface, sets the color theme, and prepares the
     * game view.
     * 
     * @param snakeModel The snake model to be displayed for visual representation.
     */
    public SnakeView(ViewableSnakeView snakeModel) {
        setPreferredSize(new Dimension(800, 600));
        this.snakeModel = snakeModel;
        this.colorTheme = new DefaultColorTheme();
        this.setBackground(colorTheme.getBackgroundColor());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (snakeModel.getGameState() == GameState.START_GAME) {
            drawStartscreen(g2);
        } else {
            drawGame(g2);
            drawScore(g2);

            if (snakeModel.getGameState() == GameState.PAUSE_GAME) {
                drawPauseScreen(g2);
            }

            if (snakeModel.getGameState() == GameState.GAME_OVER) {
                drawGameOverScreen(g2);

            }
        }
    }

    /**
     * Draws the game elements on the game board. This includes the snake, apples,
     * and any obstacles, based on the positions from the snake model.
     * 
     * @param g2 context used for drawing the game elements.
     */
    private void drawGame(Graphics2D g2) {
        double margin = 0; // rammetykkelse
        double x = INNERMARGIN;
        double y = OUTERMARGIN;

        double width = getWidth() - 2 * OUTERMARGIN;
        double height = getHeight() - 2 * INNERMARGIN;
        Rectangle2D frameRectangle = new Rectangle2D.Double(x, y, width, height);

        g2.setColor(colorTheme.getFrameColor());
        g2.fill(frameRectangle);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(frameRectangle,
                snakeModel.getDimension(), margin);

        drawCells(g2, snakeModel.getTilesOnBoard(), converter, colorTheme);
        drawCells(g2, snakeModel.getSnake(), converter, colorTheme);
    }

    /**
     * Draws the invidual cells on the board.
     *
     * @param g2         Graphics 2D context used for drawing the cells.
     * @param cells      Iterable collection of Gridcell elements to be drawn.
     * @param converter  Converts cell positions to pixel positions.
     * @param colorTheme The color theme used for drawing the cells.
     */
    private void drawCells(Graphics2D g2, Iterable<GridCell<Character>> cells,
            CellPositionToPixelConverter converter, ColorTheme colorTheme) {

        ImageIcon appleSymbol = new ImageIcon(Inf101Graphics.loadImageFromResources("/apple.png"));
        ImageIcon snakeSymbol = new ImageIcon(Inf101Graphics.loadImageFromResources("/green-circle.png"));
        ImageIcon bombSymbol = new ImageIcon(Inf101Graphics.loadImageFromResources("/bomb.png"));
        ImageIcon headSymbol = new ImageIcon(Inf101Graphics.loadImageFromResources("/snakeHead.png"));
        ImageIcon goldenSymbol = new ImageIcon(Inf101Graphics.loadImageFromResources("/goldenApple.png"));

        for (GridCell<Character> cell : cells) {
            Rectangle2D cellBounds = converter.getBoundsForCell(cell.pos());
            Color cellColor = colorTheme.getCellColor(cell.value());
            g2.setColor(cellColor);
            g2.fill(cellBounds);

            if (cell.pos().equals(snakeModel.getHeadPos())) {
                g2.drawImage(headSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);
            } else if (cell.value() == 'A') {
                g2.drawImage(appleSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);

            } else if (cell.value() == 'S') {
                int inset = 2;
                g2.drawImage(snakeSymbol.getImage(),
                        (int) cellBounds.getX() + inset,
                        (int) cellBounds.getY() + inset,
                        (int) cellBounds.getWidth() - 2 * inset,
                        (int) cellBounds.getHeight() - 2 * inset, null);
            } else if (cell.value() == 'O') {
                g2.drawImage(bombSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);

            } else if (cell.value() == 'P') {
                g2.drawImage(goldenSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);
            }
        }
    }

    /**
     * Draw the current score on the game screen.
     * 
     * @param g2 context used for drawing the score.
     */
    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 15));

        String text = "Score: " + snakeModel.getscore();
        FontMetrics fm = g2.getFontMetrics();
        int x = 10;
        int y = fm.getAscent() + 10;

        g2.drawString(text, x, y);
    }

    /**
     * Draw the game over screen.
     * 
     * @param g2 context used for drawing the game over screen.
     */
    private void drawGameOverScreen(Graphics2D g2) {
        GradientPaint gradientPaint = new GradientPaint(
                0, 0, new Color(181, 201, 154, 128),
                0, getHeight(), new Color(113, 131, 85),
                true);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // add a shadow to the text
        int shadowOffset = 2;
        Color shadowColor = Color.RED.darker();
        Color textColor = Color.WHITE;
        Font font = new Font("Arial", Font.BOLD, 24);

        g2.setFont(font);

        g2.setColor(shadowColor);
        Inf101Graphics.drawCenteredString(
                g2, "Game Over",
                20 + shadowOffset, 75 + shadowOffset,
                this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
                g2, "Your Final Score is: " + snakeModel.getscore(),
                20 + shadowOffset, 125 + shadowOffset,
                this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
                g2, "PRESS ENTER TO PLAY AGAIN",
                20 + shadowOffset, 175 + shadowOffset,
                this.getWidth() - 40,
                this.getHeight() - 540);

        g2.setColor(textColor);
        Inf101Graphics.drawCenteredString(
                g2, "Game Over",
                20, 75, this.getWidth() - 40,
                this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
                g2, "Your Final Score is: " + snakeModel.getscore(),
                20, 125, this.getWidth() - 40,
                this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
                g2, "PRESS ENTER TO PLAY AGAIN",
                20, 175, this.getWidth() - 40,
                this.getHeight() - 540);
    }

    /**
     * Draw the start screen.
     * 
     * @param g2 context used for drawing the start screen.
     */
    private void drawStartscreen(Graphics2D g2) {
        ImageIcon originalIcon = new ImageIcon(Inf101Graphics.loadImageFromResources("/Snake.png"));
        Image originalImage = originalIcon.getImage();
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), this);

        Color overlayColor = new Color(0, 0, 0, 123);
        int overlayHeight = 150;
        int overlayYPosition = (getHeight() - overlayHeight) / 2;

        g2.setColor(overlayColor);
        g2.fillRect(0, overlayYPosition, getWidth(), overlayHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));

        String message = "Press SPACE to begin!";
        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        g2.drawString(message, x, y);
    }

    /**
     * Draw the pause screen.
     * 
     * @param g2 context used for drawing the pause screen.
     */
    public void drawPauseScreen(Graphics2D g2) {
        int overlayWidth = this.getWidth() - 80;
        int overlayHeight = 200;
        int overlayX = 40;
        int overlayY = (this.getHeight() - overlayHeight) / 2;

        g2.setColor(new Color(0, 0, 0, 123));
        g2.fillRect(overlayX, overlayY, overlayWidth, overlayHeight);

        g2.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);

        int textYStart = overlayY + 20;
        int lineHeight = g2.getFontMetrics().getHeight();

        Inf101Graphics.drawCenteredString(g2, "PAUSED", overlayX, textYStart, overlayWidth, lineHeight);
        Inf101Graphics.drawCenteredString(g2, "Press 'SPACE' to resume", overlayX, textYStart + lineHeight,
                overlayWidth, lineHeight);
        Inf101Graphics.drawCenteredString(g2, "Your score is: " + snakeModel.getscore(), overlayX,
                textYStart + 2 * lineHeight, overlayWidth, lineHeight);
    }

    /**
     * Creates a new frame to the right of the main frame, displaying the keys
     * that can be used to control the game.
     * 
     * @return The main frame of the game.
     */
    public JFrame getFrame() {
        if (mainFrame == null) {
            mainFrame = new JFrame("SNAKE GAME");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a new panel with BoxLayout for the menu or additional content
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel label = new JLabel("Side Menu");
            panel.add(label);

            JLabel keys = new JLabel("<html> Key menu:" + "<br>"
                    + "----------------" + "<br>"
                    + "move up: ⬆ " + "<br>"
                    + "move down: ⬇ " + "<br>"
                    + "move left: ⬅ " + "<br>"
                    + "move right: ⮕" + "<br>"
                    + "pause: [P]" + "<br>"
                    + "restart: [SPACE]" + "<br>"
                    + "quit game: [Q]" + "<br>"
                    + "Main Menu: [M]" + "</html>");

            keys.setFont(new Font("Arial", Font.PLAIN, 18));
            keys.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

            mainFrame.add(this, BorderLayout.CENTER);
            panel.add(keys);
            mainFrame.add(panel, BorderLayout.EAST);

            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);

        }
        return mainFrame;
    }

    /**
     * Close the main frame window.
     */
    public void closeWindow() {
        mainFrame.setVisible(false);
    }
}
