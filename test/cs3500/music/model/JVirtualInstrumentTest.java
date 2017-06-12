package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The class {@JVirtualInstrumentTest} Represents tests for the {@JVirtualInstrument} class.
 */
public class JVirtualInstrumentTest {
  
  /**
   * An scale example for the virtual instrument.
   */
  private ArrayList<String> scaleEx = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * An Virtual Instrument example for a MIDI Track.
   */
  JVirtualInstrument jVirtualInstrumentEX = new JVirtualInstrument(scaleEx);
  
  
  @Test public void testGetOctaveDegree() throws Exception {
    
    assertEquals(12, jVirtualInstrumentEX.getOctaveDegree());
    
  }
  
  @Test public void testGetScale() throws Exception {
    
    assertEquals(scaleEx.toString(), jVirtualInstrumentEX.getScale());
    
  }
  
  @Test public void testGetNoteRepresentation() throws Exception {
    
    assertEquals("C", jVirtualInstrumentEX.getNoteRepresentation(0));
    assertEquals("C#", jVirtualInstrumentEX.getNoteRepresentation(1));
    assertEquals("B", jVirtualInstrumentEX.getNoteRepresentation(11));
    assertEquals(jVirtualInstrumentEX.getNoteRepresentation(0), jVirtualInstrumentEX.getNoteRepresentation(12));
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testGetNoteRepresentation_invalid_negativeValue() throws Exception {
    
    jVirtualInstrumentEX.getNoteRepresentation(-1);
    
  }
  
}