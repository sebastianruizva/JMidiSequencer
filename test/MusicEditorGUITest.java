
import org.junit.Test;

import cs3500.music.model.JMidiComposition;
import cs3500.music.view.gui.GuiView;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the MusicEditorGui.
 */
public class MusicEditorGUITest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullCompositionCausesError() {
    GuiView fail = new GuiView(null, new StringBuffer());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendableCausesError() {
    JMidiComposition comp = JMidiComposition.builder().build();
  
    GuiView fail = new GuiView(comp, null);
  }

  @Test
  public void testSetCursorPosition() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    GuiView gui = new GuiView(comp, new StringBuffer());
    gui.setCursorPosition(1);

    assertEquals(gui.getCursorPosition(), 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCursorPositionTooLow() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    GuiView gui = new GuiView(comp, new StringBuffer());
    gui.setCursorPosition(11);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCursorPositionTooHigh() {
    JMidiComposition comp = JMidiComposition.builder()
            .addNote(0, 10, 1, 60, 60).build();
    GuiView gui = new GuiView(comp, new StringBuffer());
    gui.setCursorPosition(-11);

  }
}