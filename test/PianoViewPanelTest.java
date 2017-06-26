
import org.junit.Test;

import cs3500.music.view.gui.PianoViewPanel;

/**
 * Tests for PianoViewPanel.
 */
public class PianoViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PianoViewPanel fail = new PianoViewPanel(null);
  }
}