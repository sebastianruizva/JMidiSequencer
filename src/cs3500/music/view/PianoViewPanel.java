package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * A view panel displaying a ten-octave keyboard. Notes that are currently selected by the
 * provided MusicEditorGUI will be illuminated orange.
 */
public class PianoViewPanel extends JLayeredPane {
  private MusicEditorGUI gui;

  /**
   * Constructs a new view panel with the provided {@link MusicEditorGUI}. If the GUI is determined
   * to be null, an exception is thrown.
   *
   * @param gui the GUI from which to draw the currently selected notes for illumination.
   * @throws IllegalArgumentException if the GUI is null.
   */
  public PianoViewPanel(MusicEditorGUI gui) throws IllegalArgumentException {
    if (gui == null) {
      throw new IllegalArgumentException("Cannot instantiate view panel with null GUI.");
    }

    this.gui = gui;
  }

  @Override
  public void paint(Graphics g) {
    setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH, 400));
    setMaximumSize(getPreferredSize());

    paintBackground(g);
    paintWhiteKeys(g);
    paintBlackKeys(g);
  }

  /**
   * Paints a rectangular background for the keyboard.
   *
   * @param g the graphics component with which to draw the keyboard.
   */
  private void paintBackground(Graphics g) {
    g.setColor(Color.DARK_GRAY);
    g.drawRect(0, 0, DrawValues.MIN_GRID_WIDTH, 400);
    g.fillRect(0, 0, DrawValues.MIN_GRID_WIDTH, 400);
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, DrawValues.MIN_GRID_WIDTH, 400);
  }

  /**
   * Paints all of the white keys on the keyboard onto the view, illuminating any if they're
   * selected by the model. This will paint ten octaves of white keys.
   *
   * @param g the graphics component with which to draw the keyboard.
   */
  private void paintWhiteKeys(Graphics g) {
    int size = DrawValues.RECTANGLE_H;
    int offset = 100;

    int whitePitchCount = 0;
    int skipCount = 2;
    int lastAdded = 3;

    for (int i = 0; i < 70; i++) {
      if (gui.getPitchesAtCursorPosition().contains(whitePitchCount)) {
        g.setColor(Color.ORANGE);
      } else {
        g.setColor(Color.WHITE);
      }
      g.fillRect(i * size + offset, 0, size, 350);
      g.setColor(Color.BLACK);
      g.drawRect(i * size + offset, 0, size, 350);

      if (i == skipCount) {
        whitePitchCount++;

        if (lastAdded == 3) {
          skipCount += 4;
          lastAdded = 4;
        } else {
          skipCount += 3;
          lastAdded = 3;
        }

      } else {
        whitePitchCount += 2;
      }
    }
  }

  /**
   * Paints all of the black keys in the keyboard onto the view, illuminating any if they're
   * selected by the model. This will paint ten octaves of black keys.
   *
   * @param g the graphics component with which to draw the keyboard.
   */
  private void paintBlackKeys(Graphics g) {
    int skip = 2;
    int oddCount = 0;
    int size = DrawValues.RECTANGLE_H;
    int offset = 100;

    int keyCount = 0;
    int blackPitchCount = 1;
    int skipCount = 1;
    int lastAdded = 3;

    for (int i = 0; i < 70; i++) {
      if (i == skip) {
        if (oddCount % 2 == 0) {
          skip += 4;
        } else {
          skip += 3;
        }
        oddCount++;
        continue;
      }

      if (gui.getPitchesAtCursorPosition().contains(blackPitchCount)) {
        g.setColor(Color.ORANGE);
      } else {
        g.setColor(Color.BLACK);
      }
      g.fillRect(i * size + size - 4 + offset, 0, size / 2, 175);
      g.setColor(Color.BLACK);
      g.drawRect(i * size + size - 4 + offset, 0, size / 2, 175);

      if (keyCount == skipCount) {
        blackPitchCount += 3;

        if (lastAdded == 2) {
          skipCount += 3;
          lastAdded = 3;
        } else {
          skipCount += 2;
          lastAdded = 2;
        }
      } else {
        blackPitchCount += 2;
      }
  
      keyCount++;
    }
  }
}
