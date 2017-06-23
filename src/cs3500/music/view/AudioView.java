package cs3500.music.view;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.controller.AudioController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a MIDI playback view of a composition.
 */
public class AudioView implements ICompositionView, MetaEventListener {
  
  protected Appendable ap;
  protected Sequence sequence;
  protected Sequencer sequencer;
  protected JMidiComposition composition;
  
  AudioView(JMidiComposition composition, Appendable ap) {
    JMidiUtils.message("Preparing Audio View", ap);
    this.ap = ap;
    this.composition = composition;
    this.prepareSequencer();
    this.sequencer.addMetaEventListener(this);
    JMidiUtils.message("Audio View Ready", ap);
  }
  
  /**
   * Plays the directed composition.
   */
  public void initialize() {
    new AudioController(this, ap);
    JMidiUtils.message("Audio View Initialized", ap);
    
  }
  
  protected void prepareSequencer() {
    try {
      
      JMidiUtils.message("Initializing Sequencer", ap);
      
      this.sequence = new Sequence(Sequence.PPQ, 24);
      this.addAllTracks();
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.setTempoInMPQ(composition.getTempo());
      this.sequencer.open();
      this.sequencer.setSequence(sequence);
      
      JMidiUtils.message("Sequencer Ready", ap);
      
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
  
  protected void addAllTracks() {
    //add all the tracks
    for (Integer k : composition.getTracks().keySet()) {
      try {
        this.addTrack(composition.getTracks().get(k), k);
      } catch (InvalidMidiDataException e) {
        JMidiUtils.message(e.toString(), ap);
      }
      
    }
  }
  
  /**
   * Plays the directed track.
   * @param track the track you want to play.
   */
  protected void addTrack(JMidiTrack track, int trackNumber) throws InvalidMidiDataException {
    
    JMidiUtils.message("Adding Track #" + trackNumber, ap);
    
    Track sTrack = sequence.createTrack();
    
    //set instrument and voice
    ShortMessage message = new ShortMessage();
    message.setMessage(ShortMessage.PROGRAM_CHANGE, trackNumber, track.getInstrument().getNumber(),
            0);
    sTrack.add(new MidiEvent(message, 0));
    
    //add all the notes to the midi track
    for (int i = 0; i < track.getMaxTick(); i++) {
  
      for (JMidiEvent e : track.getEventsOnTick(i)) {
    
        //start message
        MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, e.getChannel(), e.getPitch(),
                e.getVelocity());
        MidiEvent aEvent = new MidiEvent(start, e.getTick() * 24);
        sTrack.add(aEvent);
    
        //end message
        MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, e.getChannel(), e.getPitch(),
                e.getVelocity());
        MidiEvent bEvent = new MidiEvent(stop, (e.getTick() + e.getDuration()) * 24);
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
    sequencer.setTempoInMPQ(composition.getTempo());
    
  }
  
  /**
   * Stops playback.
   */
  public void pause() {
    JMidiUtils.message("Stopping Sequence", ap);
    sequencer.stop();
    sequencer.setTempoInMPQ(composition.getTempo());
  }
  
  /**
   * Stops forwards playback.
   */
  public void forward() {
    JMidiUtils.message("Forwarding Sequence", ap);
    sequencer.setTickPosition(sequencer.getTickPosition() + 24);
    sequencer.setTempoInMPQ(composition.getTempo());
  }
  
  /**
   * Stops rewinds playback.
   */
  public void rewind() {
    JMidiUtils.message("Rewinding Sequence", ap);
    sequencer.setTickPosition(sequencer.getTickPosition() - 24);
    sequencer.setTempoInMPQ(composition.getTempo());
  }
  
  /**
   * return the midi file of the composition
   */
  public File export() {
  
    JMidiUtils.message("Exporting Sequence...", ap);
    File file = new File("composition.mid");
    try {
      MidiSystem.write(sequence, 1, file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  
    JMidiUtils.message("Done :)", ap);
    return file;
  
  }
  
  /**
   * Makes sure that once the sequence has ended the sequencer closes.
   */
  public void meta(MetaMessage meta) {
    
    if (meta.getType() == 47) {
      
      sequencer.close();
      
    }
    
  }
  
}
