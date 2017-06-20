package cs3500.music.controller.PlaybackCommands;

import cs3500.music.controller.IPlaybackCommand;
import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/20/17.
 */
public class Forward implements IPlaybackCommand {
  @Override public void execute(AudioView view) {
    view.forward();
  }
}
