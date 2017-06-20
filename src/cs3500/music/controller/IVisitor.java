package cs3500.music.controller;

/**
 * Created by sebastian on 6/20/17.
 */
public interface IVisitor {
  
  void visit(CompositeController controller);
  void visit(AudioController controller);
  void visit(KeyboardController controller);
 
}
