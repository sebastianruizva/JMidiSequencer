package cs3500.music.model;

/**
 * The class {@TimeSignature} Represents how many beats (pulses) are to be contained in each bar
 * and which note value is to be given one beat in a musical composition.
 */
public class JTimeSignature {
  
  /**
   * The lower numeral indicates the note value that represents one beat (the beat unit).
   */
  private final int lower;
  
  
  /**
   * The upper numeral indicates how many such beats there are grouped together in a bar.
   */
  private final int upper;
  
  /**
   * Constructs a {@TimeSignature}.
   * @param upper indicates the note value that represents one beat (the beat unit).
   * @param lower indicates how many such beats there are grouped together in a bar.
   */
  public JTimeSignature(int upper, int lower) {
    if (upper < 0 || lower < 0) {
      throw new IllegalArgumentException("Only positive values are supported! ex 4/4");
    }
    
    this.upper = upper;
    this.lower = lower;
  }
  
  /**
   * Retrieves the lower numeral.
   */
  public int getLower() {
    return lower;
  }
  
  /**
   * Retrieves the upper numeral.
   */
  public int getUpper() {
    return upper;
  }
  
  /**
   * Returns a string with the information of the time signature.
   */
  @Override public String toString() {
    return "cs3500.music.model.JTimeSignature{" + "lower=" + lower + ", upper=" + upper + '}';
  }
  
}
