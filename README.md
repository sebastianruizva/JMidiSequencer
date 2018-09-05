# JMidiSequencer Beta

Multi-track MIDI Sequencer based on JAVA 9 using the MVC architecture. Developed alongside Joseph Annis, jMidiSequencer was a great playground to learn about audio processing with Java. It supports several features such as a terminal and GUI views, the ability to import, play, edit and export multi-track midi files, real-time visualization, practice mode and more.

**JAVA DOC for reference in folder: /doc**

###########################

**MODEL**

###########################


**JMidiComposition**

The core container of the composition. 
Represents a collection of MIDI Tracks that can be played in sync.
Is mainly responsible of synchronizing all the tracks together and set time changes.

**JMidiTrack**

Where most of the work go and the assignment visualization example is.
This class collects all the JMidiEvents (Notes) and manages them for them to be
scrollable and ready for the JMidiComposition.

**JMidiEvent**

A simple class that represents a MIDI message (note), makes sure 
the message is ready to be analyzed by the IjMidiTrack.

**JVirtualInstrument**

A simple class that represents a MIDI Instrument. It basically sets how the events 
are going to be represented from the track to the user in a mor musical way 
(defines scale degrees, intervals, etc...). This is used by JMidiTrack

###########################

**CONTROLLER**

###########################

Note: composition loops automatically so if you press R,
make sure is not playing or will go back to the beginning automatically.

Keys:
 
 <-  :move cursor to the left, 
 ->  :move cursor to the Right
 S   :Stop
 P   :Play
 B   :go to Start of composition
 E   :go to the end of the composition   
 X   : export midi file to root folder

**MainController**

Takes care of the initial command line interactions.

**CompositionController**

Takes care of the initial command line interactions.

**VisualController**

Takes care of the user interactions with the visual view only.

Keys: <- move cursor to the left, ->  move cursor to the Right

**AudioController**

Takes care of user interactions in the midi only view.

**CompositeController**

Takes care of user interactions in the Composite view.

###########################

**VIEW**

###########################

**ConsoleView** 

Renders a console audio of the composition.

**DrawValues**

Holds constants for a unified visual render between classes.

**AudioView** 

Renders an audio view of the composition and handles playback events of it.

**ViewManager**

This is a factory class for views that allows the client to easily select between a console version
of the output and the more complex GUI. 

**ICompositionView**

A unified typing for views relating to the music editor. As what and how different aspects of a 
composition should be displayed is not totally concrete, this only contains one method, initialize.
rather than a series of methods dictating what should be included in the audio.

**controller.visual**

A group of classes responsible for displaying all the necessary interfaces related to a 
JMidiComposition. This includes a ledger containing every note in the composition, arranged horizontally, with each
row matching a pitch within the composition's range. A ten-octave keyboard is also displayed,
and any selected notes are illuminated. 

**CompositeView**

An extension of the audio view, that syncs with a visual view.
