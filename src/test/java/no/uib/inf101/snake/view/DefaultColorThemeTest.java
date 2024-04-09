package no.uib.inf101.snake.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class DefaultColorThemeTest {
  @Test
  public void sanityDefaultColorThemeTest() {
    ColorTheme colors = new DefaultColorTheme();
    assertEquals(Color.BLACK, colors.getBackgroundColor());
    assertEquals(new Color(0,0,255), colors.getFrameColor());
    assertEquals(Color.BLACK, colors.getCellColor('-'));
    assertEquals(Color.BLACK, colors.getCellColor('A'));
    assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
  }

}
