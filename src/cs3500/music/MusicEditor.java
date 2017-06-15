package cs3500.music;

import cs3500.music.controller.JMidiCompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.MidiViewImpl;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {
    
    Readable reader = new InputStreamReader(System.in);
    JMidiCompositionController controller = new JMidiCompositionController(reader, System.out);
    
    controller.run();

  }
}

