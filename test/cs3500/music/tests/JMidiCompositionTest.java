/*
package cs3500.music.tests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.*;

import static org.junit.Assert.assertEquals;

*/
/**
 * The class {@JMidiCompositionTest} Represents tests for the {@JMidiComposition} class.
 *//*

public class JMidiCompositionTest {
  
  */
/**
   * A Midi Composition Example.
   *//*

  JMidiComposition jMidiCompositionEx;
  */
/**
   * A Midi Track example.
   *//*

  JMidiTrack jMidiTrackEx;
  */
/**
   * A Midi Event example.
   *//*

  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
  */
/**
   * An scale example for the virtual instrument.
   *//*

  private ArrayList<String> scaleEx = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  */
/**
   * An Virtual Instrument example for a MIDI Track.
   *//*

  JVirtualInstrument jVirtualInstrument = new JVirtualInstrument(scaleEx);
  
  */
/**
   * Initial conditions for testing.
   *//*

  public void initCond() {
    //jMidiCompositionEx = JMidiComposition.builder().build();
    jMidiTrackEx = new JMidiTrack(jVirtualInstrument);
    jMidiTrackEx.addEvent(e0);
  }
  
  */
/**
   * ***************
   * TESTS BEGIN.
   * **************
   *//*

  
  @Test public void testAddTrack() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack(1, jMidiTrackEx);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={test=       C  \n" + "    1  X  \n"
                    + "    2  |  \n" + "    3  |  \n" + "    4  |  \n" + "    5  |  \n" + "    6 "
                    + " |  \n" + "    7  |  }, timeSignatureChanges={}, tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test public void testRemoveTrack() throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack(1, jMidiTrackEx);
    jMidiCompositionEx.removeTrack(1);
    
    assertEquals("cs3500.music.model.JMidiComposition{tracks={}, timeSignatureChanges={}, "
                    + "tempoChanges={}, "
                    + "timeSignature=cs3500.music.model.JTimeSignature{lower=4, upper=4}, " +
                    "tempo=120}",
            jMidiCompositionEx.toString());
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddTrack_Invalid_alreadyThere()
          throws Exception {
    
    initCond();
    
    jMidiCompositionEx.addTrack(1, jMidiTrackEx);
    jMidiCompositionEx.addTrack(1, jMidiTrackEx);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveTrack_Invalid_notFound()
          throws Exception {
    
    initCond();
    
    jMidiCompositionEx.removeTrack(1);
    
  }
  
  
}*/
