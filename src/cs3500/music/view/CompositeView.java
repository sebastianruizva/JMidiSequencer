package cs3500.music.view;

import cs3500.music.model.JMidiComposition;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView extends AudioView {
  
  JMidiComposition composition;
  MusicEditorGUI visual;
  
  @Override public void initialize(JMidiComposition composition, Appendable ap) {
  
    this.composition = composition;
    this.visual = new MusicEditorGUI();
    this.visual.initialize(composition, ap);
    
  }
  
  /**
   * Synchronizes the audio and visual views.
   */
  public void sync() {
    
    int newTick = 0;
    int oldTick = 0;
    int interval = 0;
    
    while(newTick < composition.getMaxTick()){
      
      oldTick = newTick;
      newTick = (int) this.getTickPosition() / 24;
      interval = newTick - oldTick;
      
      if (interval != 0) {
        visual.setCursorPosition(interval);
      }
      
    }
    
  }
  
  public void play() {
  
    this.play();
    this.sync();
    
  }
  
}
