package cs3500.music.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Interface {@IjMidiComosition} Represents an interface of a collection of MIDI Tracks that can
 * be played in sync.
 */
public interface IjMidiComposition {

  /**
   * Returns a clone of the grid of the composition.
   */
  HashMap<Integer, HashMap<Integer, JMidiEvent>> getGrid();

  /**
   * Returns a clone of the tracks in the composition.
   */
  HashMap<Integer, JMidiTrack> getTracks();

  /**
   * Returns a the tempo of the composition.
   */
  int getTempo();

  /**
   * Returns the maximum tick in the track.
   */
  int getMaxTick();

  /**
   * Returns a the maximum pitch in the track.
   */
  int getMaxPitch();
  
  /**
   * Increases the tempo by 10000 micro seconds.
   */
  void incTempo();
  
  /**
   * Decreases the tempo by 10000 micro seconds.
   */
  void decTempo();
  
  /**
   * Returns a list of elements in the designated tick
   * @param tick the tick where the events are.
   */
  ArrayList<JMidiEvent> getEventsOnTick(int tick);

  /**
   * Returns the type of sector in an specific position of the grid.
   *
   * @param tick  the tick where the sector is.
   * @param pitch the pitch where the sector is.
   */
  SectorType getSectorType(int tick, int pitch);


}
