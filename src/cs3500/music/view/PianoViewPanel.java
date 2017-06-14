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
    paintWhiteKeys(g);
    paintBlackKeys(g);

    for (int i : selectedPitches) {
      fillRectangle(g, i);
    }
  }

  private void paintWhiteKeys(Graphics g) {
    int size = DrawValues.RECTANGLE_H;

    for (int i = 0; i < 70; i++) {
      g.setColor(Color.WHITE);
      g.drawRect(i * size, 0, size, 350);
      g.fillRect(i * size, 0, size, 350);
      g.setColor(Color.BLACK);
      g.drawRect(i * size, 0, size, 350);

      System.out.println("X: " + i * size);
    }
  }

  private void paintBlackKeys(Graphics g) {
    int skip = 2;
    int oddCount = 0;
    int size = DrawValues.RECTANGLE_H;

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
      g.drawRect(i * size + size - 4, 0, size / 2, 175);
      g.fillRect(i * size + size - 4, 0, size / 2, 175);
    }
  }

  public void fillRectangle(Graphics g, int pitch) {

  }
}
