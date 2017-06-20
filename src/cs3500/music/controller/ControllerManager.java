package cs3500.music.controller;

import cs3500.music.view.AudioView;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.MusicEditorGUI;

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
  
      } else if (type instanceof MusicEditorGUI) {
  
        return new KeyboardController();
  
      } else {
        
        throw new IllegalArgumentException("Controller not found!");
        
      }
    
  }
  
}
