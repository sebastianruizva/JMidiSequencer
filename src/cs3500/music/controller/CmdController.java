package cs3500.music.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.ViewSelector;

/**
 * The class {@ModeSelector} Creates a view according to the user instructions.
 */
public class CmdController {
  
  final Readable rd;
  final Appendable ap;
  String fileName;
  String viewType;
  Scanner scanner;
  JMidiComposition composition;
  String view;
  
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
    this.viewType = null;
    this.composition = null;
    this.view = null;
    this.scanner = new Scanner(rd);
  }
  
  /**
   * Interacts with the user
   */
  public void run() throws MidiUnavailableException, FileNotFoundException {
    
    this.message("please write the name of the file you want to open and its extension");
    
    while (scanner.hasNextLine()) {
      
      String next = scanner.nextLine();
      
      if(next.equalsIgnoreCase("Q")) {
        
        this.message("bye!");
        return;
        
      } else if (fileName == null) {
        
        fileName = next;
        
        try {
          
          composition = MusicReader.parseFile(new FileReader(fileName), JMidiComposition.builder());
  
          this.message("console, visual or MIDI?");
          
        } catch (IOException e) {
          
          fileName = null;
          this.message(e.toString());
          
        }
        
      } else if (view == null) {
  
        view = next;
  
        if (next.equalsIgnoreCase("console")) {
    
          this.message(composition.toString());
    
        } else {
    
          try {
      
            ICompositionView selected = ViewSelector.select(next);
            selected.initialize(composition);
      
          } catch (IllegalArgumentException e) {
      
            view = null;
            this.message(e.toString());
      
          }
    
        }
      }
      
    }
    
  }
  
  /**
   * Appends a message to the appendable
   * @param s the string that is going to be appended.
   */
  private void message(String s) {
    try {
      
      this.ap.append(s + "\n");
      
      return;
    } catch (IOException e) {
      //not needed
    }
  }
}
