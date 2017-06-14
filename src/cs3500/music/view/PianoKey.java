package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

/**
 * Created by joe on 6/13/17.
 */
public class PianoKey extends JPanel {
/*  private int x;
  private int y;*/
  private boolean isSelected;
  private KeyType type;

  public PianoKey(/*int x, int y, */KeyType type) {
/*    this.x = x;
    this.y = y;*/
    this.isSelected = false;
    this.type = type;
    initComponent();
  }


  public void initComponent() {
    setBackground(getColor());
    setBorder(BorderFactory.createLineBorder(Color.BLACK));
    setSize(type.getWidth(), type.getHeight());
  }

  public int getDepth() {
    return type.getDepth();
  }

  public KeyType getType() {
    return type;
  }

  public Color getColor() {
    Color keyColor = type.getColor();

    if (isSelected) {
      keyColor = Color.ORANGE;
    }

    return keyColor;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public enum KeyType {
    WHITE(Color.WHITE, 20, 100, 0), BLACK(Color.BLACK, 16, 50, 1);

    private Color color;
    private int width;
    private int height;
    private int depth;

    KeyType(Color color, int width, int height, int depth) {
      this.color = color;
      this.width = width;
      this.height = height;
      this.depth = depth;
    }

    public Color getColor() {
      return color;
    }

    public int getHeight() {
      return height;
    }

    public int getWidth() {
      return width;
    }

    public int getDepth() {
      return depth;
    }
  }
}
