package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The class {@JMidiEventTest} Represents tests for the {@JMidiEvent} class.
 */
public class JMidiEventTest {
  
  /**
   * Various MidiEvent examples for tests.
   */
  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
  JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).channel(3).build();
  JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).velocity(100).build();
  JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).velocity(11).pitch(3).build();
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
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
    assertEquals(100, e2.getVelocity());
    assertEquals(11, e3.getVelocity());
  }
  
  @Test public void TestGetChannelAndBuilder() throws Exception {
    assertEquals(0, e0.getChannel());
    assertEquals(3, e1.getChannel());
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
            "cs3500.music.model.JMidiEvent{tick=5, pitch=6, velocity=64, channel=3, duration=3}",
            e1.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=3, pitch=3, velocity=11, channel=0, duration=3}",
            e3.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=2, pitch=2, velocity=100, channel=0, duration=1}",
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
  
  @Test(expected = IllegalArgumentException.class) public void TestInvalidBuilder_NullClone() throws Exception {
    JMidiEvent.builder().clone(null).build();
  }
  
  
}