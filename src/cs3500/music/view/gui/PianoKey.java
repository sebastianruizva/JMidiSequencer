package cs3500.music.view.gui;

import java.awt.*;

/**
 * Represents a key on a piano.
 */
public class PianoKey {
  public int pitch;
  private Rectangle hitBox;
  private PianoType type;
  
  /**
   * Constructs a key on a piano.
   * @param x     the x posn.
   * @param type  the type of key
   * @param pitch the pitch of the key.
   * @param y     the y posn.
   */
  public PianoKey(int x, int y, PianoType type, int pitch) {
    this.type = type;
    this.pitch = pitch;

    switch (type) {
      case WHITE: {
        this.hitBox = new Rectangle(x, y, 20, 350);
        break;
      }
      case BLACK: {
        this.hitBox = new Rectangle(x, y, 10, 175);
        break;
      }
      default: {
        throw new IllegalArgumentException("Invalid type.");
      }
    }
  }

  public PianoType getType() {
    return this.type;
  }

  public int getPitch() {
    return pitch;
  }

  public Rectangle getHitBox() {
    return new Rectangle(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
  }

  public Color getColor() {
    return type.getColor();
  }

  @Override
  public String toString() {
    return "{ Type: " + type.toString() + " Pitch: " + pitch + " }";
  }

  public enum PianoType {
    BLACK(Color.BLACK), WHITE(Color.WHITE);

    private Color color;

    PianoType(Color color) {
      this.color = color;
    }

    public Color getColor() {
      return this.color;
    }
  }
}
