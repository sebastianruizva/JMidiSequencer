package jMidiSequencerBeta.view.gui;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.swing.*;

import jMidiSequencerBeta.controller.ICompositionController;
import jMidiSequencerBeta.controller.VisualController;
import jMidiSequencerBeta.model.JMidiComposition;
import jMidiSequencerBeta.model.JMidiEvent;
import jMidiSequencerBeta.util.JMidiUtils;
import jMidiSequencerBeta.view.ICompositionView;

/**
 * A frame for the Music Editor GUI. This frame will contain a scrollable display of the pitches
 * in the provided composition, displayed vertically, all of the notes associated with those pitches
 * displayed horizontally in a ledger based on their onset and offsets, and a ten octave keyboard
 * below. A move-able red bar will also be displayed, and any notes selected by the bar will be
 * illuminated by the keyboard.
 */
public class GuiView extends JFrame implements ICompositionView {
  //a map of practice notes
  public Map<Integer, JMidiEvent> practiceEvents;
  // The composition from which to draw the pitch range and notes.
  private JMidiComposition composition;
  // The layout of the pitches and score ledger.
  private JPanel scoreLayout;
  // The layout of the piano keyboard.
  private JPanel pianoLayout;
  // A model used by the scroll panes for simultaneous scrolling capabilities.
  private BoundedRangeModel scrollModel;
  // The current position of the cursor (the red bar).
  private int cursorPosition;
  // The left bound of the current part of the ledger being displayed by the scroll pane.
  private int windowBoundLeft;
  // The right bound of the current part of the ledger being displayed by the scroll pane.
  private int windowBoundRight;
  // The scroll bar used to keep in time with the cursor once it hits the edge of the display.
  private JScrollBar horizontalCursorTracker;
  // A mapping of the keyboard and their positions for note selection.
  private PianoViewPanel keyMap;
  // The appendable to notify the user of changes.
  private Appendable ap;

  /**
   * Initializes all of the panels in the {@link JFrame}, and makes it visible.
   *
   * @param composition the componsition with which to initController all of the data for each
   * {@link
   *                    JPanel}.
   * @throws IllegalArgumentException if the provided composition is null.
   */
  public GuiView(JMidiComposition composition, Appendable ap) throws IllegalArgumentException {
    if (composition == null || ap == null) {
      throw new IllegalArgumentException(
              "Cannot initController with null composition or Appendable");
    }
  
    this.ap = ap;
    JMidiUtils.message("Preparing GUI View", ap);
    this.windowBoundLeft = 0;
    this.windowBoundRight = 40;
    initScrollModel();
    this.cursorPosition = 0;
    this.composition = composition;
    this.composition.addObserver(this);
    this.pianoLayout = initPianoLayout();
    this.scoreLayout = initScoreLayout();
    this.practiceEvents = new HashMap<>();
    initComponents();
    this.setVisible(true);
    JMidiUtils.message("GUI View Ready", ap);
  }
  
  @Override public void initController() {
    new VisualController(this, ap);
  }
  
  /**
   * Adds all of the initialized {@link JPanel}s to the {@link JFrame}, the layout of the
   * {@link JFrame}, and packs.
   **/
  private void initComponents() {
    BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
    
    this.setLayout(boxLayout);
    this.add(scoreLayout);
    this.add(pianoLayout);

    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.pack();
  }

  /**
   * Initializes the scroll model for each {@link JScrollPane} in the {@link JFrame}. This allows
   * the two {@link JScrollPane}s to scroll simultaneously.
   */
  private void initScrollModel() {
    JScrollPane base = new JScrollPane();
    base.setPreferredSize(new Dimension(0, 0));
    JScrollBar bar = base.getVerticalScrollBar();

    scrollModel = bar.getModel();
  }

  /**
   * Initializes the {@link JPanel} containing the ten octave keyboard, and a buffer panel to
   * center the keybaord under the ledger.
   *
   * @return the initialized keyboard {@link JPanel}.
   */
  private JPanel initPianoLayout() {
    FlowLayout pianoLayoutGrid = new FlowLayout();
    pianoLayoutGrid.setVgap(0);
    pianoLayoutGrid.setHgap(0);

    JPanel buffer = new JPanel();
    buffer.setPreferredSize(new Dimension(40, 400));
    buffer.setMaximumSize(buffer.getPreferredSize());

    buffer.setBorder(null);

    JPanel pianoLayout = new JPanel(pianoLayoutGrid);
    PianoViewPanel pianoViewPanel = new PianoViewPanel(this);
    pianoLayout.add(buffer);
    pianoLayout.add(pianoViewPanel);

    this.keyMap = pianoViewPanel;
    return pianoLayout;
  }

  /**
   * Initializes the main {@link JPanel} containing the {@link JScrollPane} containing the range
   * of pitches, and the {@link JScrollPane} containing the ledger. This method places both panes
   * next to one another in a FlowLayout.
   *
   * @return the {@link JPanel} containg the keyboard and ledger arranged horizontally.
   */
  private JPanel initScoreLayout() {
    FlowLayout scoreLayoutGrid = new FlowLayout();
    scoreLayoutGrid.setVgap(0);
    scoreLayoutGrid.setHgap(0);

    JPanel scoreLayout = new JPanel(scoreLayoutGrid);
    scoreLayout.add(initPitchScrollPane());
    scoreLayout.add(initLedgerScrollPane());

    return scoreLayout;
  }

  /**
   * Initializes the {@link JScrollPane} containing the range of pitches. If a piece has a range
   * that's fewer than twelve pitches, this pane will contain a default range. Otherwise, the pane
   * is initialized to contain a range from the lowest pitch to the highest pitch, represented
   * by their note titles and octave numbers.
   *
   * @return the initialized {@link JScrollPane}.
   */
  private JScrollPane initPitchScrollPane() {
    PitchViewPanel pitches = new PitchViewPanel(this);
    JScrollPane base = new JScrollPane(pitches);

    base.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    base.setPreferredSize(new Dimension(40, DrawValues.MIN_GRID_HEIGHT));
    base.setBorder(null);

    JScrollBar verticalBar = base.getVerticalScrollBar();
    verticalBar.setModel(scrollModel);
    verticalBar.setPreferredSize(new Dimension(0, 0));

    return base;
  }

  /**
   * Initialized the ledger scroll pane. If the scroll pane is larger than the window size, it
   * may be navigated with the arrow keys. Also intializes the view to scroll vertically alongside
   * the pitch display.
   *
   * @return the initialized {@link JScrollPane}.
   */
  private JScrollPane initLedgerScrollPane() {

    NotesAndGridViewPanel ledger = new NotesAndGridViewPanel(this);

    JScrollPane base = new JScrollPane(ledger);

    base.setPreferredSize(new Dimension(DrawValues.MIN_GRID_WIDTH, DrawValues.MIN_GRID_HEIGHT));
    base.setBorder(null);

    JScrollBar scrollBarVertical = base.getVerticalScrollBar();
    scrollBarVertical.setModel(scrollModel);
    scrollBarVertical.setUnitIncrement(DrawValues.RECTANGLE_H);
    scrollBarVertical.setPreferredSize(new Dimension(0, 0));
    InputMap verticalKeys = scrollBarVertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    verticalKeys.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");
    verticalKeys.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");

    horizontalCursorTracker = base.getHorizontalScrollBar();
    horizontalCursorTracker.setUnitIncrement(DrawValues.RECTANGLE_W);

    return base;
  }

  @Override
  public Dimension getPreferredSize() {
    return calculateSize();
  }

  /**
   * Calculates the size for the JFrame based on the combined width and height of the {@link
   * JPanel}s within.
   *
   * @return the correct {@link Dimension} for the {@link JFrame}.
   */
  private Dimension calculateSize() {
    Dimension scoreDimension = scoreLayout.getPreferredSize();
    Dimension pianoDimension = pianoLayout.getPreferredSize();

    double height = scoreDimension.getHeight() + pianoDimension.getHeight();
    double width = scoreDimension.getWidth() + pianoDimension.getWidth();

    return new Dimension((int) width + 250, (int) height + 29);
  }

  /**
   * Returns the position of the cursor.
   *
   * @return the position of the cursor.
   */
  public int getCursorPosition() {
    return cursorPosition;
  }

  /**
   * Sets the cursor position by the desired amount, and repaints the {@link JFrame} to update
   * the necessary {@link JPanel}s, such as the keyboard view and the ledger view. If the desired
   * increase would move the bar to a position lower than zero or greater than the maximum tick
   * in the score, an exception is thrown.
   *
   * @param increase the desired change for the cursor.
   * @throws IllegalArgumentException if the change would cause the cursor value to become less than
   *                                  zero.
   * @throws IllegalArgumentException if the change would cause the cursor value to become greater
   *                                  than the maximum tick in the composition.
   */
  public void setCursorPosition(int increase) throws IllegalArgumentException {
    if (increase + cursorPosition < 0) {
      throw new IllegalArgumentException("Cannot decrease position below zero.");
    }

    if (increase + cursorPosition > composition.getMaxTick()) {
      throw new IllegalArgumentException("Cannot increase position above maximum tick.");
    }

    this.cursorPosition += increase;

    adjustWindowBounds();
    adjustScrollBar();

    refreshPanels();
  }
  
  /**
   * Sets the cursor position to the desired posn, and repaints the {@link JFrame} to update
   * the necessary {@link JPanel}s, such as the keyboard view and the ledger view. If the desired
   * increase would move the bar to a position lower than zero or greater than the maximum tick
   * in the score, an exception is thrown.
   * @throws IllegalArgumentException if the change would cause the cursor value to become less than
   *                                  zero.
   * @throws IllegalArgumentException if the change would cause the cursor value to become greater
   *                                  than the maximum tick in the composition.
   */
  public void setCursorAbsolutePosition(int posn) throws IllegalArgumentException {
    if (!(posn < 0 || posn > composition.getMaxTick())) {
    
      this.cursorPosition = posn;
    
    }
    adjustWindowBounds();
    adjustScrollBar();
  
    refreshPanels();
  }

  /**
   * Adjusts the current position of the scrollbar to match the position of the cursor. For
   * instance, if the cursor is moved offscreen, the scrollbar will move to match.
   */
  private void adjustScrollBar() {
    horizontalCursorTracker.setValue(windowBoundLeft * 40);

    refreshPanels();
  }

  /**
   * Adjusts the bounds for the scroll pane based on the position of the cursor. If the cursor
   * reaches a point currently offscreen, the window bounds are adjusted to compensate.
   */
  private void adjustWindowBounds() {
    if (cursorPosition > windowBoundRight) {
      windowBoundRight++;
      windowBoundLeft++;
    }

    if (cursorPosition < windowBoundRight && cursorPosition < windowBoundLeft) {
      windowBoundRight--;
      windowBoundLeft--;
    }
  }

  /**
   * Determines a list of pitches present at the tick currently selected by the cursor.
   *
   * @return the list of pitches.
   */
  protected ArrayList<Integer> getPitchesAtCursorPosition() {
    ArrayList<Integer> pitches = new ArrayList<>();
  
    for (JMidiEvent event : composition.getEventsOnTick(cursorPosition)) {
      pitches.add(event.getPitch());
    }
  
    return pitches;
  }
  
  /**
   * Determines a list of pitches present at the tick currently selected by the cursor.
   * @return the list of pitches.
   */
  protected ArrayList<Integer> getPitchesAtPractice() {
    ArrayList<Integer> pitches = new ArrayList<>();
    
    for (Integer i : practiceEvents.keySet()) {
      pitches.add(practiceEvents.get(i).getPitch());
    }
    
    return pitches;
  }

  /**
   * Refreshes the panels within the JFrame.
   */
  public void refreshPanels() {
    this.scoreLayout.revalidate();
    this.scoreLayout.repaint();

    this.pianoLayout.revalidate();
    this.pianoLayout.repaint();

    revalidate();
    repaint();
  }

  /**
   * Adds a key and mouse listener to this JFrame.
   * @param listener the listener to be added.
   */
  public void addListener(ICompositionController listener) {
    super.addKeyListener(listener);
    super.addMouseListener(listener);
  }

  /**
   * Returns the {@link PianoKey} associated with the given {@link Point}. This allows for notes
   * to be added based on the pitch of the selected key.
   * @param point The point used to locate the selected {@link PianoKey}.
   * @return the selected {@link PianoKey}.
   */
  public PianoKey getKeyAtPosition(Point point) {
    int y = point.y - 350;
    if (y < 0) {
      y = 0;
    }

    Point corrected = new Point(point.x - 180, y);
    return keyMap.getKeyAtPosition(corrected);
  }

  /**
   * Returns this composition.
   * @return this composition.
   */
  public JMidiComposition getComposition() {
    return this.composition;
  }
  
  @Override public void update(Observable o, Object arg) {
    JMidiUtils.message("Updating GuiVIew", ap);
    if (arg instanceof Integer) {
      this.setCursorAbsolutePosition((int) arg / 24);
    }
    refreshPanels();
  }
}
