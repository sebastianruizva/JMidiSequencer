
import org.junit.Test;

import jMidiSequencerBeta.view.gui.PitchViewPanel;

/**
 * Tests for PitchViewPanel.
 */
public class PitchViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PitchViewPanel fail = new PitchViewPanel(null);
  }
}