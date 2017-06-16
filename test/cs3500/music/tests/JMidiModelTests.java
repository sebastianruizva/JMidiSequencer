package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

import static org.junit.Assert.assertEquals;

/**
 * The class {@JMidiModelTests} Represents tests for the Model.
 */
public class JMidiModelTests {
  
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
   * Midi Event examples.
   */
  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
  JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
  JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
  JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
  StringBuilder log;
  /**
   * An scale example for the virtual instrument.
   */
  private ArrayList<String> scale = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * Initial conditions for testing.
   */
  public void initCond() {
    
    log = new StringBuilder();
    
    jVirtualInstrument = new JVirtualInstrument(scale, new TestSynth(log));
    
    jMidiTrack = new JMidiTrack(jVirtualInstrument);
    
    jMidiComposition =
            JMidiComposition.builder().addNote(0, 4, 0, 1, 30).setInstrument(0, jVirtualInstrument)
                    .build();
    
  }
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  /**
   * ************** MIDI EVENTS
   */
  
  @Test public void TestGetTickAndBuilder() throws Exception {
    assertEquals(0, e0.getTick());
    assertEquals(5, e1.getTick());
    assertEquals(2, e2.getTick());
    assertEquals(3, e3.getTick());
  }
  
  @Test public void TestGetPitchAndBuilder() throws Exception {
    assertEquals(0, e0.getPitch());
    assertEquals(6, e1.getPitch());
    assertEquals(2, e2.getPitch());
    assertEquals(3, e3.getPitch());
  }
  
  @Test public void TestGetVelocityAndBuilder() throws Exception {
    assertEquals(64, e0.getVelocity());
    assertEquals(64, e1.getVelocity());
    assertEquals(64, e2.getVelocity());
    assertEquals(64, e3.getVelocity());
  }
  
  @Test public void TestGetChannelAndBuilder() throws Exception {
    assertEquals(0, e0.getChannel());
    assertEquals(0, e1.getChannel());
    assertEquals(0, e2.getChannel());
    assertEquals(0, e3.getChannel());
  }
  
  @Test public void TestGetDurationAndBuilder() throws Exception {
    assertEquals(7, e0.getDuration());
    assertEquals(3, e1.getDuration());
    assertEquals(1, e2.getDuration());
    assertEquals(3, e3.getDuration());
  }
  
  @Test public void TestToString() throws Exception {
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=0, pitch=0, velocity=64, channel=0, duration=7}",
            e0.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=5, pitch=6, velocity=64, channel=0, duration=3}",
            e1.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=3, pitch=3, velocity=64, channel=0, duration=3}",
            e3.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=2, pitch=2, velocity=64, channel=0, duration=1}",
            e2.toString());
  }
  
  @Test public void TestClone() throws Exception {
    assertEquals(JMidiEvent.builder().clone(e0).build().toString(), e0.toString());
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_NegativePitch()
          throws Exception {
    JMidiEvent.builder().tick(2).pitch(-1).duration(1).velocity(100).build();
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_NegativeTick()
          throws Exception {
    JMidiEvent.builder().tick(-1).pitch(2).duration(1).velocity(100).build();
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_InvalidDuration()
          throws Exception {
    JMidiEvent.builder().tick(2).pitch(2).duration(0).velocity(100).build();
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_InvalidVelocity()
          throws Exception {
    JMidiEvent.builder().tick(-1).pitch(2).duration(1).velocity(200).build();
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_NullClone()
          throws Exception {
    JMidiEvent.builder().clone(null).build();
  }
  
  /**
   * ************** Virtual Instruments
   */
  
  @Test public void testGetOctaveDegree() throws Exception {
    this.initCond();
    assertEquals(12, jVirtualInstrument.getOctaveDegree());
    
  }
  
  @Test public void testGetScale() throws Exception {
    this.initCond();
    assertEquals(scale.toString(), jVirtualInstrument.getScale());
    
  }
  
  @Test public void testGetNoteRepresentation() throws Exception {
    this.initCond();
    assertEquals("C", jVirtualInstrument.getNoteRepresentation(0));
    assertEquals("C#", jVirtualInstrument.getNoteRepresentation(1));
    assertEquals("B", jVirtualInstrument.getNoteRepresentation(11));
    assertEquals(jVirtualInstrument.getNoteRepresentation(0),
            jVirtualInstrument.getNoteRepresentation(12));
    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testGetNoteRepresentation_invalid_negativeValue() throws Exception {
    this.initCond();
    jVirtualInstrument.getNoteRepresentation(-1);
    
  }
  
  /**
   * ************** MIDI Composition
   */
  
  @Test public void TestGetGrid() throws Exception {
    this.initCond();
    assertEquals(
            "{0={1=cs3500.music.model.JMidiEvent{tick=0, pitch=1, velocity=30, channel=0, "
                    + "duration=4}}, 1={1=cs3500.music.model.JMidiEvent{tick=0, pitch=1, "
                    + "velocity=30, channel=0, duration=4}}, 2={1=cs3500.music.model"
                    + ".JMidiEvent{tick=0, pitch=1, velocity=30, channel=0, duration=4}}, "
                    + "3={1=cs3500.music.model.JMidiEvent{tick=0, pitch=1, velocity=30, "
                    + "channel=0, duration=4}}}",
            jMidiComposition.getGrid().toString());
    
  }
  
  
}
  
