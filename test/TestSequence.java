import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/**
 * Created by sebastian on 6/23/17.
 */
public class TestSequence extends Sequence {
  StringBuilder log;
  
  public TestSequence(float divisionType, int resolution, StringBuilder log)
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
