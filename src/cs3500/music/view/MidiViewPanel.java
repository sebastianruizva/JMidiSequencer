package cs3500.music.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.swing.*;

import cs3500.music.model.JMidiEvent;
import cs3500.music.model.JMidiTrack;

/**
 * the class {@MidiVeiwPanel} represents a graphic view of the midi notes in a track
 */
public class MidiViewPanel extends JPanel {
  
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  int noteWidth;
  int noteHeight;
  int topMargin;
  private int width;
  private int height;
  private int maxPitch;
  private int maxTick;
  
  public MidiViewPanel(JMidiTrack track) {

    this.track = track;
    this.grid = track.getGrid();
    this.noteWidth = DrawValues.RECTANGLE_W;
    this.noteHeight = DrawValues.RECTANGLE_H;
    this.topMargin = DrawValues.GRID_MARGIN;
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
  
    @Override
    public void paintComponent(Graphics g) {
    
    super.paintComponent(g);
    
    //Place the elements in the grid
    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {
  
         JMidiTrack.SectorType type = track.getSectorType(tick, pitch);
           
           if (JMidiTrack.SectorType.HEAD == type) {
  
             g.setColor(DrawValues.NOTE_HEAD_COLOR);
             
           }
            if (JMidiTrack.SectorType.BODY == type) {
  
              g.setColor(DrawValues.NOTE_TAIL_COLOR);
        
            }
            
        g.fillRect(tick * noteWidth, height - ((pitch * noteHeight) + topMargin), noteWidth,
                noteHeight);
        
        g.drawRect(tick * noteWidth, height - ((pitch * noteHeight) + topMargin), noteWidth,
                noteHeight);
        
      }
    }
    
  }
  
}



