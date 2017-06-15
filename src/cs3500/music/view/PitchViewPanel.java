package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.IjVirtualInstrument;
import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.util.JMidiUtils;

/**
 * Created by joe on 6/13/17.
 */
public class PitchViewPanel extends JPanel {
  JMidiComposition composition;
  private int maxPitch;

  public PitchViewPanel(JMidiComposition composition) {
    this.composition = composition;
    this.maxPitch = composition.getMaxPitch();

    if (maxPitch < 12) {
      maxPitch = 12;
    }
    setPreferredSize(new Dimension(40, DrawValues.MIN_GRID_HEIGHT));
    setMaximumSize(getPreferredSize());

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    IjVirtualInstrument inst = JMidiUtils.DEFAULT_VI;
    g.setFont(DrawValues.VERDANA);

    for (int i = 0; i < maxPitch; i++) {
      g.drawString(inst.getNoteRepresentation(i) + (i / inst.getOctaveDegree()), 0, (maxPitch-i) * 20 + DrawValues.GRID_MARGIN);
    }
  }
}
