package no.uib.inf101.snake.view;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
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
    private ColorTheme colorTheme; // Legg til et felt for ColorTheme
    private static final int INNERMARGIN = 2;
    private static final int OUTERMARGIN = 2;

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
          }
         

    if (snakeModel.getGameState() == GameState.GAME_OVER) {
        drawReplayText(g2);

    }
}
    
//sjekke denne, er feil 
public JFrame getFrame() {
    JFrame frame = new JFrame("SNAKE GAME");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        + "restart: [ENTER]" + "<br>"
        + "quit: [Q] </html>");
        keys.setFont(new Font("Arial", Font.PLAIN, 18));
        //keys.setForeground(ColorTheme.menuFont);
        keys.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    // You can add more components to the panel here

    // Add the game view (this panel) to the center of the frame
    frame.add(this, BorderLayout.CENTER);

    // Add the menu (panel) to the right of the frame
    frame.add(panel, BorderLayout.EAST);

    panel.add(keys); 

    frame.pack(); // Adjusts frame to fit the preferred size and layouts of its subcomponents
    frame.setLocationRelativeTo(null); // Centers the frame on the screen
    frame.setVisible(true); // Makes the frame visible
    
    return frame;
}
    


    private void drawGame(Graphics2D g2) {
        double margin =0; // rammetykkelse
        double x = INNERMARGIN;
        double y = OUTERMARGIN;
      
        double width = getWidth() - 2 * OUTERMARGIN;
        double height = getHeight() - 2 * INNERMARGIN;
        Rectangle2D frameRectangle = new Rectangle2D.Double(x, y, width, height);

        g2.setColor(colorTheme.getFrameColor());
        g2.fill(frameRectangle);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(frameRectangle,
        snakeModel.getDimension(),margin);

        drawCells(g2, snakeModel.getTilesOnBoard(), converter, colorTheme);
        drawCells(g2, snakeModel.getSnake(),converter, colorTheme);

        // Resten av tegnekoden din...
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

  for (GridCell<Character> cell : cells) {
      Rectangle2D cellBounds = converter.getBoundsForCell(cell.pos());
      Color cellColor = colorTheme.getCellColor(cell.value());
      g2.setColor(cellColor);
      g2.fill(cellBounds);
        if (cell.pos() == snakeModel.getHeadPos())
              {
                  g2.setColor(Color.PINK);
                  g2.fill(cellBounds);
              }
        // Hvis cellen er en eple, tegn eplebildet
        else if (cell.value() == 'A') {
          g2.drawImage(appleSymbol.getImage(), (int) cellBounds.getX(), (int) cellBounds.getY(), (int) cellBounds.getWidth(), 
          (int) cellBounds.getHeight(), null);
      }
      // Hvis cellen er en del av slangen, tegn slangebildet
      else if (cell.value() == 'S') {
        g2.drawImage(snakeSymbol.getImage(), (int) cellBounds.getX(),  (int) cellBounds.getY(), (int) cellBounds.getWidth(), 
         (int) cellBounds.getHeight(),  null);
      }
      

    }
}
private void drawScore(Graphics2D g2) {
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.BOLD, 15));

    String text = "Score: " + snakeModel.getscore();
    FontMetrics fm = g2.getFontMetrics();
    
    // Sette x til en liten marg fra venstre kant av skjermen
    int x = 10; // Du kan justere denne verdien for å endre margen om ønskelig
    // Sette y til font høyden pluss en liten marg for å unngå å tegne teksten for nær på toppen av skjermen
    int y = fm.getAscent() + 10; // Igjen, juster verdien som ønsket

    g2.drawString(text, x, y);
}

private void drawReplayText(Graphics2D g2) {
  // Set a semi-transparent overlay
  g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
  g2.setColor(new Color(58, 90, 64)); // Semi-transparent green
  g2.fillRect(0, 0, getWidth(), getHeight());
  
  // Reset composite to draw opaque text
  g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
  
  // Set the color and font for the text
  g2.setColor(Color.WHITE);
  g2.setFont(new Font("Arial", Font.BOLD, 24));
  FontMetrics fm = g2.getFontMetrics();
  FontMetrics gameOverFm = g2.getFontMetrics(new Font("Arial", Font.BOLD, 30));
    
    // Calculate positions
    int gameOverY = (getHeight() - gameOverFm.getHeight()) / 2 + gameOverFm.getAscent();
    int spacing = 50;
    
    // Draw "Game Over" text
    String gameOverText = "Game Over";
    int gameOverTextX = (getWidth() - gameOverFm.stringWidth(gameOverText)) / 2;
    g2.drawString(gameOverText, gameOverTextX, gameOverY);
    
    // Draw final score text
    String finalScoreText = "Your Final Score is: " + snakeModel.getscore();
    int finalScoreY = gameOverY + gameOverFm.getHeight() + spacing;
    int finalScoreX = (getWidth() - fm.stringWidth(finalScoreText)) / 2;
    g2.drawString(finalScoreText, finalScoreX, finalScoreY);
    
    // Draw "PRESS ENTER TO PLAY AGAIN" text
    String replayText = "PRESS ENTER TO PLAY AGAIN";
    int replayTextY = finalScoreY + fm.getHeight() + spacing;
    int replayTextX = (getWidth() - fm.stringWidth(replayText)) / 2;
    g2.drawString(replayText, replayTextX, replayTextY);
}


  private void drawStartscreen(Graphics2D g2) {
    g2.setColor(new Color(58, 90, 64));
    g2.fillRect(0, 0, getWidth(), getHeight());

    g2.setColor(Color.WHITE);
    Font str = new Font("Arial", Font.BOLD, 40);
    g2.setFont(str);
    Inf101Graphics.drawCenteredString(
        g2, "Press SPACE",
        20, 75, this.getWidth() - 40, this.getHeight() - 540);
    Inf101Graphics.drawCenteredString(
        g2, "to begin!",
        20, 125, this.getWidth() - 40, this.getHeight() - 540);
}
}


