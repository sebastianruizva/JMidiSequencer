package cs3500.music.model;

import java.util.HashMap;

/**
 * The class {@JMidiComosition} Represents a collection of MIDI Tracks that can be played in sync.
 */
public class JMidiComposition implements IjMidiComposition {
  
  /**
   * Tracks represents a collection of JMidiEvents organized by track name.
   */
  private HashMap<String, JMidiTrack> tracks;
  
  /**
   * timeSignatureChanges represents a collection of time signatures representing the tick when
   * the specified time signature takes effect.
   */
  private HashMap<Integer, JTimeSignature> timeSignatureChanges;
  
  /**
   * tempoChanges represents a collection of BPM organized by the tick when the tempo change was
   * made.
   */
  private HashMap<Integer, Integer> tempoChanges;
  
  /**
   * represents how many beats (pulses) are to be contained in each bar and which note value is
   * to be given one beat.
   */
  private JTimeSignature timeSignature;
  
  /**
   * Tempo represents the BPM measurement of the composition.
   */
  private Integer tempo;
  
  /**
   * Constructs a {@JMidiComosition}.
   */
  public JMidiComposition() {
    this.tracks = new HashMap<>();
    this.timeSignatureChanges = new HashMap<>();
    this.tempoChanges = new HashMap<>();
    this.timeSignature = new JTimeSignature(4, 4);
    this.tempo = 120;
  }
  
  /**
   * Adds a time signature change in the specified beat.
   * @param beat  the beat where the change is made
   * @param upper indicates the note value that represents one beat (the beat unit).
   * @param lower indicates how many such beats there are grouped together in a bar.
   */
  @Override public void addTimeSignatureChange(int beat, int upper, int lower) throws IllegalArgumentException {
    
    //check if its a valid beat
    if (beat < 0) {
      throw new IllegalArgumentException("beats bust not be negative!");
    }
    
    //check if there is nothing there already
    if (this.timeSignatureChanges.getOrDefault(beat, null) != null) {
      throw new IllegalArgumentException("there is a time signature change there already!");
    }
    
    //add change
    this.timeSignatureChanges.put(beat, new JTimeSignature(upper, lower));
    
  }
  
  /**
   * Removes the time signature change in the specified beat.
   * @param beat the beat where the change is made
   */
  @Override public void removeTimeSignatureChange(int beat) {
    
    //check if its a valid beat
    if (beat < 0) {
      throw new IllegalArgumentException("beats bust not be negative!");
    }
    
    //check if there something there
    if (this.timeSignatureChanges.getOrDefault(beat, null) == null) {
      throw new IllegalArgumentException("there is not such change!");
    }
    
    //remove change
    this.timeSignatureChanges.remove(beat);
    
  }
  
  /**
   * Adds a tempo change in the specified beat.
   * @param beat  the beat where the change is made
   * @param tempo the tempo that is going to be applied.
   */
  @Override public void addTempoChange(int beat, int tempo) {
    
    //check if values are valid
    if (beat < 0 || tempo < 0) {
      throw new IllegalArgumentException("values can not be negative!");
    }
    
    //check if there is nothing there already
    if (this.tempoChanges.getOrDefault(beat, null) != null) {
      throw new IllegalArgumentException("there is a tempo Changes there already!");
    }
    
    //add change
    this.tempoChanges.put(beat, tempo);
    
  }
  
  /**
   * Removes a tempo change in the specified beat.
   * @param beat the beat where the change is made
   */
  @Override public void removeTempoChange(int beat) {
    
    //check if values are valid
    if (beat < 0) {
      throw new IllegalArgumentException("values can not be negative!");
    }
    
    //check if there is nothing there already
    if (this.tempoChanges.getOrDefault(beat, null) == null) {
      throw new IllegalArgumentException("there is a nothing there!");
    }
    
    //remove change
    this.tempoChanges.remove(beat);
    
  }
  
  /**
   * Adds a track with the specified name.
   * @param name  the name of the track
   * @param track the track you are going to add.
   */
  @Override public void addTrack(String name, JMidiTrack track) {
    
    //check if values are valid
    if (name == null || track == null) {
      throw new IllegalArgumentException("values can not be null!");
    }
    
    //check if there is nothing there already
    if (this.tracks.getOrDefault(name, null) != null) {
      throw new IllegalArgumentException("there is a track with the same name already!");
    }
    
    //add track
    this.tracks.put(name, track);
    
  }
  
  /**
   * Removes a track with the specified name.
   * @param name the name of the track
   */
  @Override public void removeTrack(String name) {
    
    //check if values are valid
    if (name == null) {
      throw new IllegalArgumentException("values can not be null!");
    }
    
    //check if there is something there
    if (this.tracks.getOrDefault(name, null) == null) {
      throw new IllegalArgumentException("there is nothing there!");
    }
    
    //remove track
    this.tracks.remove(name);
    
  }
  
  /**
   * Returns a string with key information of the composition.
   */
  @Override public String toString() {
    return "cs3500.music.model.JMidiComposition{" + "tracks=" + tracks + ", timeSignatureChanges"
            + "=" + timeSignatureChanges + ", tempoChanges=" + tempoChanges + ", timeSignature="
            + timeSignature + ", tempo=" + tempo + '}';
  }
  
}
