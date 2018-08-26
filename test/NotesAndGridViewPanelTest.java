
import org.junit.Test;

import jMidiSequencerBeta.view.gui.NotesAndGridViewPanel;

/**
 * Tests for NotesAndGridViewPanel.
 */

public class NotesAndGridViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    NotesAndGridViewPanel fail = new NotesAndGridViewPanel(null);
  }
}
