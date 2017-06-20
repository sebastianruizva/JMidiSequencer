# JMidiSequencer

A simple MIDI Sequencer based on JAVA

**JAVA DOC for reference in folder: /doc**

**MODEL RELATED CLASSES, INTERFACES AND DESCRIPTIONS:**

----------------------------------------------------

**Interface Hierarchy**

cs3500.music.model.IjMidiComposition

cs3500.music.model.IjMidiEvent

cs3500.music.model.IjMidiTrack

cs3500.music.model.IjVirtualInstrument


**Class Hierarchy**

cs3500.music.model.JMidiComposition (implements cs3500.music.model.IjMidiComposition)

cs3500.music.model.JMidiEvent (implements cs3500.music.model.IjMidiEvent)

cs3500.music.model.JMidiTrack (implements cs3500.music.model.IjMidiTrack)

cs3500.music.model.JTimeSignature

cs3500.music.model.JVirtualInstrument (implements cs3500.music.model.IjVirtualInstrument)

----------------------------------------------------

**_DESCRIPTIONS:_**

**cs3500.music.model.JMidiComposition**

The core container of the composition. 
Represents a collection of MIDI Tracks that can be played in sync.
Is mainly responsible of synchronizing all the tracks together and set time changes.

**cs3500.music.model.JMidiTrack**

Where most of the work go and the assignment visualization example is.
This class collects all the JMidiEvents (Notes) and manages them for them to be
scrollable and ready for the cs3500.music.model.JMidiComposition.

**cs3500.music.model.JMidiEvent**

A simple class that represents a MIDI message (note), makes sure 
the message is ready to be analyzed by the cs3500.music.model.IjMidiTrack.

**cs3500.music.model.JVirtualInstrument**

A simple class that represents a MIDI Instrument. It basically sets how the events 
are going to be represented from the track to the user in a mor musical way 
(defines scale degrees, intervals, etc...). This is used by cs3500.music.model.JMidiTrack

**Modifications from last assignment**
Removal of TimeSignature class (we decided to leave everything in 4/4 as the assignment suggested
 and not support dynamic time signatures in this release).
 
Removal of TimeSignature and Tempo change variables and methods from JMidiComposition (Smae 
reason as above).

Because JMidiTrack actually handles all the requirements of the assignment, in order to support 
multiple instruments in different tracks we decided to use JMidiComposition as the adapter 
between the MusicReader by implementing the builder on it and at the same time use it to group 
several tracks.


**CONTROLLER RELATED CLASSES, INTERFACES AND DESCRIPTIONS:**

----------------------------------------------------

**Interface Hierarchy**

cs3500.music.controller.ICmdController

**Class Hierarchy**

cs3500.music.controller.MainController (implements cs3500.music.controller.MainController)


----------------------------------------------------

**_DESCRIPTIONS:_**

**cs3500.music.controller.ICmdController**

Takes care of the command line interactions.



**VIEW RELATED CLASSES, INTERFACES AND DESCRIPTIONS:**

----------------------------------------------------

**Class Hierarchy**

cs3500.music.view.ConsoleView (implements cs3500.music.view.ICompositionView)

cs3500.music.view.DrawValues

cs3500.music.view.AudioView (implements cs3500.music.view.ICompositionView)

cs3500.music.view.ViewManager


**Interface Hierarchy**

cs3500.music.view.ICompositionView

----------------------------------------------------

**_DESCRIPTIONS:_**

**cs3500.music.view.ConsoleView** 

Renders a console view of the composition

**cs3500.music.view.DrawValues**

Holds constants for a unified visual render between classes

**cs3500.music.view.AudioView** 

Renders an audio view of the composition

**cs3500.music.view.ViewManager**

This is a factory class for views that allows the client to easily select between a console version
of the output and the more complex GUI. 

**cs3500.music.view.ICompositionView**

A unified typing for views relating to the music editor. As what and how different aspects of a 
composition should be displayed is not totally concrete, this only contains one method, initialize.
rather than a series of methods dictating what should be included in the view.

**cs3500.music.view.MusicEditorGUI**

A class responsible for displaying all the necessary interfaces related to a JMidiComposition. 
This includes a ledger containing every note in the composition, arranged horizontally, with each
row matching a pitch within the composition's range. A ten-octave keyboard is also displayed,
and any selected notes are illuminated. 

