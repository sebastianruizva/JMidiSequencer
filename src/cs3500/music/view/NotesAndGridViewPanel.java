package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.model.SectorType;
import cs3500.music.util.JMidiUtils;

/**
 * A {@link JPanel} for the notes and the grid of the provided composition, as well as the cursor
 * image.
 */
public class NotesAndGridViewPanel extends JPanel {
  private JMidiComposition composition;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private int width;
  private int height;
  private int maxPitch;
  private int maxTick;
  private int minPitch;
  private MusicEditorGUI gui;

  /**
   * Constructs a NotesAndGridPanel based on the content of the provided composition and gui.
   *
   * @param composition the composition to draw the notes and grid size from/
   * @param gui         the gui to draw the cursor from.
   * @throws IllegalArgumentException if the composition or the gui is null.
   */
  public NotesAndGridViewPanel(JMidiComposition composition, MusicEditorGUI gui) {
    
    if(composition == null || gui == null) {
      throw new IllegalArgumentException("cant be null!");
    }
    
    this.composition = composition;
    this.grid = composition.getGrid();
    this.maxPitch = composition.getMaxPitch();
    this.maxTick = composition.getMaxTick();
    this.minPitch = composition.getMinPitch();
    this.gui = gui;
    setValues();

    setPreferredSize(new Dimension(width, height));
  }

  /**
   * Sets the size for this grid and panel. If the calculated height of the note range and
   * grid are greater than the preset minimum, that height will be used. Otherwise, the minimum
   * height will be implemented.
   */
  private void setValues() {
    if (DrawValues.MIN_GRID_HEIGHT < (maxPitch - minPitch + 4) * DrawValues.RECTANGLE_H + 1) {

      this.height = (maxPitch - minPitch + 4) * DrawValues.RECTANGLE_H + 1;

    } else {

      this.height = DrawValues.MIN_GRID_HEIGHT;

    }

    if (DrawValues.MIN_GRID_WIDTH < (DrawValues.RECTANGLE_W * maxTick)) {

      this.width = DrawValues.RECTANGLE_W * maxTick;

    } else {

      this.width = DrawValues.MIN_GRID_WIDTH;

    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintNotes(g);
    paintGrid(g);
    paintBeatNumbers(g);
    paintCursor(g);
  }

  /**
   * Paints each note onto the panel. The x-coordinate of each note is based on their onset, and
   * the y-coordinate is based on their respective pitch.
   *
   * @param g the graphics component with which to draw.
   */
  private void paintNotes(Graphics g) {
    //Place the elements in the grid
    int noteWidth = DrawValues.RECTANGLE_W;
    int noteHeight = DrawValues.RECTANGLE_H;

    JVirtualInstrument inst = JMidiUtils.DEFAULT_VI();

    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {
        SectorType type = composition.getSectorType(tick, pitch);

        if (SectorType.HEAD == type) {

          g.setColor(DrawValues.NOTE_HEAD_COLOR);

        }
        if (SectorType.BODY == type) {

          g.setColor(DrawValues.NOTE_TAIL_COLOR);

        }
        g.fillRect(tick * noteWidth, (maxPitch - pitch) * 20 + DrawValues.GRID_MARGIN, noteWidth, noteHeight);
      }
    }
  }

  /**
   * Paints the grid on top of the notes. Each row correlates to one pitch value, and each
   * column is equivalent to four beats of space.
   *
   * @param g the graphics component with which to draw.
   */
  private void paintGrid(Graphics g) {
    for (int i = 0; i <= (width / DrawValues.RECTANGLE_W); i++) {
      if (i % 4 == 0) {
        g.setColor(DrawValues.GRID_BORDER_COLOR);
        g.drawLine(i * DrawValues.RECTANGLE_W, height,
                i * DrawValues.RECTANGLE_W, DrawValues.GRID_MARGIN);
      }
    }

    for (int i = 0; i <= (((maxPitch - minPitch + 1) * DrawValues.RECTANGLE_H) / DrawValues.RECTANGLE_H); i++) {
      g.setColor(DrawValues.GRID_BORDER_COLOR);
      g.drawLine(0, (i * DrawValues.RECTANGLE_H) + DrawValues.GRID_MARGIN,
              width, (i * DrawValues.RECTANGLE_H) + DrawValues.GRID_MARGIN);
    }
  }

  /**
   * Paints the number of beats progressed every four beats on the top of the grid. If beat
   * numbers start to become too large, they are abbreviated.
   *
   * @param g the graphics component with which to draw.
   */
  private void paintBeatNumbers(Graphics g) {
    g.setFont(DrawValues.VERDANA);

    for (int i = 0; i < maxTick; i += 4) {

      String number = Integer.toString(i);

      if (number.length() > 6) {
        number = number.substring(0, 6) + ". . .";
      }

      g.drawString(number, i * DrawValues.RECTANGLE_W, 50);
    }
  }

  /**
   * Paints the cursor based on the cursor position in the provided model.
   *
   * @param g the graphics component with which to draw.
   */
  private void paintCursor(Graphics g) {
    g.setColor(Color.RED);

    g.drawLine(gui.getCursorPosition() * DrawValues.RECTANGLE_W, DrawValues.GRID_MARGIN,
            gui.getCursorPosition() * DrawValues.RECTANGLE_W, height);
  }
}
