package cs3500.music.controller;

/**
 * Created by sebastian on 6/19/17.
 */
public interface IVisitableController extends java.awt.event.KeyListener {
  
  public void accept(IVisitor visitor);
  
}
