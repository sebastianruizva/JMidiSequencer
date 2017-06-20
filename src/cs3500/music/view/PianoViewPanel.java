package cs3500.music.view;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.swing.JLayeredPane;

/**
 * A view panel displaying a ten-octave keyboard. Notes that are currently selected by the
 * provided MusicEditorGUI will be illuminated orange.
 */
public class PianoViewPanel extends JLayeredPane {
  private MusicEditorGUI gui;
  private ArrayList<PianoKey> keys;

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
    this.initKeys();
    getKeys();
  }

  @Override
  public void paint(Graphics g) {
    setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH, 400));
    setMaximumSize(getPreferredSize());

    Predicate<PianoKey> isWhiteKey = (PianoKey key) -> key.getType() == PianoKey.PianoType.WHITE;
    Predicate<PianoKey> isBlackKey = (PianoKey key) -> key.getType() == PianoKey.PianoType.BLACK;

    paintBackground(g);
    paintKeyset(g, isWhiteKey);
    paintKeyset(g, isBlackKey);
  }

  private void initKeys() {
    ArrayList<PianoKey> base = new ArrayList<>();
    initWhiteKeys(base);
    initBlackKeys(base);
    keys = base;
  }

  private void initWhiteKeys(ArrayList<PianoKey> keys) {
    int size = DrawValues.RECTANGLE_H;
    int offset = 100;

    int whitePitchCount = 0;
    int skipCount = 2;
    int lastAdded = 3;

    for (int i = 0; i < 70; i++) {
      keys.add(new PianoKey(i * size + offset, 0, PianoKey.PianoType.WHITE, whitePitchCount));

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

  private void initBlackKeys(ArrayList<PianoKey> keys) {
    int skip = 2;
    int oddCount = 0;
    int size = DrawValues.RECTANGLE_H;
    int offset = 100;

    int keyCount = 0;
    int blackPitchCount = 1;
    int skipCount = 1;
    int lastAdded = 2;

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

      keys.add(new PianoKey(i * size + size - 4 + offset, 0, PianoKey.PianoType.BLACK, blackPitchCount));

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

  private void paintKeyset(Graphics g, Predicate<PianoKey> pred) {
    for (PianoKey p : keys) {
      if (pred.test(p)) {
        Rectangle temp = p.getHitBox();
        if (gui.getPitchesAtCursorPosition().contains(p.getPitch())) {
          g.setColor(Color.ORANGE);
        } else {
          g.setColor(p.getColor());
        }
        g.fillRect(temp.x, temp.y, temp.width, temp.height);
        g.setColor(Color.BLACK);
        g.drawRect(temp.x, temp.y, temp.width, temp.height);
      }
    }
  }

  public ArrayList<PianoKey> getKeys() {
    ArrayList<PianoKey> base = new ArrayList<>();

    for (PianoKey p : keys) {
      base.add(p);
    }

    return base;
  }

  public PianoKey getKeyAtPosition(Point point) {
    for (PianoKey p : keys) {
      if (p.getHitBox().contains(point)) {
        return p;
      }
    }
    throw new IllegalArgumentException("No key at that position.");
  }
}