package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cs3500.music.util.CompositionBuilder;

/**
 * The class {@JMidiComosition} Represents a MIDI Track that can be played with the assigned
 * parameters.
 */
public class JMidiComposition {
  
  public HashMap<Integer, JMidiTrack> getTracks() {
    return tracks;
  }
  
  /**
   * The track where the composition is
   */
  private HashMap<Integer,JMidiTrack> tracks;
  
  public int getTempo() {
    return tempo;
  }
  
  /**
   * Tempo represents the BPM measurement of the composition.
   */
  private int tempo;
  
  
  /**
   * Constructs a {@JMidiComposition}.
   */
  private JMidiComposition(HashMap<Integer,JMidiTrack> tracks, int tempo) {
    this.tracks = tracks;
    this.tempo = tempo;
  }

  /**
   * starts a builder for its construction.
   */
  public static Builder builder() {
    return new Builder();
  }
  
  /**
   * the class {@Builder} Builds a cs3500.music.model.JMidiComposition according to the default
   * values or the ones directed by the user.
   */
  public static final class Builder implements CompositionBuilder{
  
    /**
     * Tracks represents a collection of JMidiTracks
     */
    private HashMap<Integer,JMidiTrack> tracks;
  
    public Integer getTempo() {
      return tempo;
    }
  
    /**
     * Tempo represents the BPM measurement of the composition.
     */
    private Integer tempo;
    
    /**
     * the default scale
     */
    private ArrayList<String> defaultScale = new ArrayList<>(
            Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
    /**
     * the default Virtual Instrument.
     */
    JVirtualInstrument defaultJVirtualInstrument = new JVirtualInstrument(defaultScale);
  
    /**
     * Constructs a {@JMidiComposition.Bulider}.
     */
    public Builder() {
  
      //Define the default values
      this.tracks = new HashMap<Integer, JMidiTrack>();
      this.tempo = 120;
      
    }
  
    /**
     * sets the instrument of the jMidiTrack {@JMidiComposition.Bulider}.
     */
    public Builder setInstrument(int track, JVirtualInstrument instrument) throws
            IllegalArgumentException {
      
      if (instrument == null) {
        throw new IllegalArgumentException("instrument cant be null");
      }
  
      if (!this.tracks.keySet().contains(track)) {
        throw new IllegalArgumentException("tack not existent");
      }
  
      tracks.get(track).setInstrument(instrument);
      
      return this;
      
    }
  
    @Override public CompositionBuilder setTempo(int tempo) throws IllegalArgumentException{
      if(tempo < 1) {
        throw new IllegalArgumentException("tempo can only be a positive value");
      }
      
      this.tempo = tempo;
      
      return this;
    }
  
    @Override
    public CompositionBuilder addNote(int start, int end, int instrument, int pitch, int volume)
            throws IllegalArgumentException{
      
      //create the note
      JMidiEvent note = JMidiEvent.builder().tick(start).duration(end - start).channel
              (instrument).pitch(pitch).velocity(volume).build();

      JMidiTrack track = this.tracks.getOrDefault(instrument, null);
      
      if(track == null) {
        tracks.put(instrument, new JMidiTrack(defaultJVirtualInstrument));
        track = this.tracks.get(instrument);
      }
      
      track.addEvent(note);
      
      return this;
    }
    
    /**
     * Returns the final composition.
     */
    public JMidiComposition build() {
      
      return new JMidiComposition(this.tracks, this.tempo);
      
    }
  
    @Override public String toString() {
      return tracks.getOrDefault(1,null).toString();
    }
  }
  
  
  
  
}
