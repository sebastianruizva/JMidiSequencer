package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.util.JMidiUtils;
import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/20/17.
 */
public class AudioController implements IVisitableController {
  
  private Map<Integer, IPlaybackCommand> supportedCommands;
  private AudioView view;
  private Appendable ap;
  
  @Override
  public void accept(IVisitor visitor) {
    visitor.visit(this);
  }
  
  public void initialize(AudioView view, Readable rd, Appendable ap) {
  
    JMidiUtils.message("Audio Controller Started", ap);
    
    this.view = view;
    this.supportedCommands = new HashMap<>();
    JMidiUtils.message("Key Listener added", ap);
    
  }
  
  @Override public void keyTyped(KeyEvent e) {
    
    IPlaybackCommand cmd = supportedCommands.getOrDefault(e, null);
    if (cmd == null) {
      JMidiUtils.message("No command found!", ap);
    } else {
      cmd.execute(view);
    }
    
  }
  
  @Override public void keyPressed(KeyEvent e) {
  
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      JMidiUtils.message("KeyPressed", ap);
    }
  
    JMidiUtils.message("KeyPressed", ap);
    
    IPlaybackCommand cmd = supportedCommands.getOrDefault(e, null);
    if (cmd == null) {
      JMidiUtils.message("No command found!", ap);
    } else {
      cmd.execute(view);
    }
    
  }
  
  @Override public void keyReleased(KeyEvent e) {
  
  }
}
