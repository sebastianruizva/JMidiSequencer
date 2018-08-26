package jMidiSequencerBeta.controller;

import java.awt.event.KeyEvent;

import jMidiSequencerBeta.util.JMidiUtils;
import jMidiSequencerBeta.view.gui.GuiView;

/**
 * A {@VisualController} controller for the gui view only.
 */
public class VisualController extends CompositionController {
  
  /**
   * The controlled view.
   */
  private GuiView view;
  
  /**
   * Constructs a {@VisualController}.
   * @param view the linked gui view.
   * @param ap   an appendable for message handling.
   */
  public VisualController(GuiView view, Appendable ap) {
    JMidiUtils.message("Keyboard Controller Started", ap);
    this.view = view;
    view.addListener(this);
    JMidiUtils.message("Controller connected to view", ap);
  }
  
  /**
   * listens for key presses.
   * @param e the key typed.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      try {
        view.setCursorPosition(1);
      } catch (IllegalArgumentException i) {
        //if error means that there is no key, so do nothing.
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      try {
        view.setCursorPosition(-1);
      } catch (IllegalArgumentException i) {
        //if error means that there is no key, so do nothing.
      }
    }
  }

}
