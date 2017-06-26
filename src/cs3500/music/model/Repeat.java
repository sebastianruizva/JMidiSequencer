package cs3500.music.model;

/**
 * The class {@Repeat} represents a musical repeat in a composition.
 */
public class Repeat {
  
  public final int startingBar;
  public final int endingBar;
  public final Type type;
  
  public Repeat(Type type, int startingBar, int endingBar) throws IllegalArgumentException {
    
    if (startingBar < 0) {
      throw new IllegalArgumentException("starting bar cant be negative!");
    }
    
    if (endingBar <= startingBar) {
      throw new IllegalArgumentException("starting bar must be bigger than ending bar!");
    }
    
    this.startingBar = startingBar;
    this.endingBar = endingBar;
    this.type = type;
    
  }
  
  public Repeat(int startingBar, int endingBar) throws IllegalArgumentException {
    
    if (startingBar < 0) {
      throw new IllegalArgumentException("starting bar cant be negative!");
    }
    
    if (endingBar <= startingBar) {
      throw new IllegalArgumentException("starting bar must be bigger than ending bar!");
    }
    
    this.startingBar = startingBar;
    this.endingBar = endingBar;
    this.type = Type.DEFAULT;
    
  }
  
  @Override public String toString() {
    return "Repeat{" + "startingBar=" + startingBar + ", endingBar=" + endingBar + '}';
  }
  
  public enum Type {
    ENDING, DEFAULT
  }
}
