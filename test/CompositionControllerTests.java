import org.junit.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;
import cs3500.music.view.visual.MusicEditorGUI;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the actions of KeyBindings. These tests are to ensure that the correct actions
 * are being taken when pressing a given key, and that effects are present as well.
 */
public class CompositionControllerTests {

  /**
   * An scale example for the virtual instrument.
   */
  private ArrayList<String> scale = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));

  /**
   * A Mock for Keybindings.
   */
  private MockKeyHandler handler;

  /**
   * A GUI view for testing.
   */
  private MusicEditorGUI gui;

  /**
   * Initial conditions for testing.
   */
  private void initCond() {
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
    
    jVirtualInstrument = new JVirtualInstrument(scale, 0);

    compositionBuilder = JMidiComposition.builder();

    jMidiComposition = compositionBuilder
            .addNote(0, 4, 0, 50, 100)
            .addNote(9, 10, 0, 50, 100)
            .build();

    gui = new MusicEditorGUI(jMidiComposition, new StringBuilder());

    handler = new MockKeyHandler(gui, new StringBuilder());
  }

  @Test
  public void testHittingRightCausesCursorIncreaseByOneAndForwarding() {
    initCond();

    handler.keyPressed(KeyEvent.VK_RIGHT);

    assertEquals(handler.toString(), "Cursor position increased by one.\n"
            + "[old cursor position: " + 0 + "]\n"
            + "[new cursor position: " + 1 + "]"
            + "Forwarding.\n");
  }

  @Test
  public void testHittingRightAtMaxTickCausesFailureAndForwarding() {
    initCond();

    gui.setCursorPosition(10);
    handler.keyPressed(KeyEvent.VK_RIGHT);

    assertEquals(handler.toString(), "Cannot move cursor any farther right.\n"
            + "[old cursor position: " + 10 + "]\n"
            + "[new cursor position: " + 10 + "]\n"
            + "Forwarding.\n");
  }

  @Test
  public void testHittingLeftCausesCursorDecreaseByOneAndRewinding() {
    initCond();

    gui.setCursorPosition(10);
    handler.keyPressed(KeyEvent.VK_LEFT);

    assertEquals("Cursor position decreased by one.\n"
            + "[old cursor position: " + 10 + "]\n"
            + "[new cursor position: " + 9 + "]\n"
            + "Rewinding.\n", handler.toString());
  }

  @Test
  public void testHittingLeftAtZeroCausesFailureAndRewinding() {
    initCond();

    handler.keyPressed(KeyEvent.VK_LEFT);

    assertEquals(handler.toString(), "Cursor position cannot be decreased.\n"
                    + "[old cursor position: " + 0 + "]\n"
                    + "[new cursor position: " + 0 + "]\n"
                    + "Rewinding.\n");
  }

  @Test
  public void testSCausesStopping() {
    initCond();

    handler.keyPressed(KeyEvent.VK_S);

    assertEquals(handler.toString(), "Stopping.\n");
  }

  @Test
  public void testMouseClickedOnKeyAddsNote() {
    initCond();
    gui.setCursorPosition(5);
    handler.mouseClicked(new Point(350, 536));

    assertEquals(handler.toString(),
            "addNote: " + "[pitch: " + 5 + "] \n"
                    + "[beat: " + 5 + "] \n"
                    + "[old note quantity: " + 0 + "] \n"
                    + "[new note quantity: " + 1 + "]\n");
  }

  @Test
  public void testMouseClickedOffKeyAddsNoNotes() {
    initCond();

    handler.mouseClicked(new Point(0, 536));

    assertEquals(handler.toString(), "no key clicked.\n"
            + "[old note quantity: " + 1 + "] \n"
            + "[new note quantity: " + 1 + "]\n");
  }
}