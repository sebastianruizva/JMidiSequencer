package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import cs3500.music.controller.PlaybackCommands.Play;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.AudioView;

/**
 * Created by sebastian on 6/20/17.
 */
public class AudioController implements IVisitableController {
  
  private AudioView view;
  private Appendable ap;
  
  @Override
  public void accept(IVisitor visitor) {
    visitor.visit(this);
  }
  
  public void initialize(AudioView view, Readable rd, Appendable ap) {
  
    JMidiUtils.message("Audio Controller Started", ap);
    
    this.view = view;
    JMidiUtils.message("Controller connected to View", ap);
    new Play().execute(view);
    
  }
  
  @Override public void keyTyped(KeyEvent e) {
  }
  
  @Override public void keyPressed(KeyEvent e) {
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
  }
  
  @Override public void mouseExited(MouseEvent e) {
  }
}
