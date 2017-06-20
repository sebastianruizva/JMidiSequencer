package cs3500.music;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.MainController;

/**
 * The class {@MusicEditor} represents a MusicEditor Java program.
 */
public class MusicEditor {
  /**
   * Initiates the controller, prompting the user to select a file and a method for viewing.
   */
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {
    Readable reader = new InputStreamReader(System.in);
    MainController controller = new MainController(reader, System.out);
    controller.run();
  }
}

