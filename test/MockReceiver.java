
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;


public class MockReceiver implements Receiver {

  //the log where the messages are going to be recorded
  public StringBuilder log;

  /**
   * Constructs a {@TestReciever}.
   * @param log the log where the messages get recorded
   */
  public MockReceiver(StringBuilder log) {
    this.log = log;
  }

  @Override public void send(MidiMessage m, long timeStamp) {
  
    ShortMessage message = (ShortMessage) m;
  
    switch (message.getCommand()) {
      case ShortMessage.NOTE_OFF:
        log.append("note off " + message.getData1() + ", velocity: " + message.getData2() + "\n");
        break;
      case ShortMessage.NOTE_ON:
        log.append("note " + message.getData1() + " on velocity: " + message.getData2() + "\n");
        break;
    }

  }


  @Override
  public void close() {
    // Should do nothing.
  }

}
