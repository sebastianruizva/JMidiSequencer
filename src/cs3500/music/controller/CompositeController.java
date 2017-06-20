package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.CompositeViewCommands.Play;
import cs3500.music.view.CompositeView;

/**
 * Created by sebastian on 6/19/17.
 */
public class CompositeController implements IVisitableController {
  
  private Map<Integer, IAudioCommand> supportedCommands;
  private CompositeView view;

  @Override
  public void accept(IVisitor visitor) {
    visitor.visit(this);
  }

  public void initialize(CompositeView view, Readable rd, Appendable ap) {
  
    this.view = view;
    this.supportedCommands = new HashMap<>();
    
    supportedCommands.put(KeyEvent.VK_SPACE, new Play());
  }
  
  @Override public void keyTyped(KeyEvent e) {
  
  }
  
  @Override public void keyPressed(KeyEvent e) {
  
    IAudioCommand cmd = supportedCommands.getOrDefault(e, null);
    if (cmd == null) {
      throw new IllegalArgumentException();
    } else {
      cmd.execute(view);
    }
    
  }
  
  @Override public void keyReleased(KeyEvent e) {
  
  }
}
