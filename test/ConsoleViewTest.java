
import org.junit.Test;

import cs3500.music.model.JMidiComposition;
import cs3500.music.view.ConsoleView;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for ConsoleView.
 */
public class ConsoleViewTest {
  @Test(expected = IllegalArgumentException.class)
  public void errorWhenCompositionIsNull() {
    ConsoleView fail = new ConsoleView(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void errorWhenAppendableiIsNull() {
    ConsoleView fail = new ConsoleView(JMidiComposition.builder().build(), null);
  }
  
  @Test public void testViewInitialization() {
    Appendable appendable = new StringBuilder();
    ConsoleView view =
            new ConsoleView(JMidiComposition.builder().addNote(1, 1, 1, 1, 1).build(), appendable);
    view.initialize();
    assertEquals(
            "Preparing console view\n" + "Console view ready!\n" + "      C#0 \n" + "    1     \n"
                    + "    2  X  \n" + "Console view initialized\n", appendable);
  }
}