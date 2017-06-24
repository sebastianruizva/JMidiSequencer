
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

  @Override
  public void send(MidiMessage message, long timeStamp) {

    ShortMessage event = (ShortMessage) message;

    log.append("msg[Tck:" + timeStamp + ", Cmd:" + event.getCommand() + " Chn:" + event
            .getChannel() + " Ptc:"
            + event.getData1() + " Vel:" + event.getData2() + "] \n");

  }


  @Override
  public void close() {
    // Should do nothing.
  }

}