package jMidiSequencerBeta.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * A general controller for any JMidiComposition.
 */
abstract class CompositionController implements ICompositionController {
  
  
  @Override public void keyTyped(KeyEvent e) {
    //not needed but required.
  }
  
  @Override public void keyPressed(KeyEvent e) {
    //not needed but required.
  }
  
  @Override public void keyReleased(KeyEvent e) {
    //not needed but required.
  }
  
  @Override public void mouseClicked(MouseEvent e) {
    //not needed but required.
  }
  
  @Override public void mousePressed(MouseEvent e) {
    //not needed but required.
  }
  
  @Override public void mouseReleased(MouseEvent e) {
    //not needed but required.
  }
  
  @Override public void mouseEntered(MouseEvent e) {
    //not needed but required.
  }
  
  @Override public void mouseExited(MouseEvent e) {
    //not needed but required.
  }
}
