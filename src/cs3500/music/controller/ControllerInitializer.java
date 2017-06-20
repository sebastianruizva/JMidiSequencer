package cs3500.music.controller;

import cs3500.music.view.AudioView;
import cs3500.music.view.CompositeView;
import cs3500.music.view.ICompositionView;

/**
 * Created by sebastian on 6/20/17.
 */
public class ControllerInitializer implements IVisitor{
  
  ICompositionView view;
  Readable rd;
  Appendable ap;
  
  ControllerInitializer(ICompositionView view, Readable rd, Appendable ap) {
    
    this.view = view;
    this.rd = rd;
    this.ap = ap;
    
  }
  
  public void visit(CompositeController controller) {
    
    controller.initialize((CompositeView) view, rd, ap);
    
  }
  
  public void visit(AudioController controller) {
    
    controller.initialize((AudioView) view, rd, ap);
    
  }
}
