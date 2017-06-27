package cs3500.music.view;

import cs3500.music.controller.CompositeController;
import cs3500.music.controller.ICompositionController;
import cs3500.music.model.JMidiComposition;
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
  }
  
  /**
   * Initializes the view.
   */
  public void initController() {
    new CompositeController(this, gui, composition, ap);
  }
  
  /**
   * Assigns a keyboard and mouse listener to the gui.
   */
  public void addListener(ICompositionController controller) {
    gui.addListener(controller);
  }
  
  
}
