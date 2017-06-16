package cs3500.music.model;

/**
 * The interface {@IjMidiEvent} Represents an interface for a midi event inside a MIDI track. This
 * events usually represent NOTES but sometimes will also represent other events in the
 * composition, such as not-NOTE related events like percussive instructions. For further
 * expansion once the keyboard view is finished, I decided to keep the technical term.
 */
public interface IjMidiEvent {
  
  /**
   * Retrieves the tick of the event.
   * - A 'Tick' the standard measure we will use to store note changes, this changes will then be
   * synchronized with the actual tempo of a composition. A tick is fixed to represent 120 BPM.
   */
  int getTick();
  
  /**
   * Retrieves the absolute pitch of the event. This pitch doe not take the octave ina count,
   * this will be handled by the assigned virtual instrument in the track.
   */
  int getPitch();
  
  /**
   * Retrieves the velocity of the event (how hard the key was pressed).
   */
  int getVelocity();
  
  /**
   * Retrieves the channel of the event (where the midi message is coming from or where is
   * supposed to head at).
   */
  int getChannel();
  
  /**
   * Retrieves the duration of the event (how long the key or trigger was held).
   */
  int getDuration();
  
}
