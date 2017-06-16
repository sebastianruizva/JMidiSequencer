package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.*;

/**
 * The class {@MidiViewImplTest} represents tests for the class (@MidiViewImpl}.
 */
public class MidiViewImplTest {
  
  /**
   * A JMidiComposition example.
   */
  JMidiComposition jMidiComposition;
  
  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrack;
  
  /**
   * An Virtual Instrument example for a MIDI Track.
   */
  JVirtualInstrument jVirtualInstrument;
  
  /**
   * A Log that keeps track of the MIDI events
   */
  StringBuilder log;
  
  /**
   * An scale example for the virtual instrument.
   */
  ArrayList<String> scale = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * An example of a midi view implementation
   */
  MidiViewImpl midiView;
  
  
  /**
   * Initial conditions for testing.
   */
  public void initCond() {
    
    log = new StringBuilder();
    
    jVirtualInstrument = new JVirtualInstrument(scale, new TestSynth(log));
    
    jMidiTrack = new JMidiTrack(jVirtualInstrument);
    
    jMidiComposition =
            JMidiComposition.builder().addNote(0, 4, 0, 1, 30)
                    .setInstrument(0, jVirtualInstrument).build();
  
    midiView = new MidiViewImpl();
  }
  
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  @Test public void TestMidiView_Composition1NoteIn0() throws Exception {
    this.initCond();
    
    midiView.initialize(jMidiComposition);
    assertEquals("Synth Opened \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:800000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:800000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:800000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "Receiver Closed \n", log.toString());
  }
  
  @Test public void TestMidiView_CompositionEmpty() throws Exception {
    this.initCond();
    jMidiComposition =
            JMidiComposition.builder().build();
    
    midiView.initialize(jMidiComposition);
    assertEquals("", log.toString());
    
    
  }
  
  @Test public void TestMidiView_Composition5Note_() throws Exception {
    this.initCond();
    jMidiComposition =
            JMidiComposition.builder().addNote(10,11,0,2,3).build();
    
    midiView.initialize(jMidiComposition);
    assertEquals("", log.toString());
    
    
  }
  
}