import org.junit.Test;

import cs3500.music.view.ConsoleView;

/**
 * Tests for ConsoleView.
 */
public class ConsoleViewTest {
  @Test(expected = IllegalArgumentException.class)
  public void errorWhenCompositionIsNull() {
    ConsoleView fail = new ConsoleView();
    fail.initialize(null, new StringBuffer());
  }
}