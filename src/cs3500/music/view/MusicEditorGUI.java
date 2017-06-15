package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;


public class MusicEditorGUI extends javax.swing.JFrame {
  private JMidiComposition composition;
  private JPanel scoreLayout;
  private JPanel pianoLayout;

  public MusicEditorGUI(JMidiComposition composition) {
    this.composition = composition;
    this.pianoLayout = initPianoLayout();
    this.scoreLayout = initScoreLayout();

    initComponents();
  }

  // Initializes the components of the layout
  private void initComponents() {
    BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);

    this.setLayout(boxLayout);
    this.add(scoreLayout);
    this.add(pianoLayout);

    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  public void initialize() {
    this.setVisible(true);

  }

  // Initialize the scroll pane
  private JScrollPane initScrollPane() {

    NotesAndGridViewPanel ledger = new NotesAndGridViewPanel(composition);
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
  private JPanel initScoreLayout() {

    // Initialize the base for the pitch, midi, and grid
    FlowLayout scoreLayoutGrid = new FlowLayout();
    scoreLayoutGrid.setVgap(0);
    scoreLayoutGrid.setHgap(0);

    JPanel scoreLayout = new JPanel(scoreLayoutGrid);
    scoreLayout.add(new PitchViewPanel(composition));
    scoreLayout.add(initScrollPane());

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
