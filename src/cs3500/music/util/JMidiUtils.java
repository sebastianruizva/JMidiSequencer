package cs3500.music.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.JVirtualInstrument;

/**
 * The class {@JMidiUtils} represents a set of tools for the JMidi environment.
 */
public class JMidiUtils {
  
  /**
   * the default scale
   */
  public static final ArrayList<String> DEFAULT_SCALE = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * the default Virtual Instrument.
   */
  public static JVirtualInstrument DEFAULT_VI() {
    try {
      return new JVirtualInstrument(DEFAULT_SCALE, MidiSystem.getSynthesizer());
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * Appends a message to the appendable
   * @param s the string that is going to be appended.
   * @param ap the appendable where the message is heading
   */
  public static void message(String s, Appendable ap) {
    try {
      
      ap.append(s + "\n");
      
      return;
    } catch (IOException e) {
      //not needed
    }
  }
}
