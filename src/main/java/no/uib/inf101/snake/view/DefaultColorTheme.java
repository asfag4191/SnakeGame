package no.uib.inf101.snake.view;

import java.awt.Color;

/**
 * Default color theme for the Tetris game.
 */
public class DefaultColorTheme implements ColorTheme {

    private Color boardColor = new Color(0);
    private Color frameColor = new Color(255);
    public Color snakeColor = new Color(113, 131, 85);
    public Color snakeHead = new Color(188, 71, 73);
    public Color menuFont = new Color(144, 238, 144, 123);;

    @Override
    public Color getCellColor(char Cellcontent) {
        Color color = switch (Cellcontent) {
            case 'S' -> boardColor;
            case 'A' -> boardColor;
            case '-' -> boardColor;
            case 'B' -> snakeHead;
            
            default -> throw new IllegalArgumentException(
                    "No available color for '" + Cellcontent + "-");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return frameColor;
    }

    @Override
    public Color getBackgroundColor() {
        return boardColor;
    }

    @Override
    public Color getMenuFont() {
        return menuFont;
    }

}
