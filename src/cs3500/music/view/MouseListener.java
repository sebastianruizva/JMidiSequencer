package cs3500.music.view;

import java.awt.event.MouseEvent;

import cs3500.music.controller.CmdController;

/**
 * Created by joe on 6/19/17.
 */
public class MouseListener implements java.awt.event.MouseListener {
  private MusicEditorGUI gui;

  /*
  Instantiate this mouselistener with the controller.
   private CmdController controller;
    */

  public MouseListener(MusicEditorGUI gui /* CmdController controller*/) {
    this.gui = gui;
   // this.controller = controller;
  }

  @Override
  public void mouseClicked(MouseEvent event) {
    try {
      PianoKey target = gui.getKeyAtPosition(event.getPoint());
      // controller.addKey(target.pitch, gui.getCursorLocation);
    } catch (IllegalArgumentException e) {
      // controller.message("No key at that position.");
    }
  }

  @Override
  public void mouseReleased(MouseEvent event) {
    // Should be empty, but must be overridden.
  }

  @Override
  public void mouseExited(MouseEvent event) {
    // Should be empty, but must be overridden.
  }

  @Override
  public void mouseEntered(MouseEvent event) {
    // Should be empty, but must be overridden.
  }

  @Override
  public void mousePressed(MouseEvent event) {
    // Should be empty, but must be overridden.
  }
}
