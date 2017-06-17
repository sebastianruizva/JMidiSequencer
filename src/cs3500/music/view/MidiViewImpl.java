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
import cs3500.music.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a MIDI playback view of a composition
 */
public class MidiViewImpl implements ICompositionView {
  
  private JMidiComposition composition;
  private Appendable ap;
  
  /**
   * Initializes the view
   * @param composition the
   * @param ap the appendable that tracks the messages
   */
  @Override public void initialize(JMidiComposition composition, Appendable ap) throws IllegalArgumentException {
    
    if(composition == null || ap == null) {
      throw  new IllegalArgumentException("params cant be null!");
    }
  
    this.composition = composition;
    this.ap = ap;
    
    JMidiUtils.message("MIDI view Initialized", ap);
    
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
  
    if(track == null) {
      throw  new IllegalArgumentException("params cant be null!");
    }
    
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
  
            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, e.getChannel(), e.getPitch(), e
                    .getVelocity());
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, e.getChannel(), e.getPitch(), e.getVelocity());
  
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
  
    if(composition == null) {
      throw  new IllegalArgumentException("params cant be null!");
    }
    
    for (Integer k : composition.getTracks().keySet()) {
      
      JMidiUtils.message("Playing Track #" + k, ap);
      
      this.playTrack(composition.getTracks().get(k));
      
    }
    

    
  }
  
}
