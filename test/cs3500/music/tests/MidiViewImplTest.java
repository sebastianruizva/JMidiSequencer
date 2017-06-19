package cs3500.music.tests;

import org.junit.Test;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * The class {@MidiViewImplTest} represents tests for the class (@MidiViewImpl}.
 */
public class MidiViewImplTest {

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
   * An example of a midi view implementation.
   */
  MidiViewImpl midiView;

  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */

  @Test
  public void TestMidiView_Composition1NoteIn0() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.addNote(0, 4, 0, 1,
            30).build();

    midiView.initialize(jMidiComposition, ap);
    assertEquals("msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:1000000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:1000000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:1000000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:0, Cmd:144 Chn:0 Ptc:1 Vel:30] \n"
            + "msg[Tck:1000000, Cmd:128 Chn:0 Ptc:1 Vel:30] \n", log.toString());
  }

  /**
   * Initial conditions for testing.
   */
  public void initCond() {

    log = new StringBuilder();

    ap = new StringBuilder();

    jVirtualInstrument = new JVirtualInstrument(scale, new TestSynth(log));

    jMidiTrack = new JMidiTrack(jVirtualInstrument);

    midiView = new MidiViewImpl();

    compositionBuilder = JMidiComposition.builder();


    //Add Mock instrument to the first 21 tracks
    for (int i = 0; i < 20; i++) {

      compositionBuilder.setInstrument(i, jVirtualInstrument);

    }

  }

  @Test
  public void TestMidiView_CompositionEmpty() throws Exception {
    this.initCond();
    jMidiComposition = compositionBuilder.build();

    midiView.initialize(jMidiComposition, ap);
    assertEquals("", log.toString());
  }

  @Test
  public void TestMidiView_CompositionFromReader_singleChannel() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("singleChannel.txt"),
            compositionBuilder);

    midiView.initialize(jMidiComposition, ap);
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
            + "msg[Tck:1600000, Cmd:128 Chn:0 Ptc:76 Vel:70] \n", log.toString());

  }


  @Test
  public void TestMidiView_CompositionFromReader_multipleChannels() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("multipleChannel.txt"),
            compositionBuilder);

    midiView.initialize(jMidiComposition, ap);
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
            + "msg[Tck:1600000, Cmd:128 Chn:3 Ptc:76 Vel:70] \n", log.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void TestMidiView_CompositionFromReader_invalidFile() throws Exception {
    this.initCond();

    jMidiComposition = MusicReader.parseFile(new FileReader("invalid.txt"),
            compositionBuilder);

    midiView.initialize(jMidiComposition, ap);

  }

}