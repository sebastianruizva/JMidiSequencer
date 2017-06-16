package cs3500.music.util;

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
}
