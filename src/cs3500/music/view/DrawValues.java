package cs3500.music.view;

import java.awt.Color;
import java.awt.Font;


/**
 * A utility class containing constants for the GUI.
 */
public class DrawValues {
  protected static final int RECTANGLE_H = 20;
  protected static final int RECTANGLE_W = 40;
  protected static final Color NOTE_TAIL_COLOR = Color.GREEN;
  protected static final Color NOTE_HEAD_COLOR = Color.BLACK;
  protected static final Color GRID_BORDER_COLOR = Color.BLACK;
  protected static final int MIN_GRID_WIDTH = RECTANGLE_W * 40 + 1;
  protected static final int GRID_MARGIN = 60;
  protected static final int MIN_GRID_HEIGHT = GRID_MARGIN + RECTANGLE_H * 12 + 1;
  protected static final Font VERDANA = new Font("Verdana", Font.BOLD, RECTANGLE_H);

}
