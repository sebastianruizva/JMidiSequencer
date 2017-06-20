package cs3500.music.controller;

import cs3500.music.view.AudioView;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ICompositionView;

/**
 * Returns a controller according to the user input.
 */
public class ControllerManager {
  
  /**
   * Returns a controller according to the user input.
   * @param type the type of controller you want.
   */
  public static IVisitableController select(ICompositionView type) throws IllegalArgumentException {
    
      if (type instanceof  CompositeView) {
        
        return new CompositeController();
        
      } else if (type instanceof AudioView) {
  
        return new AudioController();
  
      } else {
        
        throw new IllegalArgumentException("Controller not found!");
        
      }
    
  }
  
}
