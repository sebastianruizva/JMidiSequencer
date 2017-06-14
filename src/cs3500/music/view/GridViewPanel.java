package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * Created by sebastian on 6/13/17.
 */
public class GridViewPanel extends JPanel {
  
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  private int width;
  private int height;
  private int maxPitch;
  private int maxTick;
  
  public GridViewPanel(JMidiTrack track) {
    
    this.track = track;
    this.grid = track.getGrid();
    this.maxPitch = track.getMaxPitch();
    this.maxTick = track.getMaxTick();
    
    if (DrawValues.MIN_GRID_HEIGHT < (DrawValues.RECTANGLE_H * maxPitch)) {
      
      this.height = DrawValues.RECTANGLE_H * maxPitch;
      
    } else {
      
      this.height = DrawValues.MIN_GRID_HEIGHT;
      
    }
    
    if (DrawValues.MIN_GRID_WIDTH < (DrawValues.RECTANGLE_W * maxTick)) {
      
      this.width = DrawValues.RECTANGLE_W * maxTick;
      
    } else {
      
      this.width = DrawValues.MIN_GRID_WIDTH;
      
    }
    
  }
  
  @Override public void paintComponent(Graphics g) {
  
    for (int i = 0; i <= (width / DrawValues.RECTANGLE_W); i++) {
      
      if(i%4 == 0) {
        g.setColor(DrawValues.GRID_BORDER_COLOR);
        g.drawLine(i*DrawValues.RECTANGLE_W, height, i*DrawValues
                .RECTANGLE_W, DrawValues.GRID_MARGIN);
      }
      
    }
  
    for (int i = 0; i <= ((height - DrawValues.GRID_MARGIN) / DrawValues.RECTANGLE_H); i++) {
      
        g.setColor(DrawValues.GRID_BORDER_COLOR);
        g.drawLine(0, (i*DrawValues.RECTANGLE_H) + DrawValues
                .GRID_MARGIN, width, (i*DrawValues.RECTANGLE_H) + DrawValues
                .GRID_MARGIN);
    }
  }
}
