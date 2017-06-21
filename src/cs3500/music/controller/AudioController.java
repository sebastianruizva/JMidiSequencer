package cs3500.music.controller;

import cs3500.music.controller.PlaybackCommands.Play;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/20/17.
 */
public class AudioController extends CompositionController {
  
  private AudioView view;
  private Appendable ap;
  
  public AudioController(AudioView view, Appendable ap) {
  
    JMidiUtils.message("Audio Controller Started", ap);
    this.view = view;
    JMidiUtils.message("Controller connected to View", ap);
    new Play().execute(view);
    
  }
  
}
