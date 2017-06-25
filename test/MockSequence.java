import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/**
 * A Mock sequence.
 */
public class MockSequence extends Sequence {
  StringBuilder log;
  
  /**
   * Constructs a mock sequence.
   */
  public MockSequence(float divisionType, int resolution, StringBuilder log)
          throws InvalidMidiDataException {
    super(divisionType, resolution);
    this.log = log;
    log.append("Sequence initiated \n");
  }
  
  @Override public Track createTrack() {
    log.append("Track created \n");
    return super.createTrack();
  }
  
}
