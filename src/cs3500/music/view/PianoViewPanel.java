package cs3500.music.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.JVirtualInstrument;

/**
 * Created by joe on 6/13/17.
 */
public class PianoViewPanel extends JLayeredPane {
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private HashMap<String, PianoKey> keys;

  public PianoViewPanel(JMidiTrack track) {
    this.track = track;
    initPiano();
  }

  private void initPiano() {
    // HashMap<String, PianoKey> keys = new HashMap<>();
    JVirtualInstrument inst = track.getInstrument();
    ArrayList<PianoKey> keys = new ArrayList<>();


    for (int i = 0; i < 87; i++) {
      String currNote = inst.getNoteRepresentation(i);
    //  System.out.println(currNote + " " + isNatural(currNote));
      PianoKey temp;

      if (isNatural(currNote)) {
        temp = new PianoKey(PianoKey.KeyType.WHITE);
        temp.setLocation(i * 10 + (i * 10 % 20), 0);
        add(temp, temp.getDepth(), -1);

      } else {
        System.out.println(currNote + " " + isNatural(currNote) + " " + i+ " " +i * 10);
        temp = new PianoKey(PianoKey.KeyType.BLACK);
        temp.setLocation(i * 10 - (i * 10 % 20), 0);
        add(temp, temp.getDepth(), -1);

      }
    }
  }

  private boolean isNatural(String name) {
    return !name.contains("#");
  }
}
