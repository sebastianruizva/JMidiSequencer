package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.PlaybackCommands.Forward;
import cs3500.music.controller.PlaybackCommands.Play;
import cs3500.music.controller.PlaybackCommands.Rewind;
import cs3500.music.controller.PlaybackCommands.Stop;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.CompositeView;

/**
 * Created by sebastian on 6/19/17.
 */
public class CompositeController extends CompositionController {
  
  private Map<Integer, IPlaybackCommand> supportedCommands;
  private CompositeView view;
  private Appendable ap;
  
  public CompositeController(CompositeView view, Appendable ap) {
    JMidiUtils.message("Composite Controller Started", ap);
    this.ap = ap;
    this.view = view;
    this.supportedCommands = new HashMap<>();
    this.addCommands();
    view.addListener(this);
    JMidiUtils.message("Controller connected to View", ap);
  }
  
  protected void addCommands() {
    
    supportedCommands.put(80, new Play());
    supportedCommands.put(83, new Stop());
    supportedCommands.put(37, new Rewind());
    supportedCommands.put(39, new Forward());
    
  }
  
  @Override public void keyPressed(KeyEvent e) {
  
    IPlaybackCommand cmd = supportedCommands.getOrDefault(e.getKeyCode(), null);
    if (cmd == null) {
      JMidiUtils.message(e.getKeyCode() + " command NOT registered, try another key!", ap);
    } else {
      cmd.execute(view);
    }
    
  }
  
  @Override public void mouseClicked(MouseEvent e) {
  
    view.addNote(e.getPoint());
  
  }
  
}
