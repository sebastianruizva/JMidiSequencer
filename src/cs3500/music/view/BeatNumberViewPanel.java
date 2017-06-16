package cs3500.music.view;

import java.awt.*;
import java.util.Collections;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;

/**
 * Created by joe on 6/15/17.
 */
public class BeatNumberViewPanel extends JPanel {

  private JMidiComposition compositon;
  private int width;

public BeatNumberViewPanel(JMidiComposition compositon) {
  this.compositon = compositon;
  setValues();
  setPreferredSize(new Dimension(width, DrawValues.RECTANGLE_H));
  setBackground(Color.GREEN);
}

  @Override
  public void paintComponent(Graphics g) {
    g.setFont(DrawValues.VERDANA);

    for (int i = 0; i < width; i += 4) {
      g.drawString(Integer.toString(i), i * DrawValues.RECTANGLE_W ,0);
    }
  }

  private void setValues() {
    if (DrawValues.MIN_GRID_WIDTH < (DrawValues.RECTANGLE_W * compositon.getMaxTick())) {

      this.width = DrawValues.RECTANGLE_W * compositon.getMaxTick();

    } else {

      this.width = DrawValues.MIN_GRID_WIDTH;

    }
  }
}
