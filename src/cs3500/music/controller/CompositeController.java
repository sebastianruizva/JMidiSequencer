package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.CompositeView;
import cs3500.music.view.visual.MusicEditorGUI;
import cs3500.music.view.visual.PianoKey;

/**
 * the class {@CompositeController} represents a controller customized for the Composite view
 */
public class CompositeController extends CompositionController {
  
  /**
   * A map of The supported commands
   */
  private Map<Integer, Runnable> supportedCommands;
  /**
   * the linked audio view
   */
  private CompositeView audio;
  /**
   * An appendable for messages
   */
  private Appendable ap;
  /**
   * The composition being watched
   */
  private JMidiComposition composition;
  /**
   * the visual view
   */
  private MusicEditorGUI gui;
  
  /**
   * Constructs a {@CompositeController}.
   * @param audio       the audio view
   * @param composition the composition
   * @param ap          an appendable to send messages
   * @param gui         the graphic view
   */
  public CompositeController(CompositeView audio, MusicEditorGUI gui, JMidiComposition composition,
          Appendable ap) {
    JMidiUtils.message("Composite Controller Started", ap);
    this.ap = ap;
    this.audio = audio;
    this.supportedCommands = new HashMap<>();
    this.composition = composition;
    this.gui = gui;
    this.addCommands();
    audio.addListener(this);
    JMidiUtils.message("Controller connected to View", ap);
  }
  
  /**
   * Populates the list of supported Commands
   */
  protected void addCommands() {
    
    supportedCommands.put(80, () -> audio.play());
    supportedCommands.put(83, () -> audio.pause());
    supportedCommands.put(37, () -> audio.rewind());
    supportedCommands.put(39, () -> audio.forward());
    
  }
  
  /**
   * Keeps track of key presses
   * @param e the key
   */
  @Override public void keyPressed(KeyEvent e) {
    
    Runnable cmd = supportedCommands.getOrDefault(e.getKeyCode(), null);
    if (cmd == null) {
      JMidiUtils.message(e.getKeyCode() + " command NOT registered, try another key!", ap);
    } else {
      cmd.run();
    }
    
  }
  
  /**
   * Keeps track of click presses
   * @param e the click
   */
  @Override public void mouseClicked(MouseEvent e) {
    
    this.addNote(e.getPoint());
    
  }
  
  public void addNote(Point point) {
    
    try {
      PianoKey key = gui.getKeyAtPosition(point);
      composition.addNote(gui.getCursorPosition(), key.getPitch());
      audio.refreshSequencer(composition);
      audio.forward();
      gui.refreshPanels();
      JMidiUtils.message("note added at " + key.getPitch(), ap);
    } catch (IllegalArgumentException e) {
      JMidiUtils.message("No key here...", ap);
    }
    
  }
  
}
