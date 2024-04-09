package no.uib.inf101.snake.view;

import java.awt.Color;

/**
 * Interface for color themes.
 */
public interface ColorTheme {

    /**
     * Gets the color associated with the given cell content character.
     * 
     * @param Cellcontent The character representing the cell content.
     * @return The color for the cell.
     */
    Color getCellColor(char Cellcontent);

    /**
     * 
     * @return the color of the frame.
     */
    Color getFrameColor();

    /**
     * 
     * @return the backgorund color .
     */
    Color getBackgroundColor();

    /**
     * @return the color of the menu font.
     */
    Color getMenuFont();
}
