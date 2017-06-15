package cs3500.music.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;

public class JMidiCompositionController {
  
  final Readable rd;
  final Appendable ap;
  String fileName;
  String viewType;
  Scanner scanner;
  
  /**
   * Constructs a {@code JMidiCompositionController} object.
   * @param rd takes user input
   * @param ap transmits output
   * @throws IllegalStateException if the controller has not been initialized properly to receive
   *                               input and transmit output.
   */
  public JMidiCompositionController(Readable rd, Appendable ap) {
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
    this.scanner = new Scanner(rd);
  }
  
  public void run() throws MidiUnavailableException, FileNotFoundException {
  
    this.message("please write the name of the file you want to open and its extension");
    
    while (scanner.hasNext()) {
  
      if(fileName == null) {
  
        fileName = scanner.next();
  
        JMidiComposition composition = MusicReader
                .parseFile(new FileReader(fileName), JMidiComposition.builder());
  
        this.message("Console, GUI or MIDI?");
  
        if(scanner.next().equals("console")) {
    
          this.message(composition.toString());
    
        }
  
        if(scanner.next().equals("GUI")) {
  
          MusicEditorGUI guiView = new MusicEditorGUI();
          guiView.initialize();
  
        }
  
        if(scanner.next().equals("midi")) {
  
          MidiViewImpl midiView = new MidiViewImpl(composition);
          
          try {
  
            midiView.playComposition();
            
          } catch (InvalidMidiDataException e) {
          
          }
    
        }
  
      } else {
  
        this.message("please write the name of the file you want to open and its extension");
        
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
