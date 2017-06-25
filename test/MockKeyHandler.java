import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import cs3500.music.controller.VisualController;
import cs3500.music.view.visual.MusicEditorGUI;
import cs3500.music.view.visual.PianoKey;

/**
 * Tester clas for the KeyHandler. Prints messages to the appendable based on what keys are
 * pressed and what they're doing.
 */
public class MockKeyHandler extends VisualController {

  // The log where different actions are going to be recorded.
  public StringBuilder log;
  private Map<Integer, Supplier<String>> supportedCommands;
  private MusicEditorGUI view;

  /**
   * Constructs a new MockKeyHandler.
   */
  public MockKeyHandler(MusicEditorGUI view, Appendable ap) {
    super(view, ap);

    this.view = view;
    this.supportedCommands = new HashMap<>();
    this.log = new StringBuilder();

    supportedCommands.put(KeyEvent.VK_P, () -> "Playing.\n");
    supportedCommands.put(KeyEvent.VK_S, () -> "Stopping.\n");
    supportedCommands.put(KeyEvent.VK_LEFT, () -> "Rewinding.\n");
    supportedCommands.put(KeyEvent.VK_RIGHT, () -> "Forwarding.\n");
  }
  
  /**
   * Detects a key press.
   */
  public void keyPressed(int e) {
    int oldPosition = view.getCursorPosition();

    if (e == KeyEvent.VK_RIGHT) {

      try {
        view.setCursorPosition(1);
        int newPosition = view.getCursorPosition();

        log.append("Cursor position increased by one.\n"
                + "[old cursor position: " + oldPosition + "]\n"
                + "[new cursor position: " + newPosition + "]");

      } catch (IllegalArgumentException i) {
        int newPosition = view.getCursorPosition();
        log.append("Cannot move cursor any farther right.\n"
                + "[old cursor position: " + oldPosition + "]\n"
                + "[new cursor position: " + newPosition + "]\n");
      }
    }
  
    if (e == KeyEvent.VK_LEFT) {
      try {
        view.setCursorPosition(-1);
        int newPosition = view.getCursorPosition();

        log.append("Cursor position decreased by one.\n"
                + "[old cursor position: " + oldPosition + "]\n"
                + "[new cursor position: " + newPosition + "]\n");

      } catch (IllegalArgumentException i) {
        int newPosition = view.getCursorPosition();

        log.append("Cursor position cannot be decreased.\n"
                + "[old cursor position: " + oldPosition + "]\n"
                + "[new cursor position: " + newPosition + "]\n");
      }
    }
  
    if (supportedCommands.getOrDefault(e, null) == null) {
      log.append("No valid key!\n");
    } else {
      log.append(supportedCommands.getOrDefault(e, null).get());
    }
  }
  
  
  /**
   * Detects mpose clicks.
   */
  public void mouseClicked(Point e) {
    try {
      int oldSize = view.getComposition().getEventsOnTick(view.getCursorPosition()).size();
      PianoKey key = view.getKeyAtPosition(e);

      view.getComposition().addNote(view.getCursorPosition(), key.getPitch());
      int newSize = view.getComposition().getEventsOnTick(view.getCursorPosition()).size();

      log.append("addNote: " + "[pitch: " + key.pitch + "] \n"
              + "[beat: " + view.getCursorPosition() + "] \n"
              + "[old note quantity: " + oldSize + "] \n"
              + "[new note quantity: " + newSize + "]\n");
    } catch (IllegalArgumentException i) {

      int oldSize = view.getComposition().getEventsOnTick(view.getCursorPosition()).size();
      int newSize = view.getComposition().getEventsOnTick(view.getCursorPosition()).size();

      log.append("no key clicked.\n"
              + "[old note quantity: " + oldSize + "] \n"
              + "[new note quantity: " + newSize + "]\n");
    }
  }

  @Override
  public String toString() {
    return log.toString();
  }
}
