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
public class GuiViewFrame extends javax.swing.JFrame {

  /**
   * Creates new GuiView
   */
  public GuiViewFrame() {
    this.setSize(new Dimension(1500, 700));
    setResizable(true);
    JMidiTrack jMidiTrack = initTrack();

    // Make the midi and grid scrollable
    JScrollPane midiAndGridScroll = initScrollPane(jMidiTrack);
    midiAndGridScroll.setPreferredSize(new Dimension( DrawValues.MIN_GRID_WIDTH, DrawValues.MIN_GRID_HEIGHT));


    // Initialize the base for the pitch, midi, and grid
    FlowLayout scoreLayoutGrid = new FlowLayout();
    scoreLayoutGrid.setVgap(0);
    scoreLayoutGrid.setHgap(0);

    JPanel scoreLayout = new JPanel(scoreLayoutGrid);
    scoreLayout.add(new PitchViewPanel(jMidiTrack));
    scoreLayout.add(midiAndGridScroll);

/*    scoreLayout.add(new PitchViewPanel(jMidiTrack));
    scoreLayout.add(midiAndGridScroll);*/
    GridLayout mainLayout = new GridLayout(2,1);
    mainLayout.setHgap(0);
    mainLayout.setVgap(0);

    this.getContentPane().setLayout(mainLayout);

    this.getContentPane().add(scoreLayout);
    this.getContentPane().add(new PianoViewPanel());

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  public void initialize() {
    this.setVisible(true);
  }

  // Initialize the scroll pane
  private JScrollPane initScrollPane(JMidiTrack jMidiTrack) {

    NotesAndGridViewPanel ledger = new NotesAndGridViewPanel(jMidiTrack);
    JScrollPane base = new JScrollPane(ledger);

    // Make the scroll bar invisible
    base.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    base.getViewport().setPreferredSize(ledger.getPreferredSize());
    base.setPreferredSize(ledger.getPreferredSize());
    base.setBorder(null);

    // Make the scroll area work with arrow keys
    JScrollBar scrollBar = base.getHorizontalScrollBar();
    scrollBar.setPreferredSize(new Dimension(0,0));
    InputMap im = scrollBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    im.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
    im.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");

    return base;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1700, 700);
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
