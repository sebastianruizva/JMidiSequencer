package cs3500.music;

import cs3500.music.controller.CmdController;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * The class {@MusicEditor} represents a MusicEditor Java program.
 */
public class MusicEditor {
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {
    
    Readable reader = new InputStreamReader(System.in);
    CmdController selector = new CmdController(reader, System.out);
  
    selector.run();
    
  }
}

