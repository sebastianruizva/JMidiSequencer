import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.model.Repeat;
import cs3500.music.util.JMidiUtils;
import cs3500.music.util.MusicReader;
import cs3500.music.view.AudioView;
import cs3500.music.view.CompositeView;

import static org.junit.Assert.assertEquals;

/**
 * The class {@MidiViewImplTest} represents tests for the class (@MidiViewImpl}.
 */
public class AudioViewTests {

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
   * Initial conditions for testing.
   */
  public void initCond() {
    
    log = new StringBuilder();
    
    log2 = new StringBuilder();
    
    ap = new StringBuilder();
    
    jVirtualInstrument = new JVirtualInstrument(scale, 0);
    
    jMidiTrack = new JMidiTrack(jVirtualInstrument);
    
    compositionBuilder = JMidiComposition.builder();
    
  }

  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */

  @Test
  public void TestMidiView_Composition1NoteIn0() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
  
    AudioView view = new AudioView(jMidiComposition, log);
    File file = view.export();
    Sequence sequence = MidiSystem.getSequence(file);
  
    //midiView.initController(jMidiComposition, ap);
    assertEquals("Track #1\n" + "msg[Tck:0, Cmd:192 Chn:1 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n" + "Track #2\n"
            + "msg[Tck:0, Cmd:192 Chn:5 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n", JMidiUtils.translateSequence(sequence));
  }


  @Test
  public void TestMidiView_CompositionEmpty() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.build();
  
    //midiView.initController(jMidiComposition, ap);
    assertEquals("sequencer initialized \n", log.toString());
  }

  @Test
  public void TestMidiView_CompositionFromReader_singleChannel() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("singleChannel.txt"),
            compositionBuilder);
  
    AudioView view = new AudioView(jMidiComposition, log);
    view.setReceiver(new MockReceiver(log));
    File file = view.export();
  
    //midiView.initController(jMidiComposition, ap);
    assertEquals("msg[Tck:0, Cmd:144 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:600000, Cmd:128 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:600000, Cmd:128 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:600000, Cmd:128 Chn:0 Ptc:4 Vel:72] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:0 Ptc:76 Vel:70] \n"
                    + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n",
            JMidiUtils.translateSequence(MidiSystem.getSequence(file)));

  }


  @Test
  public void TestMidiView_CompositionFromReader_multipleChannels() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("multipleChannel.txt"),
            compositionBuilder);
  
    AudioView view = new AudioView(jMidiComposition, log);
    view.setReceiver(new MockReceiver(log));
  
    File file = view.export();
  
    //midiView.initController(jMidiComposition, ap);
    assertEquals("msg[Tck:0, Cmd:144 Chn:1 Ptc:4 Vel:72] \n"
            + "msg[Tck:600000, Cmd:128 Chn:1 Ptc:4 Vel:72] \n"
            + "msg[Tck:0, Cmd:144 Chn:1 Ptc:4 Vel:72] \n"
            + "msg[Tck:600000, Cmd:128 Chn:1 Ptc:4 Vel:72] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n"
            + "msg[Tck:200000, Cmd:144 Chn:3 Ptc:76 Vel:70] \n"
                    + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n",
            JMidiUtils.translateSequence(MidiSystem.getSequence(file)));

  }

  @Test(expected = IllegalArgumentException.class)
  public void TestMidiView_CompositionFromReader_invalidFile() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("invalid.txt"),
            compositionBuilder);
  
    AudioView view = new AudioView(jMidiComposition, log);
    view.setReceiver(new MockReceiver(log));
  
    new AudioView(jMidiComposition, ap).initController();
  
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void TestMidiView_CompositionFromReader_invalidNull() throws Exception {
    this.initCond();
  
    new AudioView(null, null).initController();
    
  }
  
  @Test public void TestMidiView_refreshSequencer() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
  
    AudioView view = new AudioView(jMidiComposition, log);
    File file = view.export();
    Sequence sequence = MidiSystem.getSequence(file);
    
    assertEquals("Track #1\n" + "msg[Tck:0, Cmd:192 Chn:1 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n" + "Track #2\n"
            + "msg[Tck:0, Cmd:192 Chn:5 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n", JMidiUtils.translateSequence(sequence));
  
    jMidiComposition.addNote(40, 1, 1);
    //view.refreshSequencer();
    file = view.export();
    sequence = MidiSystem.getSequence(file);
  
    assertEquals("Track #1\n" + "msg[Tck:0, Cmd:192 Chn:1 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:24, Cmd:144 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n"
            + "msg[Tck:120, Cmd:128 Chn:1 Ptc:1 Vel:30] \n" + "Track #2\n"
            + "msg[Tck:0, Cmd:192 Chn:5 Ptc:0 Vel:0] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:24, Cmd:144 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n"
            + "msg[Tck:96, Cmd:128 Chn:5 Ptc:3 Vel:2] \n", JMidiUtils.translateSequence(sequence));
    
  }
  
  @Test public void TestMidiView_play() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.play();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Playing Sequence\n");
  }
  
  @Test public void TestMidiView_rewind() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.rewind();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Rewinding Sequence\n");
  }
  
  @Test public void TestMidiView_stop() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.pause();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Stopping Sequence\n");
  }
  
  @Test public void TestMidiView_beginning() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.beginning();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "jumping to Start\n");
  }
  
  @Test public void TestMidiView_end() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.end();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "jumping End\n");
  }
  
  @Test public void TestMidiView_export() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    
    view.export();
    assertEquals(log.toString(),
            "sequencer initialized \n" + "Preparing Audio View\n" + "Initializing Sequencer\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Exporting Sequence...\n" + "Done :)\n");
  }
  
  @Test public void TestMidiView_playRepeats() throws Exception {
    this.initCond();
    jMidiComposition =
            compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 1, 5, 3, 2).addNote(7, 8, 0, 3, 6)
                    .addRepeat(new Repeat(1, 2)).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    view.setReceiver(new MockReceiver(log2));
    view.play();
    
    for (int i = 0; i < 100; i++) {
      view.forward();
    }
    
    assertEquals(log2.toString(),
            "note 1 on velocity: 30\n" + "note 1 on velocity: 0\n" + "note 1 on velocity: 30\n"
                    + "note 1 on velocity: 0\n" + "note 1 on velocity: 30\n"
                    + "note off 1, velocity: 30\n" + "note off 1, velocity: 30\n"
                    + "note off 1, velocity: 30\n" + "note 3 on velocity: 6\n"
                    + "note 3 on velocity: 0\n" + "note 3 on velocity: 6\n"
                    + "note off 3, velocity: 6\n");
    
  }
  
  @Test public void TestMidiView_playEnding() throws Exception {
    this.initCond();
    jMidiComposition =
            compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 1, 5, 3, 2).addNote(7, 8, 0, 3, 6)
                    .addRepeat(new Repeat(Repeat.Type.ENDING, 1, 2)).build();
    
    AudioView view = new AudioView(jMidiComposition, log);
    view.setReceiver(new MockReceiver(log2));
    view.play();
    
    for (int i = 0; i < 100; i++) {
      view.forward();
    }
    
    assertEquals(log2.toString(),
            "note 1 on velocity: 30\n" + "note 1 on velocity: 30\n" + "note 1 on velocity: 30\n"
                    + "note 1 on velocity: 0\n" + "note 1 on velocity: 30\n"
                    + "note 1 on velocity: 0\n" + "note 1 on velocity: 30\n"
                    + "note 1 on velocity: 0\n" + "note 1 on velocity: 30\n"
                    + "note off 1, velocity: 30\n" + "note off 1, velocity: 30\n"
                    + "note off 1, velocity: 30\n" + "note 3 on velocity: 6\n"
                    + "note 3 on velocity: 0\n" + "note 3 on velocity: 6\n"
                    + "note off 3, velocity: 6\n");
    
  }
  
  @Test public void TestCompositeView_Initialize() throws Exception {
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
  
  @Test(expected = IllegalArgumentException.class) public void TestCompositeView_Null()
          throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 3, 5, 3, 2).build();
    CompositeView view = new CompositeView(null, null);
    
  }
  
  @Test public void TestCompositeView_practiceModeEnable() throws Exception {
    this.initCond();
    jMidiComposition =
            compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 1, 5, 3, 2).addNote(7, 8, 0, 3, 6)
                    .addRepeat(new Repeat(1, 2)).build();
    
    CompositeView view = new CompositeView(jMidiComposition, log);
    view.practiceEnabled();
    
    assertEquals(log.toString(),
            "Preparing Audio View\n" + "Initializing Sequencer\n" + "Adding Track #0\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Connecting to GUI\n" + "Preparing GUI View\n"
                    + "GUI View Ready\n" + "Composite view ready!\n" + "Practice Mode Enabled\n");
    
  }
  
  @Test public void TestCompositeView_practiceModeDisable() throws Exception {
    this.initCond();
    jMidiComposition =
            compositionBuilder.addNote(1, 4, 1, 1, 30).addNote(1, 1, 5, 3, 2).addNote(7, 8, 0, 3, 6)
                    .addRepeat(new Repeat(1, 2)).build();
    
    CompositeView view = new CompositeView(jMidiComposition, log);
    view.disablePracticeMode();
    
    assertEquals(log.toString(),
            "Preparing Audio View\n" + "Initializing Sequencer\n" + "Adding Track #0\n"
                    + "Adding Track #1\n" + "Adding Track #5\n" + "Sequencer Ready\n"
                    + "Audio View Ready\n" + "Connecting to GUI\n" + "Preparing GUI View\n"
                    + "GUI View Ready\n" + "Composite view ready!\n" + "Practice Mode Disabled\n");
    
  }

}