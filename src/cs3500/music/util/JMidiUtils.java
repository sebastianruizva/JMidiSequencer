package cs3500.music.util;

import java.util.ArrayList;
import java.util.Arrays;

import cs3500.music.model.JVirtualInstrument;

/**
 * Created by sebastian on 6/15/17.
 */
public class JMidiUtils {
  
  /**
   * the default scale
   */
  public static ArrayList<String> DEFAULT_SCALE = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  /**
   * the default Virtual Instrument.
   */
  public static JVirtualInstrument DEFAULT_VI = new JVirtualInstrument(DEFAULT_SCALE);
  
}
