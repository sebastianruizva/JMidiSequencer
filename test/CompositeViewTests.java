import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.view.CompositeView;

import static org.junit.Assert.assertEquals;

/**
 * The class {@CompositeViewImplTest} represents tests for the class (@MidiViewImpl}.
 */
public class CompositeViewTests {
  
  /**
   * A JMidiComposition example.
   */
  JMidiComposition jMidiComposition;
  
  /**
   * A JMidiComposition example.
   */
  JMidiComposition.Builder compositionBuilder;
  
  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrack;
  
  /**
   * An Virtual Instrument example for a MIDI Track.
   */
  JVirtualInstrument jVirtualInstrument;
  
  /**
   * A Log that keeps track of the MIDI events.
   */
  StringBuilder log;
  StringBuilder log2;
  
  /**
   * An appendable that keeps track of system messages.
   */
  StringBuilder ap;
  
  /**
   * An scale example for the virtual instrument.
   */
  ArrayList<String> scale = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * Mock sequencer.
   */
  MockSequencer testSequencer;
  
  
  /**
   * Initial conditions for testing.
   */
  public void initCond() {
    
    log = new StringBuilder();
    
    log2 = new StringBuilder();
    
    ap = new StringBuilder();
    
    jVirtualInstrument = new JVirtualInstrument(scale, 0);
    
    jMidiTrack = new JMidiTrack(jVirtualInstrument);
    
    compositionBuilder = JMidiComposition.builder();
    
    testSequencer = new MockSequencer(log);
    
  }
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  @Test public void TestInitialize() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    CompositeView view = new CompositeView(jMidiComposition, log);
  
    //midiView.initController(jMidiComposition, ap);
    assertEquals("sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
            + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n" + "Audio View Ready\n"
            + "Connecting to GUI\n" + "Preparing GUI View\n" + "GUI View Ready\n"
            + "Keyboard Controller Started\n" + "Controller connected to view\n"
            + "Synchronizing views\n" + "Composite view ready!\n", log.toString());
  }
  
  @Test(expected = IllegalArgumentException.class) public void TestNull() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    CompositeView view = new CompositeView(null, null);
    
  }
  
  
}