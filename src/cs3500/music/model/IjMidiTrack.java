package cs3500.music.model;

import java.util.List;

/**
 * The interface {@JMidiTrack} Represents an interface for a collection of related Midi Events
 * organized in a grid.
 */
public interface IjMidiTrack {
  
  /**
   * Adds a midi event to the region.
   * @param event the event you want to add
   * @throws IllegalArgumentException if there it collides with other in the region
   */
  void addEvent(JMidiEvent event) throws IllegalArgumentException;
  
  /**
   * Removes al the occurrences of a MIDI event in a midi track.
   * @param event The event you want to remove.
   * @throws IllegalArgumentException if there is no such event
   */
  void removeEvent(JMidiEvent event) throws IllegalArgumentException;
  
  /**
   * Moves a MIDI event in a midi track.
   * @param event    the event you want to update
   * @param newPitch the pitch you want to update to
   * @param newTick  the tick you want to update to
   * @throws IllegalArgumentException if it collides with other in the region or there is no such
   *                                  event.
   */
  void moveEvent(JMidiEvent event, int newPitch, int newTick) throws IllegalArgumentException;
  
  /**
   * Updates the event distance.
   * @param event       the event you want to update
   * @param newDistance the new distance for the event
   * @throws IllegalArgumentException if it collides with other in the region or there is no such
   *                                  event.
   */
  void resizeEvent(JMidiEvent event, int newDistance) throws IllegalArgumentException;
  
  
  /**
   * Returns a MIDI events on a given point in time and pitch.
   * @param tick  the tick where is supposed to be located at
   * @param pitch the pitch where is supposed to be located at
   * @throws IllegalArgumentException if there is no such event
   */
  JMidiEvent getEventOnPosition(int tick, int pitch) throws IllegalArgumentException;
  
  /**
   * Returns all the different MIDI events on a given point in time.
   * @param tick the tick where the events are
   */
  List<JMidiEvent> getEventsOnTick(int tick);
  
}
