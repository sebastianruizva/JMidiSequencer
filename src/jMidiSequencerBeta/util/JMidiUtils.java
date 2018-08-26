package jMidiSequencerBeta.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import jMidiSequencerBeta.model.JVirtualInstrument;

/**
 * The class {@JMidiUtils} represents a set of tools for the JMidi environment.
 */
public class JMidiUtils {
  
  /**
   * The default scale.
   */
  public static final ArrayList<String> DEFAULT_SCALE = new ArrayList<>(
          Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
  
  /**
   * the default Virtual Instrument.
   */
  public static JVirtualInstrument defualtVI() {
    return new JVirtualInstrument(DEFAULT_SCALE, 0);
  }
  
  /**
   * Appends a message to the appendable
   * @param s  the string that is going to be appended.
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
  
  /**
   * Transalates a sequence to  areadable format
   * @param sequence the sequence that is going to be translated.
   */
  public static java.lang.String translateSequence(Sequence sequence) {
    StringBuilder summary = new StringBuilder();
    
    int trackNumber = 0;
    for (Track track : sequence.getTracks()) {
      trackNumber++;
      summary.append("Track #" + trackNumber + "\n");
      for (int i = 0; i < track.size(); i++) {
        
        try {
          ShortMessage event = (ShortMessage) track.get(i).getMessage();
          summary.append(
                  "msg[Tck:" + track.get(i).getTick() + ", Cmd:" + event.getCommand() + " Chn:"
                          + event.getChannel() + " Ptc:" + event.getData1() + " Vel:" + event
                          .getData2() + "] \n");
        } catch (ClassCastException e) {
          //not needed
        }
      }
    }
    
    return summary.toString();
  }
  
}
