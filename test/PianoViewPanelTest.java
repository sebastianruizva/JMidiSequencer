import org.junit.Test;

import cs3500.music.view.NotesAndGridViewPanel;
import cs3500.music.view.PianoViewPanel;

import static org.junit.Assert.*;

/**
 * Tests for PianoViewPanel
 */
public class PianoViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PianoViewPanel fail = new PianoViewPanel(null);
  }
}