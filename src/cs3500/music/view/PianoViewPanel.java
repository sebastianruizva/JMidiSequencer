package cs3500.music.view;

import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * Created by joe on 6/13/17.
 */
public class PianoViewPanel extends JPanel{
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;

  public PianoViewPanel(JMidiTrack track) {
    this.track = track;
    this.grid = track.getGrid();
  }

  private void initPiano() {

  }
}
