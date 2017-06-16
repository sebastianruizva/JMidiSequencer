package cs3500.music.view;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl implements ICompositionView {
  
  private JMidiComposition composition;
  
  @Override public void initialize(JMidiComposition composition) {
    
    this.composition = composition;
    
    try {
      this.playComposition();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    
  }
  
  
  private void playTrack(JMidiTrack track) {
  
    Synthesizer synth;
    Receiver receiver;
    
    try {
      synth = track.getInstrument().getSynth();
      receiver = synth.getReceiver();
      synth.open();
      for (int i = 0; i < track.getMaxTick(); i++) {
    
        for (JMidiEvent e : track.getEventsOnTick(i)) {
      
          try {
            playNote(e.getPitch(), e.getTick(), e.getVelocity(), e.getDuration());
  
            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, e.getPitch(), e.getVelocity());
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, e.getPitch(), e.getVelocity());
  
            receiver.send(start, e.getTick() * composition.getTempo());
            receiver.send(stop, (e.getTick() + e.getDuration()) * composition.getTempo());
            
          } catch (InvalidMidiDataException x) {
            System.out.println(x);
          }
      
        }
    
      }
  
      receiver.close();
      
      
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    
    
    
  }
  
  public void playComposition() throws InvalidMidiDataException {
    
    
    for (Integer k : composition.getTracks().keySet()) {
      
      this.playTrack(composition.getTracks().get(k));
      
    }
    

    
  }
  
  
  public void playNote(int pitch, int tick, int velocity, int duration)
          throws InvalidMidiDataException {

  }
  
}
