package cs3500.music.view;

import java.awt.event.KeyEvent;

/**
 * A KeyListener for {@link MusicEditorGUI}. Allows for the cursor to be moved left by the left
 * key, and right by the right key, granted that the moves are legal.
 */
public class KeyListener implements java.awt.event.KeyListener {
  private MusicEditorGUI gui;

  public KeyListener(MusicEditorGUI gui) {
    this.gui = gui;
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      try {
        gui.setCursorPosition(1);
      } catch (IllegalArgumentException i) {
        System.out.println("Cannot increase cursor, try again.");
      }
    }
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      try {
        gui.setCursorPosition(-1);
      } catch (IllegalArgumentException i) {
        System.out.println("Cannot reduce cursor, try again.");
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }
}
