package cs3500.music.controller;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

/**
 * Created by sebastian on 6/23/17.
 */
public class meta implements MetaEventListener {
  @Override public void meta(MetaMessage meta) {
    System.out.println("s");
  }
}
