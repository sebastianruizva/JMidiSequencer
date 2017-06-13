package cs3500.music.view;

import java.awt.*;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;

import cs3500.music.model.JMidiTrack;

/**
 * Created by joe on 6/13/17.
 */
public class LedgerViewPanelGroup extends JPanel {

  JMidiTrack track;
  JPanel pitchPanel;
  JPanel gridMidiPanel;

  public LedgerViewPanelGroup(JMidiTrack track) {
    super (new FlowLayout());
    this.track = track;
    this.pitchPanel = new PitchViewPanel(track);
    this.gridMidiPanel = new MidiViewPanel(track);
    add(pitchPanel);
    add(gridMidiPanel);
  }


}
