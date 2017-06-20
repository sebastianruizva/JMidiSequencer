package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.PlaybackCommands.Forward;
import cs3500.music.controller.PlaybackCommands.Play;
import cs3500.music.controller.PlaybackCommands.Stop;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.CompositeView;

/**
 * Created by sebastian on 6/19/17.
 */
public class CompositeController implements IVisitableController {
  
  private Map<Integer, IPlaybackCommand> supportedCommands;
  private CompositeView view;
  private Appendable ap;

  @Override
  public void accept(IVisitor visitor) {
    visitor.visit(this);
  }
  
  protected void addCommands() {
  
    supportedCommands.put(80, new Play());
    supportedCommands.put(83, new Stop());
    supportedCommands.put(70, new Forward());
    supportedCommands.put(66, new Forward());
    
  }

  public void initialize(CompositeView view, Readable rd, Appendable ap) {
    this.ap = ap;
    this.view = view;
    this.supportedCommands = new HashMap<>();
    this.addCommands();
    view.addListener(this);
  }
  
  @Override public void keyPressed(KeyEvent e) {
  
    IPlaybackCommand cmd = supportedCommands.getOrDefault(e.getKeyCode(), null);
    if (cmd == null) {
      JMidiUtils.message(e.getKeyCode() + " command NOT registered, try another key!", ap);
    } else {
      cmd.execute(view);
    }
    
  }
  
  @Override public void keyTyped(KeyEvent e) {
  
  }
  
  @Override public void keyReleased(KeyEvent e) {
  
  }
  
  @Override public void mouseClicked(MouseEvent e) {
  
  }
  
  @Override public void mousePressed(MouseEvent e) {
  
  }
  
  @Override public void mouseReleased(MouseEvent e) {
  
  }
  
  @Override public void mouseEntered(MouseEvent e) {
  
    view.sync();
  
  }
  
  @Override public void mouseExited(MouseEvent e) {
  
  }
}
