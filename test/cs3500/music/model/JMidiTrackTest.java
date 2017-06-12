package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * The class {@JMidiTrackTest} Represents tests for the {@JMidiTrack} class.
 */
public class JMidiTrackTest {
  
  /**
   * A Midi Track example.
   */
  JMidiTrack jMidiTrack;
  /**
   * A Midi Event examples.
   */
  JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
  JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
  JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
  JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
  JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
  JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
  JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
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
    jMidiTrack = new JMidiTrack(jVirtualInstrument);
  }
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  @Test public void testAvailable_EnoughEmptySpace() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    
    assertEquals(true, jMidiTrack.available(10, 2, 10));
    
  }
  
  @Test public void testAvailable_NotEnoughEmptySpace() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    
    assertEquals(false, jMidiTrack.available(2, 2, 10));
    
  }
  
  @Test public void testAvailable_EmptyTrack() {
    
    initCond();
    
    assertEquals(true, jMidiTrack.available(2, 2, 10));
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAvailable_NegativeTick() {
    
    initCond();
    
    assertEquals(false, jMidiTrack.available(-2, 2, 10));
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAvailable_NegativePitch() {
    
    initCond();
    
    assertEquals(false, jMidiTrack.available(2, -2, 10));
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAvailable_NegativeDistance() {
    
    initCond();
    
    assertEquals(false, jMidiTrack.available(2, 2, -10));
    
  }
  
  @Test public void testAddEvent_MultipleEvents() {
    
    initCond();
    
    assertEquals("", jMidiTrack.toString());
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    
    assertEquals("       C   C#    D   D#    E    F   F#  \n" + "    1  X                                \n"
            + "    2  |    X                           \n" + "    3  |    |    X                      \n"
            + "    4  |    |         X                 \n" + "    5  |    |         |    X            \n"
            + "    6  |    |    X    |    |         X  \n" + "    7  |    |    |         |       "
            + "  |  \n"
            + "    8            |         |         |  ", jMidiTrack.toString());
    
    
  }
  
  @Test public void testAddEvent_SingleEvent() {
    
    initCond();
    
    assertEquals("", jMidiTrack.toString());
    
    jMidiTrack.addEvent(e0);
    
    assertEquals("       C  \n" + "    1  X  \n" + "    2  |  \n" + "    3  |  \n" + "    4  | " + " \n"
            + "    5  |  \n" + "    6  |  \n" + "    7  |  ", jMidiTrack.toString());
    
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddEvent_Invalid_NullEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(null);
    
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testAddEvent_Invalid_SomethingOnTheWay() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e0);
    
  }
  
  @Test public void testGetTicksOfMIDIEvent_MultipleTicks() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    
    assertEquals("[0, 1, 2, 3, 4, 5, 6]", jMidiTrack.getTicksOfEvent(e0).toString());
    
  }
  
  @Test public void testGetTicksOfMIDIEvent_SingleTicks() {
    
    initCond();
    
    jMidiTrack.addEvent(e2);
    
    assertEquals("[2]", jMidiTrack.getTicksOfEvent(e2).toString());
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testGetTicksOfMIDIEvent_Invalid_NotFound() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e0);
    
    jMidiTrack.getTicksOfEvent(e2);
    
  }
  
  
  @Test(expected = IllegalArgumentException.class) public void testGetTicksOfMIDIEvent_Invalid_NullEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e0);
    
    jMidiTrack.getTicksOfEvent(null);
    
  }
  
  @Test public void testRemoveEvent_SingleEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e2);
    jMidiTrack.removeEvent(e2);
    
    assertEquals("", jMidiTrack.toString());
    
  }
  
  @Test public void testRemoveEvent_MultipleEvents() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.removeEvent(e1);
    jMidiTrack.removeEvent(e2);
    jMidiTrack.removeEvent(e3);
    jMidiTrack.removeEvent(e4);
    
    assertEquals("", jMidiTrack.toString());
    
  }
  
  @Test public void testRemoveEvent_NotAll() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.removeEvent(e1);
    jMidiTrack.removeEvent(e4);
    
    assertEquals("       C   C#    D   D#  \n" + "    1                    \n" + "    2                    \n" + "    3            X       \n"
            + "    4                 X  \n" + "    5                 |  \n" + "    6                 |  ", jMidiTrack.toString());
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveEvent_Invalid_NullEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e0);
    
    jMidiTrack.removeEvent(null);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testRemoveEvent_Invalid_NotFound() {
    
    initCond();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e0);
    
    jMidiTrack.removeEvent(e3);
    
  }
  
  @Test public void testMoveEvent_singleEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.moveEvent(e1, 3, 3);
    
    
    assertEquals("       C   C#    D   D#  \n" + "    1                    \n" + "    2                    \n" + "    3                    \n"
            + "    4                 X  \n" + "    5                 |  \n" + "    6             "
            + "    |  ", jMidiTrack.toString());
    
  }
  
  @Test public void testMoveEvent_multipleEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.moveEvent(e1, 3, 0);
    jMidiTrack.moveEvent(e2, 4, 8);
    jMidiTrack.moveEvent(e3, 5, 9);
    
    assertEquals("       C   C#    D   D#    E    F  \n" + "    1                 X            \n" + "    2                 |            \n" + "    3                 |            \n"
            + "    4                              \n" + "    5                              \n" + "    6                              \n" + "    7                              \n"
            + "    8                              \n" + "    9                      X       \n" + "   10                           X  \n" + "   11                           |  \n"
            + "   12                           |  ", jMidiTrack.toString());
    
  }
  
  @Test public void testMoveEvent_leaveSome() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.moveEvent(e1, 3, 0);
    jMidiTrack.moveEvent(e2, 4, 8);
    
    assertEquals("       C   C#    D   D#    E  \n" + "    1                 X       \n" + "   " + " 2                 |       \n" + "    3                 |       \n"
            + "    4                 X       \n" + "    5                 |       \n" + "" + "    6  " + "               |       \n" + "    7                         \n"
            + "    8                         \n" + "    9                      X  ", jMidiTrack.toString());
    
  }
  
  @Test public void testMoveEvent_closeToOriginal() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.moveEvent(e1, 1, 1);
    
    assertEquals("       C   C#  \n" + "    1          \n" + "    2       X  \n" + "    3       |  "
            + "\n" + "    4       |  ", jMidiTrack.toString());
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testMoveEvent_Invalid_Null() {
    
    initCond();
    jMidiTrack.addEvent(e1);
    jMidiTrack.moveEvent(null, 0, 0);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testMoveEvent_Invalid_NegativeVal() {
    
    initCond();
    jMidiTrack.addEvent(e0);
    jMidiTrack.moveEvent(e0, 1, -1);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testMoveEvent_Invalid_SomethingThereAlready() {
    
    initCond();
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.moveEvent(e1, 0, 1);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testMoveEvent_Invalid_notSuchEvent() {
    
    initCond();
    jMidiTrack.moveEvent(e1, 0, 1);
    
  }
  
  @Test public void testResizeEvent_singleEvent() {
    
    initCond();
    
    jMidiTrack.addEvent(e1);
    jMidiTrack.resizeEvent(e1, 20);
    
    
    assertEquals("       C   C#    D   D#    E    F   F#  \n" + "    1                                   \n"
            + "    2                                   \n" + "    3                                   \n"
            + "    4                                   \n" + "    5                                   \n"
            + "    6                                X  \n" + "    7                                |  \n"
            + "    8                                |  \n" + "    9                                |  \n"
            + "   10                                |  \n" + "   11                                |  \n"
            + "   12                                |  \n" + "   13                              "
            + "  |  \n"
            + "   14                                |  \n" + "   15                                |  \n"
            + "   16                                |  \n" + "   17                              "
            + "  |  \n"
            + "   18                                |  \n" + "   19                              "
            + "  |  \n"
            + "   20                                |  \n" + "   21                                |  \n"
            + "   22                                |  \n" + "   23                              "
            + "  |  \n"
            + "   24                                |  \n" + "   25                              "
            + "  |  ", jMidiTrack.toString());
    
  }
  
  
  @Test(expected = IllegalArgumentException.class) public void testResizeEvent_Invalid_Null() {
    
    initCond();
    jMidiTrack.addEvent(e1);
    jMidiTrack.resizeEvent(null, 1);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testResizeEvent_Invalid_NegativeVal() {
    
    initCond();
    jMidiTrack.addEvent(e0);
    jMidiTrack.resizeEvent(e0, -1);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testResizeEvent_Invalid_SomethingThereAlready() {
    
    initCond();
    jMidiTrack.addEvent(JMidiEvent.builder().clone(e0).tick(5).build());
    jMidiTrack.addEvent(e0);
    jMidiTrack.resizeEvent(e0, 20);
    
  }
  
  @Test(expected = IllegalArgumentException.class) public void testResizeEvent_Invalid_notSuchEvent() {
    
    initCond();
    jMidiTrack.resizeEvent(e1, 1);
    
  }
  
  
  @Test public void testToString_EmptyTrack() {
    
    initCond();
    
    assertEquals("", jMidiTrack.toString());
    
  }
  
  @Test public void testToString_SimpleTrack_6Events() {
    
    initCond();
    JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
    JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
    JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
    JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
    JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
    JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
    JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    
    assertEquals("       C   C#    D   D#    E    F   F#  \n" + "    1  X                                \n"
            + "    2  |    X                           \n" + "    3  |    |    X                      \n"
            + "    4  |    |         X                 \n" + "    5  |    |         |    X            \n"
            + "    6  |    |    X    |    |         X  \n" + "    7  |    |    |         |         |  \n"
            + "    8            |         |         |  ", jMidiTrack.toString());
  }
  
  @Test public void testToString_LargeTrack_10Events() {
    
    initCond();
    JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
    JMidiEvent e1 = JMidiEvent.builder().tick(5).pitch(6).build();
    JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
    JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
    JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
    JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
    JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
    JMidiEvent e7 = JMidiEvent.builder().tick(100).pitch(2).duration(3).build();
    JMidiEvent e8 = JMidiEvent.builder().tick(40).pitch(5).duration(10).build();
    JMidiEvent e9 = JMidiEvent.builder().tick(50).pitch(15).duration(15).build();
    JMidiEvent e10 = JMidiEvent.builder().tick(70).pitch(10).duration(30).build();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e1);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    jMidiTrack.addEvent(e7);
    jMidiTrack.addEvent(e8);
    jMidiTrack.addEvent(e9);
    jMidiTrack.addEvent(e10);
    
    
    assertEquals("       C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D " + "  D#  \n"
            + "    1  X                                                                          " + "   \n"
            + "    2  |    X                                                                     " + "   \n"
            + "    3  |    |    X                                                                " + "   \n"
            + "    4  |    |         X                                                           " + "   \n"
            + "    5  |    |         |    X                                                      " + "   \n"
            + "    6  |    |    X    |    |         X                                            " + "   \n"
            + "    7  |    |    |         |         |                                            " + "   \n"
            + "    8            |         |         |                                            " + "   \n"
            + "    9                                                                             " + "   \n"
            + "   10                                                                             " + "   \n"
            + "   11                                                                             " + "   \n"
            + "   12                                                                             " + "   \n"
            + "   13                                                                             " + "   \n"
            
            + "   14                                                                             " + "   \n"
            + "   15                                                                             " + "   \n"
            + "   16                                                                             " + "   \n"
            + "   17                                                                             " + "   \n"
            + "   18                                                                             " + "   \n"
            + "   19                                                                             " + "   \n"
            + "   20                                                                             " + "   \n"
            + "   21                                                                             " + "   \n"
            + "   22                                                                             " + "   \n"
            + "   23                                                                             "
            + "" + "   \n"
            + "   24                                                                             " + "   \n"
            + "   25                                                                             " + "   \n"
            + "   26                                                                             "
            + "" + "   \n"
            + "   27                                                                             " + "   \n"
            + "   28                                                                             " + "   \n"
            + "   29                                                                             " + "   \n"
            
            + "   30                                                                             " + "   \n"
            + "   31                                                                             " + "   \n"
            + "   32                                                                             " + "   \n"
            + "   33                                                                             " + "   \n"
            + "   34                                                                             " + "   \n"
            + "   35                                                                             " + "   \n"
            + "   36                                                                             " + "   \n"
            + "   37                                                                             "
            + "" + "   \n"
            + "   38                                                                             " + "   \n"
            + "   39                                                                             " + "   \n"
            + "   40                                                                             " + "   \n"
            + "   41                           X                                                 " + "   \n"
            + "   42                           |                                                 " + "   \n"
            + "   43                           |                                                 " + "   \n"
            + "   44                           |                                                 " + "   \n"
            + "   45                           |                                                 " + "   \n"
            + "   46                           |                                                 " + "   \n"
            + "   47                           |                                                 " + "   \n"
            + "   48                           |                                                 " + "   \n"
            + "   49                           |                                                 " + "   \n"
            + "   50                           |                                                 " + "   \n"
            + "   51                                                                             " + "X  \n"
            + "   52                                                                             " + "|  \n"
            + "   53                                                                             " + "|  \n"
            + "   54                                                                             " + "|  \n"
            + "   55                                                                             " + "|  \n"
            + "   56                                                                             " + "|  \n"
            + "   57                                                                             " + "|  \n"
            + "   58                                                                             " + "|  \n"
            + "   59                                                                             " + "|  \n"
            + "   60                                                                             " + "|  \n"
            
            + "   61                                                                             " + "|  \n"
            + "   62                                                                             " + "|  \n"
            + "   63                                                                             " + "|  \n"
            + "   64                                                                             " + "|  \n"
            + "   65                                                                             " + "|  \n"
            + "   66                                                                             " + "   \n"
            + "   67                                                                             " + "   \n"
            + "   68                                                                             " + "   \n"
            + "   69                                                                             " + "   \n"
            + "   70                                                                             "
            + "" + "   \n"
            + "   71                                                    X                        " + "   \n"
            + "   72                                                    |                        " + "   \n"
            + "   73                                                    |                        "
            + "" + "   \n"
            + "   74                                                    |                        " + "   \n"
            + "   75                                                    |                        " + "   \n"
            + "   76                                                    |                        " + "   \n"
            
            + "   77                                                    |                        " + "   \n"
            + "   78                                                    |                        " + "   \n"
            + "   79                                                    |                        " + "   \n"
            + "   80                                                    |                        " + "   \n"
            + "   81                                                    |                        " + "   \n"
            + "   82                                                    |                        " + "   \n"
            + "   83                                                    |                        " + "   \n"
            + "   84                                                    |                        "
            + "" + "   \n"
            + "   85                                                    |                        " + "   \n"
            + "   86                                                    |                        " + "   \n"
            + "   87                                                    |                        " + "   \n"
            + "   88                                                    |                        " + "   \n"
            + "   89                                                    |                        " + "   \n"
            + "   90                                                    |                        " + "   \n"
            + "   91                                                    |                        " + "   \n"
            + "   92                                                    |                        " + "   \n"
            + "   93                                                    |                        " + "   \n"
            + "   94                                                    |                        " + "   \n"
            + "   95                                                    |                        " + "   \n"
            + "   96                                                    |                        " + "   \n"
            + "   97                                                    |                        " + "   \n"
            + "   98                                                    |                        " + "   \n"
            + "   99                                                    |                        " + "   \n"
            + "  100                                                    |                        " + "   \n"
            + "  101            X                                                                " + "   \n"
            + "  102            |                                                                " + "   \n"
            + "  103            |                                                                " + "   ", jMidiTrack.toString());
  }
  
  @Test public void TestGetEventsOnTick_MultipleEvents_Tick1() {
    initCond();
    JMidiEvent e0 = JMidiEvent.builder().duration(7).build();
    JMidiEvent e2 = JMidiEvent.builder().tick(2).pitch(2).duration(1).build();
    JMidiEvent e3 = JMidiEvent.builder().tick(3).duration(3).pitch(3).build();
    JMidiEvent e4 = JMidiEvent.builder().tick(4).pitch(4).duration(4).build();
    JMidiEvent e5 = JMidiEvent.builder().tick(1).pitch(1).duration(6).build();
    JMidiEvent e6 = JMidiEvent.builder().tick(5).pitch(2).duration(3).build();
    JMidiEvent e7 = JMidiEvent.builder().tick(100).pitch(2).duration(3).build();
    JMidiEvent e8 = JMidiEvent.builder().tick(90).pitch(20).duration(3).build();
    
    jMidiTrack.addEvent(e0);
    jMidiTrack.addEvent(e2);
    jMidiTrack.addEvent(e3);
    jMidiTrack.addEvent(e4);
    jMidiTrack.addEvent(e5);
    jMidiTrack.addEvent(e6);
    jMidiTrack.addEvent(e7);
    jMidiTrack.addEvent(e8);
    
    assertEquals(
            "[cs3500.music.model.JMidiEvent{tick=0, pitch=0, velocity=64, channel=0, duration=7}, "
                    + "cs3500.music.model.JMidiEvent{tick=1, pitch=1, velocity=64, channel=0, "
                    + "duration=6}]", jMidiTrack.getEventsOnTick(1).toString());
  }
  
  @Test public void TestGetEventsOnTick_SingleEvent() {
    initCond();
    
    jMidiTrack.addEvent(e5);
    
    assertEquals(
            "[cs3500.music.model.JMidiEvent{tick=1, pitch=1, velocity=64, channel=0, duration=6}]",
            jMidiTrack.getEventsOnTick(1).toString());
  }
  
  @Test public void TestGetEventsOnTick_NoEvent() {
    initCond();
    
    assertEquals("[]", jMidiTrack.getEventsOnTick(1).toString());
  }
  
  @Test public void TestGetEventsOnPosition() {
    initCond();
    
    jMidiTrack.addEvent(e5);
    
    assertEquals(
            "cs3500.music.model.JMidiEvent{tick=1, pitch=1, velocity=64, channel=0, duration=6}",
            jMidiTrack.getEventOnPosition(1, 1).toString());
  }
  
  
  @Test(expected = IllegalArgumentException.class) public void TestGetEventsOnPosition_Invalid_notFound() {
    
    initCond();
    jMidiTrack.getEventOnPosition(1, 1);
    
  }
  
}
  
