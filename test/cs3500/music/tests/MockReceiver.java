package cs3500.music.tests;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;


public class MockReceiver implements Receiver {
  public StringBuilder capture;

  public MockReceiver(StringBuilder capture) {
    this.capture = capture;
  }

  @Override
  public void send(MidiMessage message, long timeStamp) {
    ShortMessage msg = (ShortMessage) message;
    capture.append("note ");
    capture.append(msg.getCommand());
    capture.append(" ");
    capture.append(msg.getChannel());
    capture.append(" ");
    capture.append(msg.getData1());
    capture.append(" ");
    capture.append(msg.getData2());
    capture.append("\n");
  }


  @Override
  public void close() {

  }

}
