package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;


public class MusicEditorGUI extends javax.swing.JFrame {
  private JMidiTrack track;
  private JPanel scoreLayout;
  private JPanel pianoLayout;

  public MusicEditorGUI() {
    JMidiTrack jMidiTrack = initTrack();
    pianoLayout = initPianoLayout();
    scoreLayout = initScoreLayout(jMidiTrack);

    initComponents();
  }

  // Initializes the components of the layout
  private void initComponents() {
  //  setSize(calculateSize(scoreLayout, pianoLayout));
    BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);

    this.setLayout(boxLayout);
    this.add(scoreLayout);
    this.add(pianoLayout);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  public void initialize() {
    setPreferredSize(calculateSize());
    this.repaint();
    this.revalidate();
    this.setVisible(true);
    this.repaint();
    this.revalidate();

  }

  // Initializes a dummy track
  private JMidiTrack initTrack() {
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

  // Initialize the scroll pane
  private JScrollPane initScrollPane(JMidiTrack jMidiTrack) {

    NotesAndGridViewPanel ledger = new NotesAndGridViewPanel(jMidiTrack);
    JScrollPane base = new JScrollPane(ledger);
    base.setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH, DrawValues.MIN_GRID_HEIGHT));

    // Make the scroll bar invisible
    base.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    base.getViewport().setPreferredSize(ledger.getPreferredSize());
    base.setPreferredSize(ledger.getPreferredSize());
    base.setBorder(null);

    // Make the scroll area work with arrow keys
    JScrollBar scrollBar = base.getHorizontalScrollBar();
    scrollBar.setPreferredSize(new Dimension(0, 0));
    InputMap im = scrollBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    im.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
    im.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");

    return base;
  }

  // Initialize the piano pane
  private JPanel initPianoLayout() {
    FlowLayout pianoLayoutGrid = new FlowLayout();
    pianoLayoutGrid.setVgap(0);
    pianoLayoutGrid.setHgap(0);

    JPanel buffer = new JPanel();
    buffer.setPreferredSize(new Dimension(40, 400));
    buffer.setMaximumSize(buffer.getPreferredSize());

    buffer.setBorder(null);

    JPanel pianoLayout = new JPanel(pianoLayoutGrid);
    pianoLayout.add(buffer);
    pianoLayout.add(new PianoViewPanel());

    return pianoLayout;
  }

  // Initialize the score pane
  private JPanel initScoreLayout(JMidiTrack jMidiTrack) {

    // Initialize the base for the pitch, midi, and grid
    FlowLayout scoreLayoutGrid = new FlowLayout();
    scoreLayoutGrid.setVgap(0);
    scoreLayoutGrid.setHgap(0);

    JPanel scoreLayout = new JPanel(scoreLayoutGrid);
    scoreLayout.add(new PitchViewPanel(jMidiTrack));
    scoreLayout.add(initScrollPane(jMidiTrack));

    return scoreLayout;
  }

  // Calculate the size of the frame
  private Dimension calculateSize() {
    Dimension scoreDimension = scoreLayout.getPreferredSize();
    Dimension pianoDimension = pianoLayout.getPreferredSize();

    double height = scoreDimension.getHeight() + pianoDimension.getHeight();
    double width = scoreDimension.getWidth() + pianoDimension.getWidth();

    return new Dimension((int) width + 250, (int) height + 29);
  }

  @Override
  public Dimension getPreferredSize() {
    Dimension dim = calculateSize();
    return dim;
  }
}
