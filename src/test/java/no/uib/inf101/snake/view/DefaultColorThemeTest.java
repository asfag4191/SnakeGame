package no.uib.inf101.snake.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

public class DefaultColorThemeTest {
  @Test
  public void sanityDefaultColorThemeTest() {
      ColorTheme colors = new DefaultColorTheme();
      assertEquals(new Color(113, 131, 85), colors.getBackgroundColor());
      assertEquals(new Color(113, 131, 85), colors.getCellColor('-'));
      assertEquals(new Color(113, 131, 85), colors.getCellColor('A')); // Assuming 'A' color should be the same as '-'
      assertThrows(IllegalArgumentException.class, () -> colors.getCellColor('\n'));
  }
}
