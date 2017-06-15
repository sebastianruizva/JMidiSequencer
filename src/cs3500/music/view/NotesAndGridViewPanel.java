package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

public class NotesAndGridViewPanel extends JPanel {
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private int width;
  private int height;
  private int maxPitch;
  private int maxTick;

  NotesAndGridViewPanel(JMidiTrack track) {
    setPreferredSize(new Dimension( DrawValues.MIN_GRID_WIDTH, DrawValues.MIN_GRID_HEIGHT));
    setMaximumSize(getPreferredSize());


    this.track = track;
    this.grid = track.getGrid();
    this.maxPitch = track.getMaxPitch();
    this.maxTick = track.getMaxTick();
    setValues();
  }

  private void setValues() {
    if (DrawValues.MIN_GRID_HEIGHT < (DrawValues.RECTANGLE_H * maxPitch)) {

      this.height = DrawValues.RECTANGLE_H * maxPitch;

    } else {

      this.height = DrawValues.MIN_GRID_HEIGHT;

    }

    if (DrawValues.MIN_GRID_WIDTH < (DrawValues.RECTANGLE_W * maxTick)) {

      this.width = DrawValues.RECTANGLE_W * maxTick;

    } else {

      this.width = DrawValues.MIN_GRID_WIDTH;

    }
  }

  @Override public void paintComponent(Graphics g) {
    paintNotes(g);
    paintGrid(g);
  }

  private void paintNotes(Graphics g) {
    //Place the elements in the grid
    int noteWidth = DrawValues.RECTANGLE_W;
    int noteHeight = DrawValues.RECTANGLE_H;

    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {

        JMidiTrack.SectorType type = track.getSectorType(tick, pitch);

        if (JMidiTrack.SectorType.HEAD == type) {

          g.setColor(DrawValues.NOTE_HEAD_COLOR);

        }
        if (JMidiTrack.SectorType.BODY == type) {

          g.setColor(DrawValues.NOTE_TAIL_COLOR);

        }

        g.fillRect(tick * noteWidth, height - (pitch * noteHeight) - DrawValues.RECTANGLE_H, noteWidth,
                noteHeight);

        g.drawRect(tick * noteWidth, height - (pitch * noteHeight) - DrawValues.RECTANGLE_H, noteWidth,
                noteHeight);

      }
    }
  }

  private void paintGrid(Graphics g) {
    for (int i = 0; i <= (width / DrawValues.RECTANGLE_W); i++) {

      if(i%4 == 0) {
        g.setColor(DrawValues.GRID_BORDER_COLOR);
        g.drawLine(i*DrawValues.RECTANGLE_W, height, i*DrawValues
                .RECTANGLE_W, DrawValues.GRID_MARGIN);
      }

    }

    for (int i = 0; i <= ((height - DrawValues.GRID_MARGIN) / DrawValues.RECTANGLE_H); i++) {

      g.setColor(DrawValues.GRID_BORDER_COLOR);
      g.drawLine(0, (i*DrawValues.RECTANGLE_H) + DrawValues
              .GRID_MARGIN, width, (i*DrawValues.RECTANGLE_H) + DrawValues
              .GRID_MARGIN);
    }
  }
}
