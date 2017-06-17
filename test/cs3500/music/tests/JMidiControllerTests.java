package cs3500.music.tests;

import org.junit.Test;

import java.io.StringReader;

import cs3500.music.controller.CmdController;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.ICompositionView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.MusicEditorGUI;
import cs3500.music.view.ViewSelector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The class {@JMidiControllerTests} represents tests for the Jmidi controller.
 */
public class JMidiControllerTests {
  
  CmdController controller;
  
  Readable rd;
  
  Appendable ap;
  
  /**
   * Initial conditions for testing.
   */
  public void initCond() {
    
    ap = new StringBuffer();
    
  }
  
  /**
   * ***************
   * TESTS BEGIN.
   * **************
   */
  
  //test StartGame Not Shuffled.
  @Test public void testController_NoInput() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its " + "extension\n");
    
  }
  
  //test StartGame Not Shuffled.
  @Test public void testController_CantFindFile() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("fakefile.tft");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its " + "extension\n"
                    + "java.io.FileNotFoundException: fakefile.tft (No such file or directory)\n");
    
  }
  
  //test StartGame Not Shuffled.
  @Test public void testController_GoodFile() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its " + "extension\n"
                    + "console, visual or MIDI?\n");
    
  }
  
  //test StartGame Not Shuffled.
  @Test public void testController_ViewNonExistent() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt\n" + "modi");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its " + "extension\n"
                    + "console, visual or MIDI?\n"
                    + "java.lang.IllegalArgumentException: there is no view with that name...\n");
    
  }
  
  
  //test StartGame Not Shuffled.
  @Test public void testController_ViewMidi() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt\n" + "midi");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its extension\n"
                    + "console, visual or MIDI?\n" + "MIDI view Initialized\n"
                    + "Playing Track #0\n" + "Write Q to quit\n");
    
  }
  
  //test StartGame Not Shuffled.
  @Test public void testController_ViewConsole() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt\n" + "console");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its extension\n"
                    + "console, visual or MIDI?\n" + "Console view initialized\n"
                    + "       C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E    F   F#    G   G#    A   A#    B    C   C#    D   D#    E  \n"
                    + "    1                      X                                                                                                                                                                                                                                                                                                                                                                          \n"
                    + "    2                      |                                                                                                                                                                                                                                                                                                                                                                       X  \n"
                    + "    3                                                                                                                                                                                                                                                                                                                                                                                              |  \n"
                    + "    4                                                                                                                                                                                                                                                                                                                                                                                              |  \n"
                    + "    5                                                                                                                                                                                                                                                                                                                                                                                              |  \n"
                    + "    6                                                                                                                                                                                                                                                                                                                                                                                              |  \n"
                    + "    7                                                                                                                                                                                                                                                                                                                                                                                              |  \n"
                    + "Write Q to quit\n");
    
  }
  
  //test StartGame Not Shuffled.
  @Test public void testController_ViewGui() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt\n" + "visual");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its extension\n"
                    + "console, visual or MIDI?\n" + "Visual view Initialized!\n"
                    + "Write Q to quit\n");
    
  }
  
  @Test public void testController_QuitAfterSelection() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("singleChannel.txt\n" + "Q");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its extension\n"
                    + "console, visual or MIDI?\n" + "bye!\n");
    
  }
  
  @Test public void testController_QuitInStart() throws Exception {
    this.initCond();
    
    //Simulate user input
    rd = new StringReader("Q");
    
    controller = new CmdController(rd, ap);
    
    controller.run();
    
    assertEquals(ap.toString(),
            "please write the name of the file you want to open and its extension\n"
                     + "bye!\n");
    
  }
  
  
}
