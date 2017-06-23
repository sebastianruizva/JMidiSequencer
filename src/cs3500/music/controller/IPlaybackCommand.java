package cs3500.music.controller;

import cs3500.music.view.AudioView;

/**
 * an interface for playback related commands
 */
public interface IPlaybackCommand {
  
  void execute(AudioView view);
  
}
