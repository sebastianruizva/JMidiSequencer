package cs3500.music.model;

import java.util.ArrayList;

/**
 * The interface {@JMidiTrack} Represents an interface for a collection of related Midi Events
 * organized in a grid.
 */
public interface IjMidiTrack {

  /**
   * Returns a the maximum pitch in the track.
   */
  int getMaxPitch();

  /**
   * Returns a the maximum tick in the track.
   */
  int getMaxTick();
  
  /**
   * Returns a clone of all the different MIDI events on a given point in time.
   *
   * @param tick the tick where the events are.
   */
  ArrayList<JMidiEvent> getEventsOnTick(int tick);
  
}
