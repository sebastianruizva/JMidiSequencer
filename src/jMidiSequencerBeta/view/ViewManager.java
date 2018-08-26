package jMidiSequencerBeta.view;

import jMidiSequencerBeta.model.JMidiComposition;
import jMidiSequencerBeta.view.gui.GuiView;

/**
 * The class {@ViewSelector} Returns a view according to the user input.
 */
public final class ViewManager {

  /**
   * Returns a view according to the user input.
   *
   * @param type the type of view you want.
   * @param composition the composition to be tracked by the view
   * @param ap an appendable for feedback
   * @throws IllegalArgumentException when no view found
   */
  public static ICompositionView select(String type, JMidiComposition composition, Appendable ap)
          throws IllegalArgumentException {
    switch (type) {
      case "midi":
      case "MIDI":
        return new AudioView(composition, ap);
      case "composite":
      case "Composite":
        return new CompositeView(composition, ap);
      case "gui":
      case "VISUAL":
        return new GuiView(composition, ap);
      case "console":
      case "CONSOLE":
        return new ConsoleView(composition, ap);
      default:
        throw new IllegalArgumentException("there is no view with that name...");
    }

  }

}
