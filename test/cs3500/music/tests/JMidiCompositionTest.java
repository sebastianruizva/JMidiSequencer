package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The class {@JMidiCompositionTest} Represents tests for the {@JMidiComposition} class.
 */
public class JMidiCompositionTest {
  
  /**
   * A Midi Composition Example.
   */
  JMidiComposition jMidiCompositionEx = new JMidiComposition();
  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrackEx;
  /**
   * A Midi Event example.
   */
  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
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
   * Initial conditions for testing.
   */
  public void initCond() {
    jMidiCompositionEx = new JMidiComposition();
    jMidiTrackEx = new JMidiTrack(jVirtualInstrument);
    jMidiTrackEx.addEvent(e0);
  }
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  @Test public void testAddTimeSignatureChange() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTimeSignatureChange(1, 3, 4);
    
    assertEquals(
            "cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={1=cs3500.music"
                    + ".model.JTimeSignature{lower=4, "
                    + "upper=3}}, tempoChanges={}, timeSignature=cs3500.music.model"
                    + ".JTimeSignature{lower=4, " + "upper=4}, tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testAddTempoChange() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTempoChange(1, 130);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={}, "
                    + "tempoChanges={1=130}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testAddTrack() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack("test", jMidiTrackEx);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={test=       C  \n" + "    1  X  \n"
                    + "    2  |  \n" + "    3  |  \n" + "    4  |  \n" + "    5  |  \n" + "    6 "
                    + " |  \n" + "    7  |  }, timeSignatureChanges={}, tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testRemoveTimeSignatureChange() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTimeSignatureChange(1, 3, 4);
    jMidiCompositionEx.removeTimeSignatureChange(1);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={}, "
                    + "tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testRemoveTempoChange() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTempoChange(1, 130);
    jMidiCompositionEx.removeTempoChange(1);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={}, "
                    + "tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testRemoveTrack() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack("test", jMidiTrackEx);
    jMidiCompositionEx.removeTrack("test");
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={}, "
                    + "tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddTimeSignatureChange_Invalid_alreadyThere() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTimeSignatureChange(1, 3, 4);
    jMidiCompositionEx.addTimeSignatureChange(1, 3, 4);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddTempoChange_Invalid_alreadyThere() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTempoChange(1, 130);
    jMidiCompositionEx.addTempoChange(1, 130);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddTrack_Invalid_alreadyThere()
          throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack("test", jMidiTrackEx);
    jMidiCompositionEx.addTrack("test", jMidiTrackEx);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveTimeSignatureChange_Invalid_notFound() throws Exception {
    
    
    initCond();
    
    jMidiCompositionEx.removeTimeSignatureChange(1);
    
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveTempoChange_Invalid_notFound() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.removeTempoChange(1);
    
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveTrack_Invalid_notFound()
          throws Exception {
    
    initCond();
    
    jMidiCompositionEx.removeTrack("test");
    
    
  }
  
  
}