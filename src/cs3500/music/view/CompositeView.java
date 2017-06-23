package cs3500.music.view;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.swing.*;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ICompositionController;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView extends AudioView implements MetaEventListener {
  
  protected MusicEditorGUI gui;
  Timer timer;
  
  /**
   * Plays the directed composition.
   * @param composition the composition you want to play.
   */
  public CompositeView(JMidiComposition composition, Appendable ap) {
    
    super(composition, ap);
    JMidiUtils.message("Connecting to GUI", ap);
    this.gui = new MusicEditorGUI(composition, ap);
    this.gui.initialize();
    JMidiUtils.message("Synchronizing views", ap);
    timer = new javax.swing.Timer(100, e -> sync());
    timer.start();
    JMidiUtils.message("Composite view ready!", ap);
  }
  
  public void initialize() {
    new CompositeController(this, gui, composition, ap);
    JMidiUtils.message("Composite view initialized", ap);
  }
  
  public void addListener(ICompositionController controller) {
    gui.addListener(controller);
  }
  
  
  
  /**
   * Synchronizes the audio and gui views.
   */
  public void sync() {
    if ((int) sequencer.getTickPosition() / 24 < composition.getMaxTick()) {
      gui.setCursorAbsolutePosition((int) sequencer.getTickPosition() / 24);
    } else {
      sequencer.setTickPosition(0);
      sequencer.setTempoInMPQ(composition.getTempo());
    }
  }
  
  /**
   * MAkes sure that once the sequence has ended the sequencer closes.
   */
  @Override public void meta(MetaMessage meta) {
    
    if (meta.getType() == 47) {
      
      sequencer.close();
      
    }
    
  }
  
  public void play() {
    super.play();
  }
  
  public void pause() {
    super.pause();
  }
  
  
}
