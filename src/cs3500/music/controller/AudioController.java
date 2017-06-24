package cs3500.music.controller;

import cs3500.music.util.JMidiUtils;
import cs3500.music.view.AudioView;

/**
 * A controller made to handle the audio view only
 */
public class AudioController extends CompositionController {
  
  /**
   * The audio view
   */
  private AudioView audio;
  
  public AudioController(AudioView audio, Appendable ap) {
  
    JMidiUtils.message("Audio Controller Started", ap);
    this.audio = audio;
    JMidiUtils.message("Controller connected to View", ap);
    
    //Play the composition
    audio.play();
    
  }
  
}
