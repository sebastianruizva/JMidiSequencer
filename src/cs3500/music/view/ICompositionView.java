package cs3500.music.view;

import cs3500.music.model.JMidiComposition;

/**
 * Represents the interface for a Musical view
 */
public interface ICompositionView {
  
  /**
   * Initiates the view.
   * @composition the model that is going to be rendered
   */
  void initialize(JMidiComposition composition, Appendable ap);
  
}
