package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.SectorType;

public class NotesAndGridViewPanel extends JPanel {
  JMidiComposition composition;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private int width;
  private int height;
  private int maxPitch;
  private int maxTick;
  private int cursorPosition;

  NotesAndGridViewPanel(JMidiComposition composition/*, int cursorPosition*/) {
    this.cursorPosition = 0;
    this.composition = composition;
    this.grid = composition.getGrid();
    this.maxPitch = composition.getMaxPitch();
    this.maxTick = composition.getMaxTick();
    setValues();

    setPreferredSize(new Dimension(width, height));
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
    super.paintComponent(g);
    paintNotes(g);
    paintGrid(g);
    paintBeatNumbers(g);
  }

  private void paintNotes(Graphics g) {
    //Place the elements in the grid
    int noteWidth = DrawValues.RECTANGLE_W;
    int noteHeight = DrawValues.RECTANGLE_H;

    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {

        SectorType type = composition.getSectorType(tick, pitch);

        if (SectorType.HEAD == type) {

          g.setColor(DrawValues.NOTE_HEAD_COLOR);

        }
        if (SectorType.BODY == type) {

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
        g.drawLine(i*DrawValues.RECTANGLE_W, height,
                i*DrawValues.RECTANGLE_W, DrawValues.GRID_MARGIN);
      }
    }

    for (int i = 0; i <= ((height - DrawValues.GRID_MARGIN) / DrawValues.RECTANGLE_H); i++) {
      g.setColor(DrawValues.GRID_BORDER_COLOR);
      g.drawLine(0, (i*DrawValues.RECTANGLE_H) + DrawValues.GRID_MARGIN,
              width, (i*DrawValues.RECTANGLE_H) + DrawValues.GRID_MARGIN);
    }
  }

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
  }
