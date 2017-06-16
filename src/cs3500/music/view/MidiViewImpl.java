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
 * The class {@MidiViewImpl} implements a MIDI playback view of a composition
 */
public class MidiViewImpl implements ICompositionView {
  
  private JMidiComposition composition;
  
  /**
   * Plays the directed composition
   * @param composition the composition you want to play
   */
  @Override public void initialize(JMidiComposition composition) {
    
    this.composition = composition;
    
    try {
      this.playComposition();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    
  }
  
  
  /**
   * Plays the directed track
   * @param track the track you want to play
   */
  private void playTrack(JMidiTrack track) {
    
    //listen for exceptions
    try {
      //set vars
      Synthesizer synth = track.getInstrument().getSynth();
      Receiver receiver = synth.getReceiver();
      
      //open synth
      synth.open();
      
      //add all the notes
      for (int i = 0; i < track.getMaxTick(); i++) {
    
        for (JMidiEvent e : track.getEventsOnTick(i)) {
      
          try {
  
            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, e.getPitch(), e.getVelocity());
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, e.getPitch(), e.getVelocity());
  
            receiver.send(start, e.getTick() * composition.getTempo());
            receiver.send(stop, (e.getTick() + e.getDuration()) * composition.getTempo());
            
          } catch (InvalidMidiDataException x) {
            System.out.println(x);
          }
      
        }
    
      }
  
      //close the synth
      receiver.close();
      
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    
  }
  
  /**
   * Plays all the tracks in the composition
   */
  public void playComposition() throws InvalidMidiDataException {
    
    
    for (Integer k : composition.getTracks().keySet()) {
      
      this.playTrack(composition.getTracks().get(k));
      
    }
    

    
  }
  
}
