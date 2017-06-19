package cs3500.music.controller;

import java.io.FileNotFoundException;

import javax.sound.midi.MidiUnavailableException;

/**
 * The class {@ICmdController} Represents a commandline controller class interface.
 */
public interface ICmdController {

  /**
   * Runs the interpreter.
   */
  void run() throws MidiUnavailableException, FileNotFoundException;
}
