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

public class SnakeView extends JPanel {
    private ViewableSnakeView snakeModel;
    private ColorTheme colorTheme;
    private static final int INNERMARGIN = 2;
    private static final int OUTERMARGIN = 2;
    private JFrame mainFrame;

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
                drawReplayText(g2);

            }
        }
    }

    public JFrame getFrame() {
        if (mainFrame == null) {
            mainFrame = new JFrame("SNAKE GAME");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new panel with BoxLayout for the menu or additional content
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Example: Adding some content to the side panel
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
                +"quit game: [Q]" + "<br>"
                +"Main Menu: [M]"+ "</html>");
                
        keys.setFont(new Font("Arial", Font.PLAIN, 18));
        // keys.setForeground(ColorTheme.menuFont);
        keys.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        // You can add more components to the panel here

        // Add the game view (this panel) to the center of the frame
        mainFrame.add(this, BorderLayout.CENTER);

        // Add the menu (panel) to the right of the frame
        mainFrame.add(panel, BorderLayout.EAST);

        panel.add(keys);

        mainFrame.pack(); // Adjusts frame to fit the preferred size and layouts of its subcomponents
        mainFrame.setLocationRelativeTo(null); // Centers the frame on the screen
        mainFrame.setVisible(true); // Makes the frame visible

    
    }
        return mainFrame;
}

    /**
     * Close the JFrame
     */
    public void closeWindow() {
        mainFrame.setVisible(false);
    }
    
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

        for (GridCell<Character> cell : cells) {
            Rectangle2D cellBounds = converter.getBoundsForCell(cell.pos());
            Color cellColor = colorTheme.getCellColor(cell.value());
            g2.setColor(cellColor);
            g2.fill(cellBounds);
            // Hvis cellen er slangehodet, tegn en spesiell farge og fyll hele cellen
            if (cell.pos().equals(snakeModel.getHeadPos())) {
                g2.setColor(Color.PINK);
                g2.fill(cellBounds);
            }
            // Hvis cellen er en eple, tegn eplebildet
            else if (cell.value() == 'A') {
                g2.drawImage(appleSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);

            }
            // Hvis cellen er en del av slangen (ikke hodet), tegn slangebildet litt mindre
            // enn hele ruten
            else if (cell.value() == 'S') {
                int inset = 1; // som over
                g2.drawImage(snakeSymbol.getImage(),
                        (int) cellBounds.getX() + inset,
                        (int) cellBounds.getY() + inset,
                        (int) cellBounds.getWidth() - 2 * inset,
                        (int) cellBounds.getHeight() - 2 * inset, null);
            }
            else if (cell.value()=='O'){
                g2.drawImage(bombSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(),
                        (int) cellBounds.getWidth(),
                        (int) cellBounds.getHeight(), null);

            }
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 15));

        String text = "Score: " + snakeModel.getscore();
        FontMetrics fm = g2.getFontMetrics();

        // Sette x til en liten marg fra venstre kant av skjermen
        int x = 10;
        int y = fm.getAscent() + 10; // Igjen, juster verdien som ønsket

        g2.drawString(text, x, y);
    }
    private void drawReplayText(Graphics2D g2) {
        // Create a gradient background
        GradientPaint gradientPaint = new GradientPaint(0, 0, new Color(181, 201, 154, 128), 0, getHeight(), new Color(120, 130, 120, 128), true);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());
    
        // Add a shadow to the text by drawing the text twice in offset positions
        int shadowOffset = 2;
        Color shadowColor = new Color(0, 0, 0, 150); // Semi-transparent black
        Color textColor = Color.WHITE;
    
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);
    
        // Shadow text
        g2.setColor(shadowColor);
        Inf101Graphics.drawCenteredString(
            g2, "Game Over", 20 + shadowOffset, 75 + shadowOffset, this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
            g2, "Your Final Score is: " + snakeModel.getscore(), 20 + shadowOffset, 125 + shadowOffset, this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
            g2, "PRESS ENTER TO PLAY AGAIN", 20 + shadowOffset, 175 + shadowOffset, this.getWidth() - 40, this.getHeight() - 540);
    
        g2.setColor(textColor);
        Inf101Graphics.drawCenteredString(
            g2, "Game Over", 20, 75, this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
            g2, "Your Final Score is: " + snakeModel.getscore(), 20, 125, this.getWidth() - 40, this.getHeight() - 540);
        Inf101Graphics.drawCenteredString(
            g2, "PRESS ENTER TO PLAY AGAIN", 20, 175, this.getWidth() - 40, this.getHeight() - 540);
    }

    private void drawStartscreen(Graphics2D g2) {
        // image
        ImageIcon originalIcon = new ImageIcon(Inf101Graphics.loadImageFromResources("/Snake.png"));
        Image originalImage = originalIcon.getImage();
    
        // Draw the background image
        g2.drawImage(originalImage, 0, 0, getWidth(), getHeight(), this);
    
        // Set semi-transparent overlay for text background for readability
        g2.setColor(new Color(0, 0, 0, 123)); // Semi-transparent black
        int overlayHeight = 150; // Example height, adjust as needed
        int overlayYPosition = (getHeight() - overlayHeight) / 2;
        g2.fillRect(0, overlayYPosition, getWidth(), overlayHeight); // Full width overlay
    
        // Set up the text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        String message = "Press SPACE to begin!";
    
        // Calculate width and height of the text to be drawn, to position it in the center
        FontMetrics metrics = g2.getFontMetrics();
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent(); // Adjust y to center the text vertically
    
        // Draw the text
        g2.drawString(message, x, y);
    }
    
    

    public void drawPauseScreen(Graphics2D g2) {
        // Semi-transparent overlay for focused area
        g2.setColor(new Color(0, 0, 0, 123)); // Semi-transparent black
        int overlayWidth = this.getWidth() - 80; // Adjust the width for the overlay
        int overlayHeight = 200; // Define the height for the overlay
        int overlayX = 40; // Horizontal starting point (padding from the left)
        int overlayY = (this.getHeight() - overlayHeight) / 2; // Vertical starting point (centered)
        g2.fillRect(overlayX, overlayY, overlayWidth, overlayHeight);
    
        g2.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);
    
        // Assuming drawCenteredString correctly centers text within a given rectangle
        // The text positions are now relative to the overlay, not the whole screen
        int textYStart = overlayY + 20; // Start drawing text a bit below the top of the overlay
        int lineHeight = g2.getFontMetrics().getHeight(); // Calculate line height for the font
        Inf101Graphics.drawCenteredString(g2, "PAUSED", overlayX, textYStart, overlayWidth, lineHeight);
        Inf101Graphics.drawCenteredString(g2, "Press 'SPACE' to resume", overlayX, textYStart + lineHeight, overlayWidth, lineHeight);
        Inf101Graphics.drawCenteredString(g2, "Your score is: " + snakeModel.getscore(), overlayX, textYStart + 2 * lineHeight, overlayWidth, lineHeight);
    }


}

