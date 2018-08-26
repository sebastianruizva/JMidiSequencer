package jMidiSequencerBeta.view;

import java.util.Observable;

import jMidiSequencerBeta.model.JMidiComposition;
import jMidiSequencerBeta.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a Console view of the composition.
 */
public class ConsoleView implements ICompositionView {
  
  Appendable ap;
  JMidiComposition composition;

  /**
   * Constructs the view.
   * @param composition the composition to be rendered.
   * @param ap          the appendable that tracks the messages.
   */
  public ConsoleView(JMidiComposition composition, Appendable ap) {
    if (composition == null) {
      throw new IllegalArgumentException("Composition cant be null!");
    }

    if (ap == null) {
      throw new IllegalArgumentException("Appendable cant be null!");
    }
    JMidiUtils.message("Preparing console view", ap);
    this.ap = ap;
    this.composition = composition;
    this.composition.addObserver(this);
    JMidiUtils.message("Console view ready!", ap);

  }
  
  /**
   * Initializes the view's Controller.
   */
  @Override public void initController() {
    JMidiUtils.message(composition.toString(), ap);
    JMidiUtils.message("Console view initialized", ap);
  }
  
  /**
   * Updates the console's values.
   */
  @Override public void update(Observable o, Object arg) {
    JMidiUtils.message(composition.toString(), ap);
  }
}
