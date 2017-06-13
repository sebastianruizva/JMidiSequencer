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
  int noteWidth;
  int noteHeight;
  
  public GridViewPanel(JMidiTrack track) {
    
    this.track = track;
    this.grid = track.getGrid();
    this.noteWidth = DrawValues.RECTANGLE_W.getValue();
    this.noteHeight = DrawValues.RECTANGLE_H.getValue();
    
  }
  
  @Override
  public void paintComponent(Graphics g) {
  
  
  
  }
  
  
  }
