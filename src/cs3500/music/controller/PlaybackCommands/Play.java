package cs3500.music.controller.PlaybackCommands;

import cs3500.music.controller.IPlaybackCommand;
import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/19/17.
 */
public class Play implements IPlaybackCommand {
  @Override public void execute(AudioView view) {
    view.play();
  }
}
