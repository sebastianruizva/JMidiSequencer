package cs3500.music.tests;

import org.junit.Test;

/**
 * Tests for NotesAndGridViewPanel.
 */
public class NotesAndGridViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    NotesAndGridViewPanel fail = new NotesAndGridViewPanel(null, null);
  }
}