package cs3500.music.view;


import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class PianoViewPanel extends JLayeredPane {
  ArrayList<Integer> selectedPitches;

  public PianoViewPanel(ArrayList<Integer> selectedPitches) {
    this.selectedPitches = selectedPitches;
  }

  public PianoViewPanel() {
    this.selectedPitches = new ArrayList<>();
  }

  @Override
  public void paint(Graphics g) {
    setSize(new Dimension(1500, 400));

    paintBackground(g);
    paintWhiteKeys(g);
    paintBlackKeys(g);

    for (int i : selectedPitches) {
      fillRectangle(g, i);
    }
  }

  private void paintBackground(Graphics g) {
    g.setColor(Color.DARK_GRAY);
    g.drawRect(0,0,1500, 400);
    g.fillRect(0,0,1500, 400);
    g.setColor(Color.BLACK);
    g.drawRect(0,0,1500, 400);
  }

  private void paintWhiteKeys(Graphics g) {
    int size = DrawValues.RECTANGLE_H;
    int offset = 50;

    for (int i = 0; i < 70; i++) {
      g.setColor(Color.WHITE);
      g.drawRect(i * size + offset, 0, size, 350);
      g.fillRect(i * size + offset, 0, size, 350);
      g.setColor(Color.BLACK);
      g.drawRect(i * size + offset, 0, size, 350);
    }
  }

  private void paintBlackKeys(Graphics g) {
    int skip = 2;
    int oddCount = 0;
    int size = DrawValues.RECTANGLE_H;
    int offset = 50;

    for (int i = 0; i < 70; i++) {

      if (i == skip) {
        if (oddCount % 2 == 0) {
          skip += 4;
        } else {
          skip += 3;
        }
        oddCount++;
        continue;
      }
      g.setColor(Color.BLACK);
      g.drawRect(i * size + size - 4 + offset, 0, size / 2, 175);
      g.fillRect(i * size + size - 4 + offset, 0, size / 2, 175);
    }
  }

  public void fillRectangle(Graphics g, int pitch) {

  }
}
