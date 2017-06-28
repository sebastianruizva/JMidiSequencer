package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.CompositeView;
import cs3500.music.view.gui.GuiView;
import cs3500.music.view.gui.PianoKey;

/**
 * the class {@CompositeController} represents a controller customized for the Composite view
 * Takes care of user interactions in the Composite view.
 */
public class CompositeController extends CompositionController {
  
  /**
   * A map of The supported commands.
   */
  private Map<Integer, Runnable> supportedCommands;
  /**
   * the linked audio view.
   */
  private CompositeView audio;
  /**
   * An appendable for messages.
   */
  private Appendable ap;
  /**
   * The composition being watched.
   */
  private JMidiComposition composition;
  /**
   * the gui view.
   */
  private GuiView gui;
  /**
   * A counter for various actions.
   */
  private int counter;
  /**
   * A timer for various actions.
   */
  private Timer timer;
  
  /**
   * Constructs a {@CompositeController}.
   * @param audio       the audio view
   * @param composition the composition
   * @param ap          an appendable to send messages
   * @param gui         the graphic view
   */
  public CompositeController(CompositeView audio, GuiView gui, JMidiComposition composition,
          Appendable ap) {
    JMidiUtils.message("Composite Controller Started", ap);
    this.counter = 0;
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
   * Populates the list of supported Commands.
   * Keys:.
   * <-  :move cursor to the left.
   * ->  :move cursor to the Right.
   * S   :Stop.
   * P   :Play.
   * B   :go to Start of composition.
   * E   :go to the end of the composition.
   * M   :enable practice mode
   * X   :export file to root
   * -to disable practice mode just press any other command.
   */
  protected void addCommands() {
    
    supportedCommands.put(80, () -> audio.play());
    supportedCommands.put(83, () -> audio.pause());
    supportedCommands.put(37, () -> audio.rewind());
    supportedCommands.put(39, () -> audio.forward());
    supportedCommands.put(69, () -> audio.end());
    supportedCommands.put(66, () -> audio.beginning());
    supportedCommands.put(88, () -> audio.export());
    supportedCommands.put(38, () -> composition.incTempo());
    supportedCommands.put(40, () -> composition.decTempo());
    supportedCommands.put(77, () -> audio.enablePracticeMode());
    
  }
  
  /**
   * Keeps track of key presses.
   * @param e the key
   */
  @Override public void keyPressed(KeyEvent e) {
    
    Runnable cmd = supportedCommands.getOrDefault(e.getKeyCode(), null);
    if (cmd == null) {
      JMidiUtils.message(e.getKeyCode() + " command NOT registered, try another key!", ap);
    } else {
      audio.disablePracticeMode();
      cmd.run();
    }
    
  }
  
  /**
   * Keeps track of click presses.
   * @param e the click
   */
  @Override public void mousePressed(MouseEvent e) {
    try {
      PianoKey key = gui.getKeyAtPosition(e.getPoint());
      audio.playNote(key.getPitch());
      if (!audio.practiceEnabled()) {
        audio.play();
        timer = new javax.swing.Timer(composition.getTempo() / 1000, t -> {
          counter++;
        });
        timer.start();
      } else {
        audio.removePracticeEvent(key.getPitch());
      }
    } catch (IllegalArgumentException c) {
      JMidiUtils.message(c.toString(), ap);
    }
  }
  
  /**
   * Keeps track of click releases.
   * @param e the click
   */
  @Override public void mouseReleased(MouseEvent e) {
    if (!audio.practiceEnabled()) {
      audio.pause();
      timer.stop();
      this.addNote(e.getPoint(), counter);
      counter = 0;
    }
  }
  
  
  /**
   * Adds a note to the composition
   * @param point the point where the new note should go.
   */
  public void addNote(Point point, int duration) {
    try {
      PianoKey key = gui.getKeyAtPosition(point);
      composition.addNote(gui.getCursorPosition() - counter, key.getPitch(), duration);
      JMidiUtils.message("note added at tick " + audio.tick / 24 + " pitch " + key.getPitch(), ap);
      /*for (int i = 0; i < counter; i++) {
        audio.forward();
      }*/
    } catch (IllegalArgumentException e) {
      JMidiUtils.message(e.toString(), ap);
    }
  }
  
}
