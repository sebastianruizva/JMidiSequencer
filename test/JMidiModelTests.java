
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.util.MusicReader;

import static org.junit.Assert.assertEquals;

//import static com.sun.tools.hat.internal.parser.Reader.readFile;

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
   * A composition builder example.
   */
  JMidiComposition.Builder compositionBuilder;

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
   * ************** MIDI EVENTS.
   */

  @Test
  public void TestGetTickAndBuilder() throws Exception {
    assertEquals(0, e0.getTick());
    assertEquals(5, e1.getTick());
    assertEquals(2, e2.getTick());
    assertEquals(3, e3.getTick());
  }

  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */

  @Test
  public void TestGetPitchAndBuilder() throws Exception {
    assertEquals(0, e0.getPitch());
    assertEquals(6, e1.getPitch());
    assertEquals(2, e2.getPitch());
    assertEquals(3, e3.getPitch());
  }

  @Test
  public void TestGetVelocityAndBuilder() throws Exception {
    assertEquals(64, e0.getVelocity());
    assertEquals(64, e1.getVelocity());
    assertEquals(64, e2.getVelocity());
    assertEquals(64, e3.getVelocity());
  }

  @Test
  public void TestGetChannelAndBuilder() throws Exception {
    assertEquals(0, e0.getChannel());
    assertEquals(0, e1.getChannel());
    assertEquals(0, e2.getChannel());
    assertEquals(0, e3.getChannel());
  }

  @Test
  public void TestGetDurationAndBuilder() throws Exception {
    assertEquals(7, e0.getDuration());
    assertEquals(3, e1.getDuration());
    assertEquals(1, e2.getDuration());
    assertEquals(3, e3.getDuration());
  }

  @Test
  public void TestToString() throws Exception {
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=0, pitch=0, velocity=64, channel=0,"
                    + " duration=7}",
            e0.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=5, pitch=6, velocity=64, channel=0,"
                    + " duration=3}",
            e1.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=3, pitch=3, velocity=64, channel=0,"
                    + " duration=3}",
            e3.toString());
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=2, pitch=2, velocity=64, channel=0,"
                    + " duration=1}",
            e2.toString());
  }

  @Test
  public void TestClone() throws Exception {
    assertEquals(JMidiEvent.builder().clone(e0).build().toString(), e0.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidBuilder_NegativePitch()
          throws Exception {
    JMidiEvent.builder().tick(2).pitch(-1).duration(1).velocity(100).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidBuilder_NegativeTick()
          throws Exception {
    JMidiEvent.builder().tick(-1).pitch(2).duration(1).velocity(100).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidBuilder_InvalidDuration()
          throws Exception {
    JMidiEvent.builder().tick(2).pitch(2).duration(0).velocity(100).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidBuilder_InvalidVelocity()
          throws Exception {
    JMidiEvent.builder().tick(-1).pitch(2).duration(1).velocity(200).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void TestInvalidBuilder_NullClone()
          throws Exception {
    JMidiEvent.builder().clone(null).build();
  }

  /**
   * ************** Virtual Instruments.
   */

  @Test
  public void testGetOctaveDegree() throws Exception {
    this.initCond();
    assertEquals(12, jVirtualInstrument.getOctaveDegree());

  }


  /**
   * Initial conditions for testing.
   */
  public void initCond() {

    log = new StringBuilder();

    jVirtualInstrument = new JVirtualInstrument(scale, 0);

    jMidiTrack = new JMidiTrack(jVirtualInstrument);

    compositionBuilder = JMidiComposition.builder();

  }

  @Test
  public void testGetScale() throws Exception {
    this.initCond();
    assertEquals(scale.toString(), jVirtualInstrument.getScale());

  }

  @Test
  public void testGetNoteRepresentation() throws Exception {
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
   * ************** MIDI Composition.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_NegativeStart() {

    JMidiComposition.builder().addNote(-1, 1, 0, 0, 0).build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_NegativeChannel() {

    JMidiComposition.builder().addNote(1, 1, -1, 0, 0).build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_EndSmallerTHanStart() {

    JMidiComposition.builder().addNote(10, 1, 0, 0, 0).build();

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_NegativeInstrument() {

    JMidiComposition.builder().addNote(1, 10, -1, 0, 0).build();

  }

  @Test
  public void testAddEvent_Duration1() {
    this.initCond();
    jMidiComposition = JMidiComposition.builder().addNote(1, 1, 0, 0,
            0).build();

    assertEquals("       C0 \n" + "    1     \n" + "    2  X  ",
            jMidiComposition.toString());

  }

  @Test
  public void testAddEvent_Duration10() {
    this.initCond();
    jMidiComposition = JMidiComposition.builder().addNote(1, 10, 0, 0,
            0).build();

    assertEquals("       C0 \n" + "    1     \n" + "    2  X  \n" + "    3  |  \n"
            + "    4  |  \n"
            + "    5  |  \n" + "    6  |  \n" + "    7  |  \n" + "    8  |  \n" + "    9  |  \n"
            + "   10  |  \n" + "   11  |  ", jMidiComposition.toString());

  }

  @Test
  public void testAddEvent_MultipleEvents_DifferentChannel_samePitch() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).build();

    assertEquals("       C0 \n" + "    1     \n" + "    2  X  \n" + "    3  |  ",
            jMidiComposition.toString());

  }

  @Test
  public void testAddEvent_FromFile() throws FileNotFoundException {
    this.initCond();
    jMidiComposition = MusicReader.parseFile(new FileReader("singleChannel.txt"),
            compositionBuilder);

    assertEquals("       E0   F0  F#0   G0  G#0   A0  A#0   B0   C1  C#1   D1  D#1   E1"
            + "   F1  F#1"
            + "   G1  G#1   A1  A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2   G2  G#2   A2  A#2  "
            + " B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  "
            + "D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5   "
            + "G5  G#5   A5  A#5   B5   C6  C#6   D6  D#6   E6 \n"
            + "    1  X                                                                          "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                          \n"
            + "    2  |                                                                          "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       X  \n"
            + "    3  |                                                                          "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  \n"
            + "    4                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  \n"
            + "    5                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  \n"
            + "    6                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  \n"
            + "    7                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  \n"
            + "    8                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                       |  ", jMidiComposition.toString());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_FromFile() throws FileNotFoundException {

    this.initCond();
    jMidiComposition = MusicReader.parseFile(new FileReader("invalid.txt"),
            compositionBuilder);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_MultipleEvents_sameChannel_samePitch() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 0, 0, 0).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_PitchTooHigh() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 128, 0)
            .addNote(1, 2, 0, 0, 0).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddEvent_Invalid_VolumeTooHigh() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 128, 0)
            .addNote(1, 2, 0, 0, 0).build();
  }

  @Test
  public void testAddEvent_MultipleEvents_DifferentChannel_DifferentPitch() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).build();

    assertEquals("       C0 \n" + "    1     \n" + "    2  X  \n" + "    3  |  ",
            jMidiComposition.toString());

  }

  @Test
  public void testAddEvent_MultipleEvents_EdgePitch_EdgeVelocity() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 127, 0)
            .addNote(1, 2, 2, 0, 127).build();

    assertEquals("       C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 "
            + " C#1   D1"
            + "  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2  C#2   D2  D#2   E2   F2  F#2  "
            + " G2  G#2   A2  A#2   B2   C3  C#3   D3  D#3   E3   F3  F#3   G3  G#3   A3  A#3   "
            + "B3   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5"
            + "   E5   F5  F#5   G5  G#5   A5  A#5   B5   C6  C#6   D6  D#6   E6   F6  F#6   G6  "
            + "G#6   A6  A#6   B6   C7  C#7   D7  D#7   E7   F7  F#7   G7  G#7   A7  A#7   B7   "
            + "C8  C#8   D8  D#8   E8   F8  F#8   G8  G#8   A8  A#8   B8   C9  C#9   D9  D#9   E9"
            + "   F9  F#9   G9  G#9   A9  A#9   B9  C10  C#10 D10  D#10 E10  F10  F#10 G10 \n"
            + "    1                                                                             "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                       \n"
            + "    2  X                                                                          "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                    X  \n"
            + "    3  |                                                                          "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                                  "
            + "                                                                    |  ",
            jMidiComposition.toString());

  }

  @Test
  public void testSetTempo() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setTempo(1).build();

    assertEquals(1,
            jMidiComposition.getTempo());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetTempo_InvalidNUmber() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setTempo(-1).build();

  }

  @Test
  public void testGetSectorType_Head() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setTempo(1).build();

    assertEquals("X",
            jMidiComposition.getSectorType(1, 0).toString());

  }

  @Test
  public void testGetSectorType_Tail() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setTempo(1).build();

    assertEquals("|",
            jMidiComposition.getSectorType(2, 0).toString());

  }

  @Test
  public void testGetSectorType_Rest() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setTempo(1).build();

    assertEquals(" ",
            jMidiComposition.getSectorType(3, 0).toString());

  }

  @Test
  public void testSetInstrument() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 0, 0)
            .addNote(1, 2, 2, 0, 0).setInstrument(1,
                    jVirtualInstrument).build();

    assertEquals(jVirtualInstrument,
            jMidiComposition.getTracks().get(1).getInstrument());

  }


  @Test
  public void testGetMinimumAndMaximumValues() {
    this.initCond();
    jMidiComposition = compositionBuilder
            .addNote(1, 2, 0, 12, 0)
            .addNote(1, 2, 2, 11, 0).setTempo(1).build();

    assertEquals(11,
            jMidiComposition.getMinPitch());

    assertEquals(12,
            jMidiComposition.getMaxPitch());

    assertEquals(2,
            jMidiComposition.getMaxTick());

  }


}
  
