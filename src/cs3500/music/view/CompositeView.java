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
   * A synthesizer for direct feedback
   */
  protected Synthesizer synthesizer;
  
  /**
   * An array of midi channels for the synthesizer
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
    this.practiceEvents = new HashMap<>();
    this.playedEvents = new ArrayList<>();
    gui.practiceEvents = this.practiceEvents;
    prepareSynthesizer();
    JMidiUtils.message("Composite view ready!", ap);
  
  }
  
  /**
   * Determines if the current bar is part of a repeat.
   */
  @Override public void validRepeat() {
    int bar = ((int) tick / 24) / 4;
    if (this.composition.getRepeats().keySet().contains(bar) && !this.playedRepeats.contains(bar)) {
      playedEvents.clear();
    }
    super.validRepeat();
  }
  
  /**
   * Jumps to the beginning of the composition.
   */
  @Override public void beginning() {
    super.beginning();
    playedEvents.clear();
  }
  
  /**
   * prepares a synthesizer for instant playback.
   */
  public void prepareSynthesizer() {
    try {
      this.synthesizer = MidiSystem.getSynthesizer();
      this.synthesizer.open();
      this.mc = synthesizer.getChannels();
      Instrument[] instr = synthesizer.getDefaultSoundbank().getInstruments();
      synthesizer.loadInstrument(instr[0]);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * plays the desired pitch.
   * @param pitch the pitch you want to play.
   */
  public void playNote(int pitch) {
    mc[1].noteOn(pitch, 600);
  }
  
  /**
   * Determines if practice mode is enabled.
   */
  public boolean practiceEnabled() {
    JMidiUtils.message("Practice Mode Enabled", ap);
    return tasks.containsKey(4);
  }
  
  /**
   * Enables practice mode.
   */
  public void disablePracticeMode() {
    JMidiUtils.message("Practice Mode Disabled", ap);
    if (tasks.containsKey(4)) {
      tasks.remove(4);
      this.practiceEvents.clear();
      this.playedEvents.clear();
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
  protected void practiceMode() {
    //add if not been played yet
    for (JMidiEvent e : this.composition.getEventsOnTick((int) tick / 24)) {
      if (!playedEvents.contains(e)) {
        this.practiceEvents.put(e.getPitch(), e);
        gui.refreshPanels();
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
