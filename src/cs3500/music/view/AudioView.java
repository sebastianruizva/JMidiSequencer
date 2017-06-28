package cs3500.music.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.*;

import cs3500.music.controller.AudioController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;
import cs3500.music.model.Repeat;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@MidiViewImpl} implements a MIDI playback view of a composition.
 */
public class AudioView extends java.util.Observable implements ICompositionView {
  
  /**
   * the current tick.
   */
  public long tick;
  /**
   * an appendable for messages.
   */
  protected Appendable ap;
  /**
   * A MIDI Sequence object.
   */
  protected Sequence sequence;
  /**
   * A MIDI sequencer object.
   */
  protected Sequencer sequencer;
  /**
   * the composition being observed.
   */
  protected JMidiComposition composition;
  /**
   * A List of repeats that have being applied
   */
  protected List<Integer> playedRepeats;
  /**
   * A list of bars that must not be replayed
   */
  protected List<Integer> ignoredBars;
  /**
   * A Map of tasks by id
   */
  protected HashMap<Integer, Runnable> tasks;
  
  /**
   * Constructs an {@AudioView}.
   * @param ap          an appendable for messages.
   * @param composition the composition being observed
   */
  public AudioView(JMidiComposition composition, Appendable ap) {
    if (composition == null || ap == null) {
      throw new IllegalArgumentException("no null!");
    }
    JMidiUtils.message("Preparing Audio View", ap);
    this.playedRepeats = new ArrayList<>();
    this.ignoredBars = new ArrayList<>();
    this.tasks = new HashMap<>();
    this.ap = ap;
    this.composition = composition;
    this.composition.addObserver(this);
    this.prepareSequencer();
    this.initTasks();
    JMidiUtils.message("Audio View Ready", ap);
  }
  
  /**
   * Constructs an {@AudioView} [Alternative constructor for testing].
   * @param ap          an appendable for messages.
   * @param composition the composition being observed
   * @param sequencer   space for a custom sequencer
   * @throws IllegalArgumentException if anything is null
   */
  public AudioView(JMidiComposition composition, Appendable ap, Sequencer sequencer) {
    if (composition == null || sequencer == null || ap == null) {
      throw new IllegalArgumentException("no null!");
    }
    JMidiUtils.message("Preparing Audio View", ap);
  
    try {
    
      JMidiUtils.message("Initializing Sequencer", ap);
    
      this.ap = ap;
      this.composition = composition;
      this.sequence = sequencer.getSequence();
      this.addAllTracks();
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.setTempoInMPQ(composition.getTempo());
      this.sequencer.open();
      this.sequencer.setSequence(sequence);
      //observe for repeats and update observers
      JMidiUtils.message("Sequencer Ready", ap);
    
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
    
    JMidiUtils.message("Audio View Ready", ap);
  }
  
  /**
   * Initializes the view.
   */
  public void initController() {
    JMidiUtils.message("Audio View Initialized", ap);
    new AudioController(this, ap);
  }
  
  /**
   * Initializes time related tasks.
   */
  public void initTasks() {
    //add tasks
    tasks.put(1, () -> registerChanges());
    tasks.put(2, () -> validRepeat());
    tasks.put(3, () -> ignoredBar());
    //create timer every 1000 / (sequencers resolution) of a ms.
    Timer timer = new javax.swing.Timer(1000 / 24, (e) -> {
      for (Integer i : tasks.keySet()) {
        tasks.get(i).run();
      }
    });
    //run
    timer.start();
  }
  
  /**
   * Registers changes if any
   */
  public void registerChanges() {
    if (this.sequencer.getTickPosition() != this.tick) {
      this.tick = this.sequencer.getTickPosition();
      setChanged();
      notifyObservers((int) tick);
    }
  }
  
  /**
   * Determines if the current bar should be ignored.
   */
  public void ignoredBar() {
    int bar = ((int) tick / 24) / 4;
    if (ignoredBars.contains(bar)) {
      JMidiUtils.message("Jumping bar", this.ap);
      this.sequencer.setTickPosition(this.sequencer.getTickPosition() + (24 * 4));
      this.sequencer.setTempoInMPQ(this.composition.getTempo());
    }
  }
  
  /**
   * Determines if the current bar is part of a repeat.
   */
  public void validRepeat() {
    int bar = ((int) tick / 24) / 4;
    if (this.composition.getRepeats().keySet().contains(bar) && !this.playedRepeats.contains(bar)) {
      JMidiUtils.message("Valid repeat point found", this.ap);
      this.sequencer.setTickPosition(this.composition.getRepeats().get(bar).startingBar * (4 * 24));
      this.sequencer.setTempoInMPQ(this.composition.getTempo());
      this.playedRepeats.add(bar);
      if (this.composition.getRepeats().get(bar).type == Repeat.Type.ENDING) {
        JMidiUtils.message("Bar is going to be jumped in the next repeat", this.ap);
        this.ignoredBars.add(bar - 1);
      }
    }
  }
  
  /**
   * Constructs the sequencer.
   */
  public void prepareSequencer() {
    try {
      
      JMidiUtils.message("Initializing Sequencer", ap);
      
      this.sequence = new Sequence(Sequence.PPQ, 24);
      this.addAllTracks();
      this.sequencer = MidiSystem.getSequencer();
      this.sequencer.setTempoInMPQ(composition.getTempo());
      this.sequencer.open();
      this.sequencer.setSequence(sequence);
      this.tick = this.sequencer.getTickPosition();
      JMidiUtils.message("Sequencer Ready", ap);
      
    } catch (MidiUnavailableException | InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Adds all the tracks.
   */
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
   * @param trackNumber the track's number
   */
  protected void addTrack(JMidiTrack track, int trackNumber) throws InvalidMidiDataException {
    
    JMidiUtils.message("Adding Track #" + trackNumber, ap);
    Track sTrack = sequence.createTrack();
    //set instrument and voice
    ShortMessage message = new ShortMessage();
    message.setMessage(ShortMessage.PROGRAM_CHANGE, trackNumber, track.getInstrument().getNumber(),
            0);
    sTrack.add(new MidiEvent(message, 0));
    //add all the notes to the track
    for (int i = 0; i < track.getMaxTick(); i++) {
      for (JMidiEvent event : track.getEventsOnTick(i)) {
        addEvent(sTrack, event);
      }
    }
  
  }
  
  protected void addEvent(Track track, JMidiEvent event) throws InvalidMidiDataException {
    
    //start message
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, event.getChannel(), event.getPitch(),
            event.getVelocity());
    MidiEvent aEvent = new MidiEvent(start, event.getTick() * 24);
    track.add(aEvent);
    
    //end message
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, event.getChannel(), event.getPitch(),
            event.getVelocity());
    MidiEvent bEvent = new MidiEvent(stop, (event.getTick() + event.getDuration()) * 24);
    track.add(bEvent);
    
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
   * Jumps to the beginning of the composition.
   */
  public void beginning() {
    JMidiUtils.message("clearing history", ap);
    this.playedRepeats = new ArrayList<>();
    this.ignoredBars = new ArrayList<>();
    JMidiUtils.message("jumping to Start", ap);
    sequencer.setTickPosition(0);
    sequencer.setTempoInMPQ(composition.getTempo());
  }
  
  
  /**
   * Jumps to the end of the composition.
   */
  public void end() {
    JMidiUtils.message("jumping End", ap);
    sequencer.setTickPosition((composition.getMaxTick() - 1) * 24);
    sequencer.setTempoInMPQ(composition.getTempo());
  }
  
  /**
   * return the midi file of the composition.
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
   * updates the view
   * @param o   the model
   * @param arg the args sent by the model
   */
  @Override public void update(java.util.Observable o, Object arg) {
    JMidiUtils.message("Updating Audio VIew", ap);
    try {
      long tick = sequencer.getTickPosition();
      this.sequence = new Sequence(Sequence.PPQ, 24);
      this.addAllTracks();
      this.sequencer.setSequence(sequence);
      sequencer.setTickPosition(tick);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }
  
}
