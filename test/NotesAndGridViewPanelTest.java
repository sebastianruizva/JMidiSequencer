
import org.junit.Test;

import cs3500.music.view.visual.NotesAndGridViewPanel;

/**
 * Tests for NotesAndGridViewPanel.
 */

public class NotesAndGridViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    NotesAndGridViewPanel fail = new NotesAndGridViewPanel(null);
  }
}
