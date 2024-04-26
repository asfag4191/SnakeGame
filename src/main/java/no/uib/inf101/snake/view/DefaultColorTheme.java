package no.uib.inf101.snake.view;

import java.awt.Color;

/**
 * Provides a default color theme for the Snake game, defining colors for various game elements.
 * This implementation of the ColorTheme interface specifies colors for cells based on their contents,
 * as well as colors for the game's frame, background, and menu fonts.
 */
public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(char Cellcontent) {
        Color color = switch (Cellcontent) {
            case 'S' -> getBackgroundColor();
            case 'A' -> getBackgroundColor();
            case '-' -> getBackgroundColor();
            case 'O' -> getBackgroundColor();
            case 'P' -> getBackgroundColor();
            default -> throw new IllegalArgumentException(
                    "No available color for '" + Cellcontent + "-");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return null;
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(113, 131, 85);
    }

    @Override
    public Color getMenuFont() {
        return new Color(144, 238, 144, 123);
    }
}
