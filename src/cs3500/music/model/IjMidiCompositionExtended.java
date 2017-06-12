package cs3500.music.model;

/**
 * The Interface {@IjMidiComosition} Represents an interface of a collection of MIDI Tracks that can
 * be played in sync.
 */
public interface IjMidiCompositionExtended {
  
  /**
   * Adds a time signature change in the specified beat.
   * @param beat  the beat where the change is made
   * @param upper indicates the note value that represents one beat (the beat unit).
   * @param lower indicates how many such beats there are grouped together in a bar.
   */
  void addTimeSignatureChange(int beat, int upper, int lower) throws IllegalArgumentException;
  
  /**
   * Removes the time signature change in the specified beat.
   * @param beat the beat where the change is made
   */
  void removeTimeSignatureChange(int beat) throws IllegalArgumentException;
  
  /**
   * Adds a tempo change in the specified beat.
   * @param beat  the beat where the change is made
   * @param tempo the tempo that is going to be applied.
   */
  void addTempoChange(int beat, int tempo) throws IllegalArgumentException;
  
  /**
   * Removes a tempo change in the specified beat.
   * @param beat the beat where the change is made
   */
  void removeTempoChange(int beat) throws IllegalArgumentException;
  
  /**
   * Adds a track with the specified name.
   * @param name  the name of the track
   * @param track the track you are going to add.
   */
  void addTrack(String name, JMidiTrack track) throws IllegalArgumentException;
  
  /**
   * Removes a track with the specified name.
   * @param name the name of the track
   */
  void removeTrack(String name) throws IllegalArgumentException;
  
}
