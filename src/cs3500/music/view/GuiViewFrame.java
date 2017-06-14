package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IjMidiTrackView {

//  private final JPanel displayPanel; // You may want to refine this to a subtype of JPanel

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
  private ArrayList<String> scaleEx = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  /**
   * An Virtual Instrument example for a MIDI Track.
   */
  JVirtualInstrument jVirtualInstrument = new JVirtualInstrument(scaleEx);

  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrack = new JMidiTrack(jVirtualInstrument);


  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);

    // ----------------------------------------------------------------------------------------

/*    JPanel basePanel = new JPanel(new FlowLayout());
    JPanel midiPanel = new JPanel();
    LayoutManager overlay = new OverlayLayout(midiPanel);
    midiPanel.setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH,
            12*20));
    midiPanel.setLayout(overlay);
    basePanel.setComponentOrientation(
            ComponentOrientation.LEFT_TO_RIGHT);

    midiPanel.add(new GridViewPanel(jMidiTrack));
    midiPanel.add(new MidiViewPanel(jMidiTrack));

    basePanel.add(new PitchViewPanel(jMidiTrack));
    basePanel.add(midiPanel);

    add(basePanel);*/
    this.getContentPane().add(new PianoViewPanel(jMidiTrack));
    //  this.getContentPane().add(new PitchViewPanel(jMidiTrack));

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    this.pack();
  }

  // @Override
  public void initialize() {
    this.setVisible(true);

  }


  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }


}
