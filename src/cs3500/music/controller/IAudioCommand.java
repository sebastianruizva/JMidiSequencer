package cs3500.music.controller;

import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/19/17.
 */
public interface IAudioCommand {
  
  void execute(AudioView view);
  
}
