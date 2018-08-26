package jMidiSequencerBeta.controller;

import jMidiSequencerBeta.util.JMidiUtils;
import jMidiSequencerBeta.view.AudioView;

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
    audio.play();
    JMidiUtils.message("playing composition", ap);
    
  }
  
}
