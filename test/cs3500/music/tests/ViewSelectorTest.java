package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.view.ConsoleView;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.ViewSelector;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the class {@ViewSelector}.
 */
public class ViewSelectorTest {


  @Test
  public void testViewSelector_GetMidiViewImpl() throws Exception {

    ICompositionView view = new ViewSelector().select("midi");

    assertEquals(true, view instanceof MidiViewImpl);

  }

  @Test
  public void testViewSelector_GetGuiView() throws Exception {

    ICompositionView view = new ViewSelector().select("visual");

    assertEquals(true, view instanceof MusicEditorGUI);

  }

  @Test
  public void testViewSelector_GetConsoleView() throws Exception {

    ICompositionView view = new ViewSelector().select("console");

    assertEquals(true, view instanceof ConsoleView);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewSelector_Invalid_View() throws Exception {

    ICompositionView view = new ViewSelector().select("sdasd");

  }

}