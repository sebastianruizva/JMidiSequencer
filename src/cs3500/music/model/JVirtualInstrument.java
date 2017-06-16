package cs3500.music.model;

import java.util.ArrayList;

import javax.sound.midi.Synthesizer;

/**
 * The class {@JVirtualInstrument} Represents a midi based musical instrument.
 */
public class JVirtualInstrument implements IjVirtualInstrument {
  
  private ArrayList<String> scale;
  
  private Synthesizer synth;
  
  /**
   * Constructs a {@JVirtualInstrument}
   * @param scale A list of the 'notes' playable by the virtual instrument from low to high.
   */
  public JVirtualInstrument(ArrayList<String> scale, Synthesizer synth) {
    if (scale == null) {
      throw new IllegalArgumentException("scale cant be null!");
    }
    this.scale = scale;
    this.synth = synth;
    
  }
  
  /**
   * Return the size of an octave in this virtual instrument.
   */
  @Override public int getOctaveDegree() {
    
    return scale.size();
    
  }
  
  /**
   * Returns a string representing all the notes supported by the Virtual instrument.
   */
  @Override public String getScale() {
    
    return scale.toString();
    
  }
  
  /**
   * @param pitch returns a string representation of the appropriate note depending on the specified
   *              pitch.
   * @throws IllegalArgumentException if is a negative value
   */
  @Override public String getNoteRepresentation(int pitch) throws IllegalArgumentException {
    if (pitch < 0) {
      throw new IllegalArgumentException("cant support negative values!");
    }
    return scale.get(pitch % scale.size());
  }
  
  /**
   * Returns a copy of the instrument's synthesizer.
   */
  @Override public Synthesizer getSynth() {
    return synth;
  }
}
