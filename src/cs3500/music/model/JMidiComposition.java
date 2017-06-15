package cs3500.music.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.JMidiUtils;

import static cs3500.music.util.JMidiUtils.DEFAULT_VI;

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
        tracks.put(instrument, new JMidiTrack(JMidiUtils.DEFAULT_VI));
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

  }
  
  /**
   * Adds a track with the specified name.
   * @param number  the name of the track
   * @param track the track you are going to add.
   */
  public void addTrack(int number, JMidiTrack track) {
    
    //check if values are valid
    if (track == null) {
      throw new IllegalArgumentException("values can not be null!");
    }
    
    //check if values are valid
    if (number < 0) {
      throw new IllegalArgumentException("values can not be negative!");
    }
    
    //check if there is nothing there already
    if (this.tracks.getOrDefault(number, null) != null) {
      throw new IllegalArgumentException("there is a track with the same name already!");
    }
    
    //add track
    this.tracks.put(number, track);
    
  }
  
  /**
   * Removes a track with the specified name.
   * @param number the name of the track
   */
  public void removeTrack(int number) {
    
    //check if values are valid
    if (number < 0) {
      throw new IllegalArgumentException("values can not be negative!");
    }
    
    //check if there is something there
    if (this.tracks.getOrDefault(number, null) == null) {
      throw new IllegalArgumentException("there is nothing there!");
    }
    
    //remove track
    this.tracks.remove(number);
    
  }
  
  /**
   * Draws a grid with the given dimensions.
   */
  private StringBuilder buildGrid(int width, int height) {
    
    int length = height * width;
    StringBuilder grid = new StringBuilder(length);
    int i = 0;
    int row = 0;
    
    while (i < length) {
      if (i >= width && i % (width) == 0) {
        grid.append('\n');
        row++;
      }
      if (i % (width) == 0 && i >= width) {
        grid.append(String.format("%5s", row).substring(0, 5));
        i += 5;
      } else if (i > 4 && width - 4 > i) {
        String note = DEFAULT_VI.getNoteRepresentation((i - 4) / 5);
        grid.append(String.format(" %2s  ", note).substring(0, 5));
        i += 5;
      } else {
        grid.append(" ");
        i++;
      }
    }
    
    return grid;
    
  }
  
  /**
   * Returns a string grid with all the element in the region.
   */
  @Override public String toString() {
    
    //if no tracks
    if (tracks.size() == 0) {
      return "";
    }
  
    int maxPitch = 0;
    int maxTick = 0;
  
    for (Integer k: tracks.keySet()) {
      
      if(tracks.get(k).getMaxTick() > maxTick) {
        maxTick = tracks.get(k).getMaxTick();
      }
  
      if(tracks.get(k).getMaxPitch() > maxPitch) {
        maxPitch = tracks.get(k).getMaxPitch();
      }
      
    }
    
    //determine the dimensions of the grid
    int width = (maxPitch + 2) * 5;
    int height = maxTick + 2;

    
    //build a grid according to its current state
    StringBuilder grid = buildGrid(width, height);
  
    for (Integer k: tracks.keySet()) {
      
      JMidiTrack t = tracks.get(k);
      
      //Place the elements in the grid
      for (Integer tick : t.getGrid().keySet()) {
        for (Integer pitch : t.getGrid().get(tick).keySet()) {
          int index = tick * (width + 1) + (pitch * 5) + 6 + width;
          grid.setCharAt(index + 2, t.getSectorType(tick, pitch).toString().charAt(0));
        }
      }
      
    }
    
    return grid.toString();
  }
  
  
  
  
}
