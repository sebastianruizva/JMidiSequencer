package cs3500.music.view;

import cs3500.music.model.JMidiComposition;

/**
 * The class {@ViewSelector} Returns a view according to the user input
 */
public final class ViewSelector {
  
  /**
   * Returns a view according to the user input.
   * @param type the type of view you want
   */
  public static ICompositionView select(String type) throws IllegalArgumentException {
    
    switch (type) {
      case "midi":
      case "MIDI":
        return new MidiViewImpl();
      case "console":
      case "CONSOLE":
        return new MidiViewImpl();
      case "gui":
      case "GUI":
        return new MusicEditorGUI();
        default:
          throw new IllegalArgumentException("there is no view with that name...");
    }
    
  }
  
}
