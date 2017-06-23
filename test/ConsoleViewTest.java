
import org.junit.Test;

import cs3500.music.model.JMidiComposition;
import cs3500.music.view.ConsoleView;

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
}