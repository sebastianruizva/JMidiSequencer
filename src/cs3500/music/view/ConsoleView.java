package cs3500.music.view;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a Console view of the composition.
 */
public class ConsoleView implements ICompositionView {
  
  Appendable ap;
  JMidiComposition composition;

  /**
   * Initializes the view.
   *
   * @param composition the composition to be rendered.
   * @param ap          the appendable that tracks the messages.
   */
  public ConsoleView(JMidiComposition composition, Appendable ap) {
  
    JMidiUtils.message("Preparing console view", ap);
    if (composition == null || ap == null) {
      throw new IllegalArgumentException("params cant be null!");
    }
    this.ap = ap;
    this.composition = composition;
    JMidiUtils.message("Console view ready!", ap);

  }
  
  @Override public void initialize() {
    JMidiUtils.message(composition.toString(), ap);
    JMidiUtils.message("Console view initialized", ap);
  }
}
