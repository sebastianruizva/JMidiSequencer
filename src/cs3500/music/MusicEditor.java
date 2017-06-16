package cs3500.music;

import cs3500.music.controller.JMidiCompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;


public class MusicEditor {
  public static void main(String[] args)
          throws IOException, InvalidMidiDataException, MidiUnavailableException {
    
/*    Readable reader = new InputStreamReader(System.in);
    JMidiCompositionController controller = new JMidiCompositionController(reader, System.out);
    
    controller.run();*/

    JMidiComposition composition = MusicReader
            .parseFile(new FileReader("mystery-3.txt"), JMidiComposition.builder());

    MusicEditorGUI guiView = new MusicEditorGUI(composition);
    guiView.initialize();
    
  }
}

