package cs3500.music.controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.PlaybackCommands.Forward;
import cs3500.music.controller.PlaybackCommands.Play;
import cs3500.music.controller.PlaybackCommands.Rewind;
import cs3500.music.controller.PlaybackCommands.Stop;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.CompositeView;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.PianoKey;

/**
 * Created by sebastian on 6/19/17.
 */
public class CompositeController extends CompositionController {
  
  private Map<Integer, IPlaybackCommand> supportedCommands;
  private CompositeView audio;
  private Appendable ap;
  private JMidiComposition composition;
  private MusicEditorGUI gui;
  
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
  
  protected void addCommands() {
    
    supportedCommands.put(80, new Play());
    supportedCommands.put(83, new Stop());
    supportedCommands.put(37, new Rewind());
    supportedCommands.put(39, new Forward());
    
  }
  
  @Override public void keyPressed(KeyEvent e) {
  
    IPlaybackCommand cmd = supportedCommands.getOrDefault(e.getKeyCode(), null);
    if (cmd == null) {
      JMidiUtils.message(e.getKeyCode() + " command NOT registered, try another key!", ap);
    } else {
      cmd.execute(audio);
    }
    
  }
  
  @Override public void mouseClicked(MouseEvent e) {
  
    this.addNote(e.getPoint());
  
  }
  
  public void addNote(Point point) {
    
    try {
      PianoKey key = gui.getKeyAtPosition(point);
      composition.addNote(gui.getCursorPosition(), key.getPitch());
      gui.setCursorPosition(1);
      gui.refreshPanels();
      JMidiUtils.message("***********************************", ap);
      JMidiUtils.message("*          New State:             *", ap);
      JMidiUtils.message("***********************************", ap);
      JMidiUtils.message(composition.toString(), ap);
    } catch (IllegalArgumentException e) {
      JMidiUtils.message("No key here...", ap);
    }
    
  }
  
}
