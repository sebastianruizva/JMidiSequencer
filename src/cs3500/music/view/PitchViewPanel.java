package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.IjVirtualInstrument;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * Created by joe on 6/13/17.
 */
public class PitchViewPanel extends JPanel {
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private int maxPitch;

  public PitchViewPanel(JMidiTrack track) {
    this.track = track;
    this.grid = track.getGrid();
    this.maxPitch = track.getMaxPitch();

    if (maxPitch < 12) {
      maxPitch = 12;
    }
    setOpaque(false);
/*    setPreferredSize(new Dimension(DrawValues.RECTANGLE_H,
            DrawValues.RECTANGLE_H * track.getMaxPitch()));*/
    setPreferredSize(new Dimension(40,
            12*20+60));
    setBorder(BorderFactory.createEtchedBorder());

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    IjVirtualInstrument inst = track.getInstrument();
    g.setFont(DrawValues.VERDANA);

    for (int i = 0; i < maxPitch; i++) {
      g.drawString(inst.getNoteRepresentation(i), 0, i * 20 + DrawValues.GRID_MARGIN);
    }
  }
}
