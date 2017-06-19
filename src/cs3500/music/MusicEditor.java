package cs3500.music;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.CmdController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.ViewSelector;

/**
 * The class {@MusicEditor} represents a MusicEditor Java program.
 */
public class MusicEditor {
  /**
   * Initiates the controller, prompting the user to select a file and a method for viewing.
   */
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {

/*    Readable reader = new InputStreamReader(System.in);
    CmdController selector = new CmdController(reader, System.out);

    selector.run();*/


    JMidiComposition composition = MusicReader.parseFile(new FileReader("mary-little-lamb.txt"), JMidiComposition.builder());
    ICompositionView selected = ViewSelector.select("visual");
    selected.initialize(composition, new StringBuilder());

  }
}

