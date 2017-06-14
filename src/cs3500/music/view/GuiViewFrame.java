package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IjMidiTrackView {

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {

    JMidiTrack jMidiTrack = initTrack();

    // Overlay the midi notes and the grid
    JPanel midiAndGridOverlay = new JPanel();
    midiAndGridOverlay.setLayout(new OverlayLayout(midiAndGridOverlay));

    midiAndGridOverlay.add(new GridViewPanel(jMidiTrack));
    midiAndGridOverlay.add(new MidiViewPanel(jMidiTrack));

    // Make the midi and grid scrollable
    JScrollPane midiAndGridScroll = new JScrollPane();
    midiAndGridScroll.add(midiAndGridOverlay);

    // Initialize the base for the pitch, midi, and grid
    JPanel scoreLayout = new JPanel(new FlowLayout());

    scoreLayout.add(new PitchViewPanel(jMidiTrack));
    scoreLayout.add(midiAndGridScroll);

    // this.getContentPane().setLayout(new BoxLayout());


    // add(new PianoViewPanel());
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
/*    this.getContentPane().add(new PitchViewPanel(jMidiTrack));
    this.getContentPane().add(new PianoViewPanel());*/
    this.getContentPane().add(midiAndGridScroll);
    this.pack();
  }

  // @Override
  public void initialize() {
    this.setVisible(true);

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(DrawValues.MIN_GRID_WIDTH, 1000);
  }

  public JMidiTrack initTrack() {
    /**
     * A Midi Event examples.
     */
    JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
    JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
    JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
    JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
    JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
    JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
    JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
    /**
     * An scale example for the virtual instrument.
     */
    ArrayList<String> scaleEx = new ArrayList<>(
            Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
    /**
     * An Virtual Instrument example for a MIDI Track.
     */
    JVirtualInstrument jVirtualInstrument = new JVirtualInstrument(scaleEx);

    /**
     * A Midi Track example.
     */
    JMidiTrack jMidiTrack = new JMidiTrack(jVirtualInstrument);

    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);

    return jMidiTrack;
  }

}
