package cs3500.music.view;

import java.awt.*;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ICompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView extends AudioView {
  
  protected MusicEditorGUI visual;
  
  /**
   * Plays the directed composition.
   * @param composition the composition you want to play.
   */
  public CompositeView(JMidiComposition composition, Appendable ap) {
    
    super(composition, ap);
    JMidiUtils.message("Connecting to GUI", ap);
    this.visual = new MusicEditorGUI(composition, ap);
    this.visual.initialize();
    JMidiUtils.message("Synchronizing views", ap);
    this.sync();
    JMidiUtils.message("Composite view ready!", ap);
    
  }
  
  public void initialize() {
    new CompositeController(this, ap);
    JMidiUtils.message("Composite view initialized", ap);
  }
  
  /**
   * Synchronizes the audio and visual views.
   */
  public void sync() {
    Thread syncThreat = new Thread(() -> {
      int newTick = 0;
      int oldTick;
      int interval;
      while (true) {
        if (newTick < composition.getMaxTick()) {
          oldTick = newTick;
          newTick = (int) sequencer.getTickPosition() / 24;
          interval = newTick - oldTick;
          if (interval != 0) {
            visual.setCursorPosition(interval);
          }
        } else {
          this.pause();
          sequencer.setTickPosition(0);
          sequencer.setTempoInMPQ(composition.getTempo());
          visual.setCursorPosition(0);
          newTick = 0;
          oldTick = 0;
        }
      }
    });
    syncThreat.start();
  }
  
  public void addNote(Point point) {
    
    try {
      PianoKey key = visual.getKeyAtPosition(point);
      composition.addNote(visual.getCursorPosition(), key.getPitch());
      visual.setCursorPosition(1);
      visual.refreshPanels();
      JMidiUtils.message("***********************************", ap);
      JMidiUtils.message("*          Note added             *", ap);
      JMidiUtils.message("***********************************", ap);
      JMidiUtils.message(key.toString(), ap);
    } catch (IllegalArgumentException e) {
      JMidiUtils.message("No key here...", ap);
    }
  }
  
  public void addListener(ICompositionController controller) {
    visual.addListener(controller);
  }
  
}
