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

  public PitchViewPanel(JMidiTrack track) {
    this.track = track;
    this.grid = track.getGrid();

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    IjVirtualInstrument currInstrument = track.getInstrument();
    g.setFont(DrawValues.VERDANA);

    for (int i = 0; i < track.getMaxPitch(); i++) {

    }
  }
}
