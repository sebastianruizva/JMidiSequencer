package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;


public class MusicEditorGUI extends javax.swing.JFrame {
  private JMidiComposition composition;
  private JPanel scoreLayout;
  private JPanel pianoLayout;
  private BoundedRangeModel scrollModel;

  public MusicEditorGUI(JMidiComposition composition) {
    initScrollModel();
    this.composition = composition;
    this.pianoLayout = initPianoLayout();
    this.scoreLayout = initScoreLayout();

    initComponents();
  }

  public void initialize() {
    this.setVisible(true);
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

  // initialize the scroll model
  private void initScrollModel() {
    JScrollPane base = new JScrollPane();
    base.setPreferredSize(new Dimension(0,0));
    JScrollBar bar = base.getVerticalScrollBar();

    scrollModel = bar.getModel();
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
    scoreLayout.add(initPitchScrollPane());
    scoreLayout.add(initLedgerScrollPane());

    return scoreLayout;
  }

  // Initialize the scroll pane for the pitches
  private JScrollPane initPitchScrollPane() {
    PitchViewPanel pitches = new PitchViewPanel(composition);
    JScrollPane base = new JScrollPane(pitches);

    base.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    base.setPreferredSize(new Dimension(40, DrawValues.MIN_GRID_HEIGHT));
    base.setBorder(null);

    JScrollBar scrollBarVertical = base.getVerticalScrollBar();
    scrollBarVertical.setModel(scrollModel);
    scrollBarVertical.setPreferredSize(new Dimension(0, 0));

    return base;
  }

  // Initialize the scroll for the ledger
  private JScrollPane initLedgerScrollPane() {

    NotesAndGridViewPanel ledger = new NotesAndGridViewPanel(composition);
    JScrollPane base = new JScrollPane(ledger);

    base.setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH, DrawValues.MIN_GRID_HEIGHT));
    base.setBorder(null);

    // Make the scroll area work with arrow keys
    JScrollBar scrollBarHorizontal = base.getHorizontalScrollBar();
    scrollBarHorizontal.setPreferredSize(new Dimension(0, 0));
    scrollBarHorizontal.setUnitIncrement(DrawValues.RECTANGLE_W);
    InputMap horizontalKeys = scrollBarHorizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    horizontalKeys.put(KeyStroke.getKeyStroke("RIGHT"), "positiveUnitIncrement");
    horizontalKeys.put(KeyStroke.getKeyStroke("LEFT"), "negativeUnitIncrement");

    JScrollBar scrollBarVertical = base.getVerticalScrollBar();
    scrollBarVertical.setModel(scrollModel);
    scrollBarVertical.setUnitIncrement(DrawValues.RECTANGLE_H);
    scrollBarVertical.setPreferredSize(new Dimension(0, 0));
    InputMap verticalKeys = scrollBarVertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    verticalKeys.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
    verticalKeys.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");

    return base;
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
    return calculateSize();
  }
}
