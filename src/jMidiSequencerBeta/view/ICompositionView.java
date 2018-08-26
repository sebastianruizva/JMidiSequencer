package jMidiSequencerBeta.view;

import java.util.Observer;

/**
 * Represents the interface for a Musical view.
 */
public interface ICompositionView extends Observer {

  /**
   * Initiates the view's controller.
   */
  void initController();

}
