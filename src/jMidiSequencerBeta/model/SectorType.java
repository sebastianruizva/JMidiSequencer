package jMidiSequencerBeta.model;

/**
 * The enum {@SectorType} Represents a type of sector in an specific location of time and pitch.
 */
public enum SectorType {

  HEAD, BODY, REST;

  /**
   * Returns a different string depending on the sector type.
   * HEAD = X.
   * BODY = |.
   * REST = ' '.
   */
  @Override
  public String toString() {

    switch (this) {
      case HEAD:
        return "X";
      case BODY:
        return "|";
      default:
        return " ";
    }
  }

}