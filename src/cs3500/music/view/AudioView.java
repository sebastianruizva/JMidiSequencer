package cs3500.music.view;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.spi.MidiFileWriter;
import javax.sound.midi.spi.MidiFileWriter;

import cs3500.music.MusicEditor;
import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.util.JMidiUtils;

import javax.sound.midi.*; // package for all midi classes
/**
 * The class {@MidiViewImpl} implements a MIDI playback view of a composition.
 */
public class AudioView implements ICompositionView {
  
  private JMidiComposition composition;
  private Sequence sequence;
  private Sequencer sequencer;
  private Appendable ap;
  
  /**
   * Plays the directed composition.
   * @param composition the composition you want to play.
   */
  public void initialize(JMidiComposition composition, Appendable ap) {
    
    JMidiUtils.message("Initializing Audio View", ap);
  
    //set the composition
    this.composition = composition;
    
    this.ap = ap;
    
    //create the sequence
    try {
      sequence = new Sequence(Sequence.PPQ, 24);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  
    //add all the tracks
    for (Integer k : composition.getTracks().keySet()) {
      try {
        this.addTrack(composition.getTracks().get(k), k);
      } catch (InvalidMidiDataException e) {
        JMidiUtils.message(e.toString(), ap);
      }
  
    }

    // Get default sequencer.
    try {
      sequencer = MidiSystem.getSequencer();
      sequencer.setTempoInMPQ(composition.getTempo());
      sequencer.open();
      sequencer.setSequence(sequence);
  
      JMidiUtils.message("Sequence Initialized", ap);
      
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
  
  }

  /**
   * Plays the directed track.
   * @param track the track you want to play.
   */
  private void addTrack(JMidiTrack track, int trackNumber) throws InvalidMidiDataException {
  
    JMidiUtils.message("Adding Track #" + trackNumber, ap);
    
    Track sTrack = sequence.createTrack();
  
    //set instrument and voice
    ShortMessage message = new ShortMessage( );
    message.setMessage(ShortMessage.PROGRAM_CHANGE, trackNumber, track.getInstrument().getNumber(), 0);
    sTrack.add(new MidiEvent(message, 0));

    //add all the notes to the midi track
    for (int i = 0; i < track.getMaxTick(); i++) {

      for (JMidiEvent e : track.getEventsOnTick(i)) {

          //start message
          MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, e.getChannel(), e.getPitch(),
                  e.getVelocity());
          MidiEvent aEvent = new MidiEvent(start,e.getTick() * 24);
          sTrack.add(aEvent);

          //end message
          MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, e.getChannel(), e.getPitch(),
                  e.getVelocity());
          MidiEvent bEvent = new MidiEvent(stop,(e.getTick() + e.getDuration()) * 24);
          sTrack.add(bEvent);
  
      }

    }
    
  }
  
  /**
   * Returns the current position of the tick.
   */
  public long getTickPosition() {
    
    return sequencer.getTickPosition();
    
  }
  
  /**
   * Starts playback.
   */
  public void play() {
  
    JMidiUtils.message("Playing Sequence", ap);
    sequencer.start();
  }
  
  /**
   * Stops playback.
   */
  public void stop() {
    JMidiUtils.message("Stopping Sequence", ap);
    sequencer.stop();
  }
  
  /**
   * return the midi file of the composition
   */
  public File export() {
  
    JMidiUtils.message("Exporting Sequence...", ap);
    File file = new File("composition.mid");
    try {
      MidiSystem.write(sequence,1,file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  
    JMidiUtils.message("Done :)", ap);
    return file;
  
  }
  
}
