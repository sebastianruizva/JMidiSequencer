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
 * A dummy view that simply draws a string 
 */
public class MidiVeiwPanel extends JPanel {
  
  JMidiTrack track;
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;
  
  public MidiVeiwPanel(JMidiTrack track) {

    this.track = track;
    this.grid = track.getGrid();
    
  }
    @Override
   public void paintComponent(Graphics g) {
    
    super.paintComponent(g);
    
    //Place the elements in the grid
    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {
  
         JMidiTrack.SectorType type = track.getSectorType(tick, pitch);
           
           if (JMidiTrack.SectorType.HEAD == type) {
  
             g.setColor(Color.black);
             
           }
            if (JMidiTrack.SectorType.BODY == type) {
  
              g.setColor(Color.green);
        
            }
            
        g.fillRect(tick * 10, pitch * 10, 10, 10);
        
        g.drawRect(tick * 10, pitch * 10, 10, 10);
        
      }
    }
    
  }
  
}



