package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@JMidiComosition} Represents a MIDI Track that can be played with the assigned
 * parameters.
 */
public class JMidiComposition implements IjMidiComposition {
  
  /**
   * The collection of tracks int he composition by number
   */
  private HashMap<Integer, JMidiTrack> tracks;
  
  /**
   * Tempo represents the BPM measurement of the composition.
   */
  private int tempo;
  
  /**
   * The maximum pitch in the composition
   */
  int maxPitch;
  
  /**
   * the maximum tick in the composition
   */
  int maxTick;
  
  /**
   * the grid of the composition
   */
  HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  
  /**
   * the minimum pitch
   */
  private int minPitch;
  
  /**
   * Constructs a {@JMidiComposition}.
   */
  private JMidiComposition(HashMap<Integer, JMidiTrack> tracks, int tempo) {
    this.tracks = tracks;
    this.tempo = tempo;
    this.maxPitch = 0;
    this.minPitch = 0;
    this.maxTick = 0;
    this.grid = new HashMap<>();
    this.updateMaxValues();
    this.updateGrid();
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
  public static final class Builder implements CompositionBuilder<JMidiComposition> {
    
    /**
     * Tracks represents a collection of JMidiTracks
     */
    private HashMap<Integer, JMidiTrack> tracks;
    
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
      this.tempo = 200000;
      
    }
    
    /**
     * sets the instrument of the jMidiTrack {@JMidiComposition.Bulider}.
     */
    public Builder setInstrument(int track, JVirtualInstrument instrument)
            throws IllegalArgumentException {
      
      if (instrument == null) {
        throw new IllegalArgumentException("instrument cant be null");
      }
      
      //if no track create a track with the instrument
      if (!this.tracks.keySet().contains(track)) {
        this.tracks.put(track, new JMidiTrack(JMidiUtils.DEFAULT_VI()));
      }
      
      tracks.get(track).setInstrument(instrument);
      
      return this;
      
    }
    
    @Override public Builder setTempo(int tempo) throws IllegalArgumentException {
      if (tempo < 1) {
        throw new IllegalArgumentException("tempo can only be a positive value");
      }
      
      this.tempo = tempo;
      
      return this;
    }
    
    
    @Override
    public Builder addNote(int start, int end, int instrument, int pitch, int volume)
            throws IllegalArgumentException {
      
      if(instrument < 0) {
        
        throw new IllegalArgumentException("Instrument cant be negative!");
      }
      
      
      //create the note
      JMidiEvent note = JMidiEvent.builder().tick(start).duration(end + 1 - start).channel
              (instrument)
              .pitch(pitch).velocity(volume).build();
      
      //assign a track
      JMidiTrack track = this.tracks.getOrDefault(instrument, null);
      
      //if not existent create one
      if (track == null) {
        tracks.put(instrument, new JMidiTrack(JMidiUtils.DEFAULT_VI()));
        track = this.tracks.get(instrument);
      }
      
      //add to track
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
   * Returns a clone of the grid of the composition
   */
  public HashMap<Integer, HashMap<Integer, JMidiEvent>> getGrid() {
    
    return (HashMap<Integer, HashMap<Integer, JMidiEvent>>) grid.clone();
    
  }
  
  /**
   * Returns a clone of the tracks in the composition
   */
  public HashMap<Integer, JMidiTrack> getTracks() {
    
    return (HashMap<Integer, JMidiTrack>) tracks.clone();
    
  }
  
  /**
   * Returns a the tempo of the composition
   */
  public int getTempo() {
    
    return tempo;
    
  }
  
  /**
   * Returns the maximum tick in the track
   */
  public int getMaxTick() {
    
    return this.maxTick;
  }
  
  /**
   * Returns the minimum pitch in the track
   */
  public int getMinPitch() {
    
    return this.minPitch;
  }
  
  /**
   * Returns a the maximum pitch in the track
   */
  public int getMaxPitch() {
    
    return this.maxPitch;
  }
  
  /**
   * Updates the maxPitch and maxTick of the grid.
   */
  private void updateMaxValues() {
    
    for (Integer k : tracks.keySet()) {
      
      if (tracks.get(k).getMaxTick() > maxTick) {
        this.maxTick = tracks.get(k).getMaxTick();
      }
      
      if (tracks.get(k).getMaxPitch() > maxPitch) {
        this.maxPitch = tracks.get(k).getMaxPitch();
      }
      
    }
  
    List<Integer> pitches = new ArrayList<>();
  
    //find all pitches
    for (Integer i : grid.keySet()) {
      pitches.addAll(grid.get(i).keySet());
    }
  
    //determine smallest pitch
    if (pitches.size() == 0) {
      this.minPitch = 0;
    } else {
      this.minPitch = Collections.min(pitches);
    }
  
  }
  
  /**
   * Determines if there is enough free space in the grid on the specified location and distance.
   * @param tick     the tick you ar looking for
   * @param pitch    the pitch you want to verify
   * @param distance how big is the area?
   */
  private boolean available(int tick, int pitch, int distance) throws IllegalArgumentException {
    
    //invalid negative numbers
    if (tick < 0 || pitch < 0 || distance < 0) {
      throw new IllegalArgumentException("negative values not supported!");
    }
    
    for (int i = 0; i < distance; i++) {
      
      if (grid.getOrDefault(tick + i, null) != null) {
        if (grid.get(tick + i).getOrDefault(pitch, null) != null) {
          return false;
        }
      }
      
    }
    
    return true;
    
  }
  
  
  /**
   * Returns a MIDI events on a given point in time and pitch.
   * @param tick  the tick where is supposed to be located at
   * @param pitch the pitch where is supposed to be located at
   * @throws IllegalArgumentException if there is no such event
   */
  private JMidiEvent getEventOnPosition(int tick, int pitch) throws IllegalArgumentException {
    
    //verify if there is something there
    if (this.available(tick, pitch, 1)) {
      throw new IllegalArgumentException("no event in specified position");
    }
    
    return grid.get(tick).get(pitch);
    
  }
  
  /**
   * Returns a clone of all the different MIDI events on a given point in time.
   * @param tick the tick where the events are
   */
  public ArrayList<JMidiEvent> getEventsOnTick(int tick) {
    
    ArrayList<JMidiEvent> events = new ArrayList<JMidiEvent>();
    
    if (grid.getOrDefault(tick, null) != null) {
      
      for (Integer pitch : grid.get(tick).keySet()) {
        
        if (grid.get(tick).getOrDefault(pitch, null) != null) {
          
          events.add(grid.get(tick).get(pitch));
          
        }
        
      }
      
    }
    
    return (ArrayList<JMidiEvent>) events.clone();
    
  }
  
  /**
   * Returns the type of sector in an specific position of the grid.
   * @param tick the tick where the sector is
   * @param pitch the pitch where the sector is
   */
  public SectorType getSectorType(int tick, int pitch) {
    
    //if there is nothing
    if (this.available(tick, pitch, 1)) {
      return SectorType.REST;
    }
    
    JMidiEvent event = getEventOnPosition(tick, pitch);
    
    //if the events origin is the same then is a head
    if (tick == event.getTick()) {
      return SectorType.HEAD;
    }
    
    return SectorType.BODY;
    
  }
  
  /**
   * updates the grid of the composition
   */
  private void updateGrid() {
    
    HashMap<Integer, HashMap<Integer, JMidiEvent>> grid = new HashMap<>();
    
    for (Integer k : tracks.keySet()) {
      
      grid.putAll(tracks.get(k).getGrid());
      
    }
    
    this.grid = grid;
    
    //update min and max values
    this.updateMaxValues();
    
  }
  
  /**
   * Draws a string grid with the given dimensions.
   */
  private StringBuilder buildStringGrid(int width, int height) {
    
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
        String note = JMidiUtils.DEFAULT_VI().getNoteRepresentation((i - 4) / 5);
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
    
    //determine the dimensions of the grid
    int width = (this.maxPitch + 2) * 5;
    int height = this.maxTick + 2;
    
    
    //build a grid according to its current state
    StringBuilder grid = buildStringGrid(width, height);
    
    //Place the elements in the grid
    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {
        int index = tick * (width + 1) + (pitch * 5) + 6 + width;
        grid.setCharAt(index + 2, getSectorType(tick, pitch).toString().charAt(0));
      }
    }
    
    return grid.toString();
  }
  
}
