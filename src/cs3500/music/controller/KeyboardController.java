package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.PianoKey;

/**
 * Created by sebastian on 6/20/17.
 */
public class KeyboardController  implements IVisitableController {
  
  private MusicEditorGUI view;
  
  public void initialize(MusicEditorGUI view, Readable rd, Appendable ap) {
    
    this.view = view;
    view.addListener(this);

  }
  
  
  @Override public void accept(IVisitor visitor) {
    visitor.visit(this);
  }
  
  @Override
  public void keyTyped(KeyEvent e) {
    // Should do nothing, but must be overridden.
  }
  
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      try {
        view.setCursorPosition(1);
      } catch (IllegalArgumentException i) {
        // System.out.println("Cannot increase cursor, try again.");
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      try {
        view.setCursorPosition(-1);
      } catch (IllegalArgumentException i) {
        // System.out.println("Cannot reduce cursor, try again.");
      }
    }
  }
  
  @Override
  public void keyReleased(KeyEvent e) {
    // Should do nothing, but must be overridden.
  }
  
  @Override public void mouseClicked(MouseEvent e) {
  
    try {
      PianoKey target = view.getKeyAtPosition(e.getPoint());
      //controller.addKey(target.pitch, view.getCursorLocation());
    } catch (IllegalArgumentException x) {
      // controller.message("No key at that position.");
    }
  
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
