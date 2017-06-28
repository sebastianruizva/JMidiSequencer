package cs3500.music.model;

/**
 * The class {@Repeat} represents a musical repeat in a composition.
 */
public class Repeat {
  
  /**
   * the bar where the repeat jumps too when finished.
   */
  public final int startingBar;
  /**
   * the bar where the repeat jumps from.
   */
  public final int endingBar;
  /**
   * One of DEFAULT or ENDING, if ending will be ignored in the next repeat.
   */
  public final Type type;
  
  /**
   * Constructs a {@Repeat} object.
   * @param type        one of DEFAULT or ENDING, if ending will be ignored in the next repeat.
   * @param startingBar the bar where the repeat jumps too when finished.
   * @param endingBar   the bar where the repeat jumps from.
   */
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
  
  /**
   * Constructs a {@Repeat} object.
   * @param startingBar the bar where the repeat jumps too when finished.
   * @param endingBar the bar where the repeat jumps from.
   */
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
    return "Repeat{" + "startingBar=" + startingBar + ", endingBar=" + endingBar + ", type=" + type
            + '}';
  }
  
  /**
   * One of DEFAULT or ENDING, if ending will be ignored in the next repeat.
   */
  public enum Type {
    ENDING, DEFAULT
  }
}
