# JMidiSequencer

A simple MIDI Sequencer based on JAVA

----------------------------------------------------

**_FILES:_**

**JAVA DOC for reference in folder: /doc**

**Interface Hierarchy**

cs3500.music.model.IjMidiCompositionExtended

cs3500.music.model.IjMidiEvent

cs3500.music.model.IjMidiTrack

cs3500.music.model.IjVirtualInstrument


**Class Hierarchy**

cs3500.music.model.JMidiCompositionExtended (implements cs3500.music.model.IjMidiCompositionExtended)

cs3500.music.model.JMidiEvent (implements cs3500.music.model.IjMidiEvent)

cs3500.music.model.JMidiTrack (implements cs3500.music.model.IjMidiTrack)

cs3500.music.model.JTimeSignature

cs3500.music.model.JVirtualInstrument (implements cs3500.music.model.IjVirtualInstrument)

----------------------------------------------------

**_DESCRIPTIONS:_**

**cs3500.music.model.JMidiCompositionExtended**

The core container of the composition. 
Represents a collection of MIDI Tracks that can be played in sync.
Is mainly responsible of synchronizing all the tracks together and set time changes.

**cs3500.music.model.JMidiTrack**

Where most of the work go and the assignment visualization example is.
This class collects all the JMidiEvents (Notes) and manages them for them to be
scrollable and ready for the cs3500.music.model.JMidiCompositionExtended.

**cs3500.music.model.JMidiEvent**

A simple class that represents a MIDI message (note), makes sure 
the message is ready to be analyzed by the cs3500.music.model.IjMidiTrack.

**cs3500.music.model.JVirtualInstrument**

A simple class that represents a MIDI Instrument. It basically sets how the events 
are going to be represented from the track to the user in a mor musical way 
(defines scale degrees, intervals, etc...). This is used by cs3500.music.model.JMidiTrack

**cs3500.music.model.JTimeSignature**

Represents how many beats (pulses) are to be contained in each bar
and which note value is to be given one beat in a musical composition.
This is used by cs3500.music.model.JMidiCompositionExtended.



