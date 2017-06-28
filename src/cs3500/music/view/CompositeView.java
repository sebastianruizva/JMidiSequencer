package cs3500.music.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ICompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.model.JMidiEvent;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.gui.GuiView;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView extends AudioView {
  
  /**
   * The GUI that needs to be synchronized.
   */
  protected GuiView gui;
  
  /**
   * A map of events that need to be played to continue by pitch.
   * - This is part of the practice mode.
   */
  protected Map<Integer, JMidiEvent> practiceEvents;
  
  /**
   * A list of played events.
   * - This is part of the practice mode.
   */
  protected ArrayList<JMidiEvent> playedEvents;
  
  /**
   * A synth for direct feedback
   */
  protected Synthesizer synth;
  
  /**
   * An array of midi channels for the synth
   */
  protected MidiChannel[] mc;
  
  /**
   * Plays the directed composition.
   * @param composition the composition you want to play.
   * @param ap          an appendable for messages.
   */
  public CompositeView(JMidiComposition composition, Appendable ap) {
    //initiate audio view
    super(composition, ap);
    //connect to GUI
    JMidiUtils.message("Connecting to GUI", ap);
    this.gui = new GuiView(composition, ap);
    this.addObserver(gui);
    JMidiUtils.message("Composite view ready!", ap);
    this.practiceEvents = new HashMap<>();
    this.playedEvents = new ArrayList<>();
    prepareSynth();
  
  }
  
  /**
   * prepares a synth for instant playback.
   */
  public void prepareSynth() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.synth.open();
      this.mc = synth.getChannels();
      Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
      synth.loadInstrument(instr[0]);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Determines if practice mode is enabled.
   */
  public void playNote(int pitch) {
    mc[1].noteOn(pitch, 600);
  }
  
  /**
   * Determines if practice mode is enabled.
   */
  public boolean practiceEnabled() {
    return tasks.containsKey(4);
  }
  
  /**
   * Enables practice mode.
   */
  public void disablePracticeMode() {
    if (tasks.containsKey(4)) {
      tasks.remove(4);
    }
  }
  
  /**
   * Disables practice mode.
   */
  public void enablePracticeMode() {
    if (!tasks.containsKey(4)) {
      tasks.put(4, () -> practiceMode());
    }
  }
  
  /**
   * Ensures that notes that have not been played halt and ask for user imput.
   */
  public void practiceMode() {
    //add if not been played yet
    for (JMidiEvent e : this.composition.getEventsOnTick((int) tick / 24)) {
      if (!playedEvents.contains(e)) {
        this.practiceEvents.put(e.getPitch(), e);
      }
    }
    //ensure the song does not play unless all keys have been played
    if (practiceEvents.size() > 0) {
      this.pause();
    } else {
      this.play();
    }
  }
  
  /**
   * Removes and Event fro the practice events list.
   */
  public void removePracticeEvent(int i) {
    JMidiEvent e = practiceEvents.getOrDefault(i, null);
    if (e != null) {
      System.out.println("removing " + i);
      playedEvents.add(e);
      practiceEvents.remove(i);
    }
  }
  
  /**
   * Initializes the view.
   */
  public void initController() {
    new CompositeController(this, gui, composition, ap);
  }
  
  /**
   * Assigns a keyboard and mouse listener t.
   */
  public void addListener(ICompositionController controller) {
    gui.addListener(controller);
  }
  
  
}
