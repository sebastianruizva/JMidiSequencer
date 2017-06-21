package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiTrack;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.MusicEditorGUI;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the MusicEditorGui.
 */
public class MusicEditorGUITest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullCompositionCausesError() {
    MusicEditorGUI fail = new MusicEditorGUI(null, new StringBuffer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableCausesError() {
    JMidiComposition comp = JMidiComposition.builder().build();

    MusicEditorGUI fail = new MusicEditorGUI(comp, null);
  }

  @Test
  public void testSetCursorPosition() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    MusicEditorGUI gui = new MusicEditorGUI(comp, new StringBuffer());
    gui.setCursorPosition(1);

    assertEquals(gui.getCursorPosition(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCursorPositionTooLow() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    MusicEditorGUI gui = new MusicEditorGUI(comp, new StringBuffer());
    gui.setCursorPosition(11);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCursorPositionTooHigh() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    MusicEditorGUI gui = new MusicEditorGUI(comp, new StringBuffer());
    gui.setCursorPosition(-11);

  }
}