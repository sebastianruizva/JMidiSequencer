package cs3500.music.view;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

/**
 * A skeleton for MIDI playback
 */
public class MidiViewImpl {
  /**
   * A Midi Event examples.
   */
  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
  JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
  JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
  JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
  JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
  JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
  JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
  /**
   * An scale example for the virtual instrument.
   */
  private ArrayList<String> scaleEx = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  /**
   * An Virtual Instrument example for a MIDI Track.
   */
  JVirtualInstrument jVirtualInstrument = new JVirtualInstrument(scaleEx);
  
  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrack = new JMidiTrack(jVirtualInstrument);
  
  private final Synthesizer synth;
  private final Receiver receiver;
  private final JMidiTrack track;

  public MidiViewImpl() throws MidiUnavailableException {
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    this.track = jMidiTrack;
    this.synth = MidiSystem.getSynthesizer();
    this.receiver = synth.getReceiver();
    this.synth.open();
  }
  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   *  <li>{@link MidiSystem#getSynthesizer()}</li>
   *  <li>{@link Synthesizer}
   *    <ul>
   *      <li>{@link Synthesizer#open()}</li>
   *      <li>{@link Synthesizer#getReceiver()}</li>
   *      <li>{@link Synthesizer#getChannels()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link Receiver}
   *    <ul>
   *      <li>{@link Receiver#send(MidiMessage, long)}</li>
   *      <li>{@link Receiver#close()}</li>
   *    </ul>
   *  </li>
   *  <li>{@link MidiMessage}</li>
   *  <li>{@link ShortMessage}</li>
   *  <li>{@link MidiChannel}
   *    <ul>
   *      <li>{@link MidiChannel#getProgram()}</li>
   *      <li>{@link MidiChannel#programChange(int)}</li>
   *    </ul>
   *  </li>
   * </ul>
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   *   </a>
   */
  
  public void playComposition() {
    
    for (int i = 0; i < track.getMaxTick(); i++) {
  
      for (JMidiEvent e: track.getEventsOnTick(i)) {
        
        try {
          playNote(e.getPitch(), e.getTick(), e.getVelocity());
        } catch (InvalidMidiDataException x) {
          System.out.println(x);
        }
        
      }
      
    }
  
    this.receiver.close();
  }

  public void playNote(int pitch, int tick, int velocity) throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 0, pitch + 60, velocity);
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 0, pitch + 60, velocity);
    this.receiver.send(start, tick * 200000);
    this.receiver.send(stop, this.synth.getMicrosecondPosition() + 200000);
  }
}
