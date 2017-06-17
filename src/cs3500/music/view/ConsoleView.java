package cs3500.music.view;

import com.sun.media.sound.MidiUtils;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a Console view of the composition
 */
public class ConsoleView implements ICompositionView {
  
  /**
   * Initializes the view
   * @param composition the
   * @param ap the appendable that tracks the messages
   */
  @Override public void initialize(JMidiComposition composition, Appendable ap) {
    
    if(composition == null || ap == null) {
      throw  new IllegalArgumentException("params cant be null!");
    }
  
    JMidiUtils.message("MIDI view initialized", ap);
    JMidiUtils.message(composition.toString(), ap);
    
  }
  
}
