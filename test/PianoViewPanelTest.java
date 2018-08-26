
import org.junit.Test;

import jMidiSequencerBeta.view.gui.PianoViewPanel;

/**
 * Tests for PianoViewPanel.
 */
public class PianoViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PianoViewPanel fail = new PianoViewPanel(null);
  }
}