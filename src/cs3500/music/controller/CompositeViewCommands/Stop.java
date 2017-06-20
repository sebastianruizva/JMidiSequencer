package cs3500.music.controller.CompositeViewCommands;

import cs3500.music.controller.IAudioCommand;
import cs3500.music.view.AudioView;
import cs3500.music.view.CompositeView;

/**
 * Created by sebastian on 6/19/17.
 */
public class Stop implements IAudioCommand {
  @Override public void execute(AudioView view) {
    view.stop();
  }
}
