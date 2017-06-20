package cs3500.music.view;

/**
 * The class {@ViewSelector} Returns a view according to the user input.
 */
public final class ViewSelector {

  /**
   * Returns a view according to the user input.
   *
   * @param type the type of view you want.
   */
  public static ICompositionView select(String type) throws IllegalArgumentException {

    switch (type) {
      case "midi":
      case "MIDI":
        return new AudioView();
      case "composite":
      case "Composite":
        return new CompositeView();
      case "visual":
      case "VISUAL":
        return new MusicEditorGUI();
      case "console":
      case "CONSOLE":
        return new ConsoleView();
      default:
        throw new IllegalArgumentException("there is no view with that name...");
    }

  }

}
