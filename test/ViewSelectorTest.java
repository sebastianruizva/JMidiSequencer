
import org.junit.Test;

/**
 * Tests for the class {@ViewSelector}.
 */
public class ViewSelectorTest {


  @Test
  public void testViewSelector_GetMidiViewImpl() throws Exception {
  
    //ICompositionView view = new ViewManager(jM).select("midi");
  
    // assertEquals(true, view instanceof AudioView);

  }

  @Test
  public void testViewSelector_GetGuiView() throws Exception {
  
    //ICompositionView view = new ViewManager().select("visual");
  
    //  assertEquals(true, view instanceof MusicEditorGUI);

  }

  @Test
  public void testViewSelector_GetConsoleView() throws Exception {
  
    //ICompositionView view = new ViewManager().select("console");
  
    // assertEquals(true, view instanceof ConsoleView);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testViewSelector_Invalid_View() throws Exception {
  
    //ICompositionView view = new ViewManager().select("sdasd");

  }

}