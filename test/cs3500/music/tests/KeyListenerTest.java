package cs3500.music.tests;

import org.junit.Test;

import cs3500.music.view.KeyListener;

import static org.junit.Assert.*;

/**
 * Tests for KeyListener
 */
public class KeyListenerTest {
@Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
  KeyListener fail = new KeyListener(null);
}
}