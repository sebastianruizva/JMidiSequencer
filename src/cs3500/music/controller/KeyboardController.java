package cs3500.music.controller;

import java.awt.event.KeyEvent;

import cs3500.music.view.MusicEditorGUI;

/**
 * Created by sebastian on 6/20/17.
 */
public class KeyboardController extends CompositionController {
  
  private MusicEditorGUI view;
  
  public KeyboardController(MusicEditorGUI view, Appendable ap) {
    this.view = view;
    view.addListener(this);
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
  
}
