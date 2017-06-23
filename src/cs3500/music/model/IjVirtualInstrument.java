package cs3500.music.model;

/**
 * The interface {@IjVirtualInstrument} Represents a midi based musical instrument interface for
 * a midi track.
 */
public interface IjVirtualInstrument {

  /**
   * Return the size of an octave in this virtual instrument.
   */
  int getOctaveDegree();

  /**
   * Returns a string representing all the notes supported by the Virtual instrument.
   */
  String getScale();

  /**
   * @param pitch returns a string representation of the appropriate note depending on the specified
   *              pitch.
   * @throws IllegalArgumentException if is a negative value.
   */
  String getNoteRepresentation(int pitch) throws IllegalArgumentException;

  /**
   * Returns its current instrument number.
   */
  int getNumber();

}
