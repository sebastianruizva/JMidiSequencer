import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

/**
 * A Mock Sequencer class, represents a mock sequencer.
 */
public class MockSequencer implements Sequencer {
  
  StringBuilder log;
  private Sequence sequence;
  private long tick;
  
  /**
   * Constructs a {@TestSequencer}.
   * @param log the log where the messages get recorded
   */
  MockSequencer(StringBuilder log) {
    tick = 0;
    this.log = log;
    log.append("sequencer initialized \n");
    try {
      this.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
  
  
  @Override public Sequence getSequence() {
    try {
      return new MockSequence(Sequence.PPQ, 24, log);
    } catch (InvalidMidiDataException e) {
      throw new UnsupportedOperationException();
    }
  }
  
  @Override public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    
    this.sequence = sequence;
    
  }
  
  @Override public void setSequence(InputStream stream)
          throws IOException, InvalidMidiDataException {
    //required but not needed.
  }
  
  @Override public void start() {
  
    log.append("sequencer started \n");
  
  }
  
  @Override public void stop() {
  
    log.append("sequencer stopped \n");
  
  }
  
  @Override public boolean isRunning() {
    return false;
  }
  
  @Override public void startRecording() {
    //required but not needed.
  }
  
  @Override public void stopRecording() {
    //required but not needed.
  }
  
  @Override public boolean isRecording() {
    return false;
  }
  
  @Override public void recordEnable(Track track, int channel) {
    //required but not needed.
  }
  
  @Override public void recordDisable(Track track) {
    //required but not needed.
  }
  
  @Override public float getTempoInBPM() {
    return 0;
  }
  
  @Override public void setTempoInBPM(float bpm) {
    //required but not needed.
  }
  
  @Override public float getTempoInMPQ() {
    return 0;
  }
  
  @Override public void setTempoInMPQ(float mpq) {
    //required but not needed.
  }
  
  @Override public float getTempoFactor() {
    return 0;
  }
  
  @Override public void setTempoFactor(float factor) {
    //required but not needed.
  }
  
  @Override public long getTickLength() {
    return 0;
  }
  
  @Override public long getTickPosition() {
    return this.tick;
  }
  
  @Override public void setTickPosition(long tick) {
    System.out.println(tick);
  
    this.tick = 34;
  
  }
  
  @Override public long getMicrosecondLength() {
    return 0;
  }
  
  @Override public Info getDeviceInfo() {
    return null;
  }
  
  @Override public void open() throws MidiUnavailableException {
    //required but not needed.
  }
  
  @Override public void close() {
    //required but not needed.
  }
  
  @Override public boolean isOpen() {
    return false;
  }
  
  @Override public long getMicrosecondPosition() {
    return 0;
  }
  
  @Override public void setMicrosecondPosition(long microseconds) {
    //required but not needed.
  }
  
  @Override public int getMaxReceivers() {
    return 0;
  }
  
  @Override public int getMaxTransmitters() {
    return 0;
  }
  
  @Override public Receiver getReceiver() throws MidiUnavailableException {
    MockReceiver receiver = new MockReceiver(log);
    return receiver;
  }
  
  @Override public List<Receiver> getReceivers() {
    return null;
  }
  
  @Override public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }
  
  @Override public List<Transmitter> getTransmitters() {
    return null;
  }
  
  @Override public SyncMode getMasterSyncMode() {
    return null;
  }
  
  @Override public void setMasterSyncMode(SyncMode sync) {
    //required but not needed.
  }
  
  @Override public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }
  
  @Override public SyncMode getSlaveSyncMode() {
    return null;
  }
  
  @Override public void setSlaveSyncMode(SyncMode sync) {
    //required but not needed.
  }
  
  @Override public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }
  
  @Override public void setTrackMute(int track, boolean mute) {
    //required but not needed.
  }
  
  @Override public boolean getTrackMute(int track) {
    return false;
  }
  
  @Override public void setTrackSolo(int track, boolean solo) {
    //required but not needed.
  }
  
  @Override public boolean getTrackSolo(int track) {
    return false;
  }
  
  @Override public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }
  
  @Override public void removeMetaEventListener(MetaEventListener listener) {
    //required but not needed.
  }
  
  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }
  
  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }
  
  @Override public long getLoopStartPoint() {
    return 0;
  }
  
  @Override public void setLoopStartPoint(long tick) {
    //required but not needed.
  }
  
  @Override public long getLoopEndPoint() {
    return 0;
  }
  
  @Override public void setLoopEndPoint(long tick) {
    //required but not needed.
  }
  
  @Override public int getLoopCount() {
    return 0;
  }
  
  @Override public void setLoopCount(int count) {
    //required but not needed.
  }
}
