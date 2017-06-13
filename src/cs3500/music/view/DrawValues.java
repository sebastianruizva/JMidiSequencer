package cs3500.music.view;

/**
 * Created by sebastian on 6/13/17.
 */
public enum DrawValues {
  
  RECTANGLE_H(20), RECTANGLE_W(40);
  
  int value;
  
  DrawValues(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return value;
  }
}
