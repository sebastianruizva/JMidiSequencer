package jMidiSequencerBeta;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import jMidiSequencerBeta.controller.MainController;

/**
 * The class {@MusicEditor} represents a MusicEditor Java program.
 */
public class MusicEditor {
  /**
   * Initiates the controller, prompting the user to select a file and a method for viewing.
   * @param args main arguments
   */
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {
    //set reader
    Readable reader = new InputStreamReader(System.in);
    //set controller
    MainController controller = new MainController(reader, System.out);
    //run main view
    controller.run();
  }
}

