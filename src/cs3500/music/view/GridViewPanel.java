package cs3500.music.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * Created by sebastian on 6/13/17.
 */
public class GridViewPanel  extends JPanel {
  
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  int width;
  int height;
  
  public GridViewPanel(JMidiTrack track) {
    
    this.track = track;
    this.grid = track.getGrid();
    
    
    if(DrawValues.GRID_HEIGHT < DrawValues.RECTANGLE_W*)
    
    
    this.width = DrawValues.RECTANGLE_W;
    this.height = DrawValues.RECTANGLE_H;
    
    
  }
  
  @Override
  public void paintComponent(Graphics g) {
  
  
  
  }
  
  
  }
