package cs3500.music.controller;

import java.awt.event.KeyEvent;

import cs3500.music.util.JMidiUtils;
import cs3500.music.view.visual.MusicEditorGUI;

/**
 * A {@VisualController} controller for the visual view only
 */
public class VisualController extends CompositionController {
  
  /**
   * The controlled view
   */
  private MusicEditorGUI view;
  
  /**
   * Constructs a {@VisualController}.
   * @param view the linked visual view
   * @param ap   an appendable for message handling
   */
  public VisualController(MusicEditorGUI view, Appendable ap) {
    JMidiUtils.message("Keyboard Controller Started", ap);
    this.view = view;
    view.addListener(this);
    JMidiUtils.message("Controller connected to view", ap);
  }
  
  /**
   * listens for key presses
   * @param e the key typed
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      try {
        view.setCursorPosition(1);
      } catch (IllegalArgumentException i) {
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      try {
        view.setCursorPosition(-1);
      } catch (IllegalArgumentException i) {
      }
    }
  }

}