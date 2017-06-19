package cs3500.music.view;

import cs3500.music.model.JMidiComposition;

/**
 * The class {@CompositeView} Creates an Audio Visual view of the composition.
 */
public class CompositeView implements ICompositionView {
  
  JMidiComposition composition;
  MusicEditorGUI visual;
  MidiViewImpl audio;
  
  @Override public void initialize(JMidiComposition composition, Appendable ap) {
  
    this.composition = composition;
  
    this.visual = new MusicEditorGUI();
  
    this.audio = new MidiViewImpl();
  
    this.visual.initialize(composition, ap);
  
    this.audio.initialize(composition, ap);
 

  }

  
}
