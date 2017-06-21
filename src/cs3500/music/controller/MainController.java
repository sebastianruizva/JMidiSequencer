package cs3500.music.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;
import cs3500.music.util.MusicReader;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.ViewManager;

/**
 * The class {@ModeSelector} Creates a view according to the user instructions.
 */
public class MainController {

  final Readable rd;
  final Appendable ap;
  private String fileName;
  private Scanner scanner;
  private JMidiComposition composition;
  private ICompositionView selected;
  
  /**
   * Constructs a {@code MainController} object.
   *
   * @param rd takes user input
   * @param ap transmits output
   * @throws IllegalStateException if the controller has not been initialized properly to receive
   *                               input and transmit output.
   */
  public MainController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalStateException("readable can't be null!");
    }
    if (ap == null) {
      throw new IllegalStateException("Appendable can't be null!");
    }
    this.rd = rd;
    this.ap = ap;
    this.fileName = null;
    this.composition = null;
    this.scanner = new Scanner(rd);
    this.selected = null;
  }

  /**
   * Interacts with the user.
   */
  public void run() {
  
    JMidiUtils.message("please write the name of the file you want to open and its extension or "
            + "write Q to quit", ap);

    while (scanner.hasNextLine()) {

      String next = scanner.nextLine();

      if (next.equalsIgnoreCase("Q")) {

        JMidiUtils.message("bye!", ap);
        return;

      } else if (fileName == null) {

        fileName = next;

        try {

          composition = MusicReader.parseFile(new FileReader(fileName), JMidiComposition.builder());
  
          JMidiUtils.message("console, gui, composite or MIDI?", ap);

        } catch (IOException | IllegalArgumentException e) {

          fileName = null;
  
          JMidiUtils.message(e.toString(), ap);

        }

      } else if (selected == null) {

        try {
  
          selected = ViewManager.select(next, composition, ap);
          selected.initialize();

        } catch (IllegalArgumentException e) {
  
          selected = null;
          JMidiUtils.message(e.toString(), ap);

        }

      }

    }
    
  }

}
