package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

import cs3500.music.model.IjVirtualInstrument;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * A {@link JPanel} for displaying the range of pitches in the provided {@link JMidiComposition}.
 * If the range of pitches in the composition is less than twelve, twelve will be displayed
 * regardless. Otherwise, a range extending from the lowest note in the composition to the highest
 * in the composition will be generated.
 */
public class PitchViewPanel extends JPanel {
  private JMidiComposition composition;
  private int maxPitch;
  private int height;

  /**
   * Constructs a new PitchViewPanel based on the provided composition.
   *
   * @param composition the composition to draw the range of pitches from.
   * @throws IllegalArgumentException if the composition is null.
   */
  public PitchViewPanel(JMidiComposition composition) throws IllegalArgumentException {
    if (composition == null) {
      throw new IllegalArgumentException("Composition cannot be null.");
    }

    this.composition = composition;
    this.maxPitch = composition.getMaxPitch();

    if (maxPitch < 12) {
      maxPitch = 12;
    }

    setSize();
    setPreferredSize(new Dimension(DrawValues.RECTANGLE_W, height));
    setMaximumSize(getPreferredSize());
  }

  /**
   * Sets the size of this panel. If the calculated size is smaller than the defualt value, the
   * default is used.
   */
  private void setSize() {
    if (DrawValues.MIN_GRID_HEIGHT < (DrawValues.RECTANGLE_H * maxPitch)) {

      height = DrawValues.RECTANGLE_H * maxPitch;

    } else {

      height = DrawValues.MIN_GRID_HEIGHT;

    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    IjVirtualInstrument inst = JMidiUtils.defualtVI();
    g.setFont(DrawValues.VERDANA);


    int minPitch = composition.getMinPitch();
    for (int i = minPitch; i <= maxPitch; i++) {
      g.drawString(inst.getNoteRepresentation(i) + (i / inst.getOctaveDegree()), 0,
              (maxPitch - i) * 20 + DrawValues.GRID_MARGIN + 20);
    }
  }
}
