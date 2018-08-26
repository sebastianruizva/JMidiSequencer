
import org.junit.Test;

import jMidiSequencerBeta.model.JMidiComposition;
import jMidiSequencerBeta.view.AudioView;
import jMidiSequencerBeta.view.ConsoleView;
import jMidiSequencerBeta.view.ICompositionView;
import jMidiSequencerBeta.view.ViewManager;
import jMidiSequencerBeta.view.gui.GuiView;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the class {@ViewSelector}.
 */
public class ViewManagerTests {


  @Test
  public void testViewSelector_GetMidiViewImpl() throws Exception {
  
    ICompositionView view = new ViewManager()
            .select("midi", JMidiComposition.builder().build(), new StringBuilder());
  
    assertEquals(true, view instanceof AudioView);

  }

  @Test
  public void testViewSelector_GetGuiView() throws Exception {
  
    ICompositionView view = new ViewManager()
            .select("gui", JMidiComposition.builder().build(), new StringBuilder());
  
  
    assertEquals(true, view instanceof GuiView);

  }

  @Test
  public void testViewSelector_GetConsoleView() throws Exception {
  
    ICompositionView view = new ViewManager()
            .select("console", JMidiComposition.builder().build(), new StringBuilder());
  
  
    assertEquals(true, view instanceof ConsoleView);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewSelector_Invalid_View() throws Exception {
  
    ICompositionView view = new ViewManager()
            .select("sdasd", JMidiComposition.builder().build(), new StringBuilder());
  
  }

}