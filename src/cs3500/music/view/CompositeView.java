package cs3500.music.view;

import javax.swing.*;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ICompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.view.visual.MusicEditorGUI;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView extends AudioView {
  
  /**
   * The GUI that needs to be synchronized.
   */
  protected MusicEditorGUI gui;
  
  /**
   * A timer to keep the syncronization.
   */
  Timer timer;
  
  /**
   * Plays the directed composition.
   * @param composition the composition you want to play.
   * @param ap an appendable for messages.
   */
  public CompositeView(JMidiComposition composition, Appendable ap) {
    //initiate audio view
    super(composition, ap);
    //connect to GUI
    JMidiUtils.message("Connecting to GUI", ap);
    this.gui = new MusicEditorGUI(composition, ap);
    this.gui.initialize();
    JMidiUtils.message("Synchronizing views", ap);
    //Sync with the GUI
    timer = new javax.swing.Timer(100, e -> sync());
    timer.start();
    JMidiUtils.message("Composite view ready!", ap);
  }
  
  /**
   * Initializes the view.
   */
  public void initialize() {
    //selects its controller
    new CompositeController(this, gui, composition, ap);
    JMidiUtils.message("Composite view initialized", ap);
  }
  
  /**
   * Assigns a keyboard and mouse listener.
   */
  public void addListener(ICompositionController controller) {
    gui.addListener(controller);
  }
  
  /**
   * Synchronizes the audio and gui views.
   */
  public void sync() {
    //if son has not ended
    if ((int) sequencer.getTickPosition() / 24 < composition.getMaxTick()) {
      gui.setCursorAbsolutePosition((int) sequencer.getTickPosition() / 24);
    } else {
      //go back to the start
      sequencer.setTickPosition(0);
      sequencer.setTempoInMPQ(composition.getTempo());
    }
  }
  
  
}
