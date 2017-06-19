package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * Created by sebastian on 6/19/17.
 */
public class CompositeView extends MidiViewImpl implements ICompositionView {
  
  JMidiComposition composition;
  MusicEditorGUI gui;
  
  @Override public void initialize(JMidiComposition composition, Appendable ap) {
  
    composition = composition;
    
    gui = new MusicEditorGUI();
    
    gui.initialize(composition, ap);

    
    
    
    gui.setCursorPosition(5);

  }
  
}
