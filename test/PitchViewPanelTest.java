import org.junit.Test;

import cs3500.music.view.PianoViewPanel;
import cs3500.music.view.PitchViewPanel;

import static org.junit.Assert.*;

/**
 * Tests for PitchViewPanel
 */
public class PitchViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PitchViewPanel fail = new PitchViewPanel(null);
  }
}