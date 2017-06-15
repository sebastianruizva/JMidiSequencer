package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.*;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl {
  
  private final Synthesizer synth;
  private final Receiver receiver;
  private JMidiComposition composition;

  public MidiViewImpl(JMidiComposition composition) throws MidiUnavailableException {
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
    this.composition = composition;
  }
  
  private void playTrack(JMidiTrack track) {
    
    for (int i = 0; i < track.getMaxTick(); i++) {
  
      for (JMidiEvent e: track.getEventsOnTick(i)) {
        
        try {
          playNote(e.getPitch(), e.getTick(), e.getVelocity(), e.getDuration());
        } catch (InvalidMidiDataException x) {
          System.out.println(x);
        }
        
      }
      
    }
    
  }
  
  public void playComposition() {
  
    for (Integer k: composition.getTracks().keySet()) {
      
      this.playTrack(composition.getTracks().get(k));
      
    }
  
    this.receiver.close();
    
  }
  
  
  public void playNote(int pitch, int tick, int velocity, int duration) throws
          InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, pitch, velocity);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch, velocity);
    this.receiver.send(start, tick * 200000);
    this.receiver.send(stop, (tick + duration) * 200000);
  }
  
 
}
