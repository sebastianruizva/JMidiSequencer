package cs3500.music.tests;

import org.junit.Test;

/**
 * Tests for the MusicEditorGui.
 */
public class MusicEditorGUITest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullCompositionCausesError() {
    //MusicEditorGUI fail = new MusicEditorGUI();
    //fail.initialize(null, new StringBuffer());
  }
}