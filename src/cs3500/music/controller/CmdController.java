package cs3500.music.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.ViewSelector;

/**
 * The class {@ModeSelector} Creates a view according to the user instructions.
 */
public class CmdController implements ICmdController {
  
  final Readable rd;
  final Appendable ap;
  private String fileName;
  private Scanner scanner;
  private JMidiComposition composition;
  private String view;
  
  /**
   * Constructs a {@code CmdController} object.
   * @param rd takes user input
   * @param ap transmits output
   * @throws IllegalStateException if the controller has not been initialized properly to receive
   *                               input and transmit output.
   */
  public CmdController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalStateException("readable can't be null!");
    }
    if (ap == null) {
      throw new IllegalStateException("Appendable can't be null!");
    }
    this.rd = rd;
    this.ap = ap;
    this.fileName = null;
    this.composition = null;
    this.view = null;
    this.scanner = new Scanner(rd);
  }
  
  /**
   * Interacts with the user
   */
  @Override public void run() throws MidiUnavailableException, FileNotFoundException {
  
    JMidiUtils.message("please write the name of the file you want to open and its extension", ap);
    
    while (scanner.hasNextLine()) {
      
      String next = scanner.nextLine();
      
      if(next.equalsIgnoreCase("Q")) {
  
        JMidiUtils.message("bye!", ap);
        return;
        
      } else if (fileName == null) {
        
        fileName = next;
        
        try {
          
          composition = MusicReader.parseFile(new FileReader(fileName), JMidiComposition.builder());
  
          JMidiUtils.message("console, visual or MIDI?", ap);
          
        } catch (IOException | IllegalArgumentException e) {
          
          fileName = null;
          JMidiUtils.message(e.toString(), ap);
          
        }
        
      } else if (view == null) {
    
          try {
      
            ICompositionView selected = ViewSelector.select(next);
            selected.initialize(composition, ap);
            JMidiUtils.message("Write Q to quit", ap);
      
          } catch (IllegalArgumentException e) {
      
            view = null;
            JMidiUtils.message(e.toString(), ap);
      
          }
    
        }
      
    }
    
  }
  
}
