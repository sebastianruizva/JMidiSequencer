package cs3500.music.controller;

import cs3500.music.util.JMidiUtils;
import cs3500.music.view.AudioView;

/**
 * The class {@AudioController} is controller made to handle the audio view only.
 */
public class AudioController extends CompositionController {
  
  /**
   * Constructs an {@audio controller}.
   * @param audio the audio view to controll.
   * @param ap the appendable to add messages.
   */
  public AudioController(AudioView audio, Appendable ap) {
  
    JMidiUtils.message("Audio Controller Started", ap);
    JMidiUtils.message("Controller connected to View", ap);
    
    //Play the composition
    audio.play();
    
  }
  
}
