package jMidiSequencerBeta.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * The class {@JMidiTrack} Represents a collection of related Midi Events organized in a grid.
 */
public class JMidiTrack {

  /**
   * grid represents a collection of JMidiEvents organized by pitch and tick.
   */
  private HashMap<Integer, HashMap<Integer, JMidiEvent>> grid;

  /**
   * instrument represents the assigned Virtual Instrument.
   */
  private JVirtualInstrument instrument;

  /**
   * maxTick represents the Maximum tick in the track.
   * - A 'Tick' the standard measure we will use to store note changes, this changes will then be
   * synchronized with the actual tempo of a composition. A tick is fixed to represent 120 BPM.
   */
  private int maxTick;

  /**
   * maxPitch represents the Maximum pitch in the track.
   */
  private int maxPitch;

  /**
   * Constructs a {@JMidiTrack} for the specified instrument.
   *
   * @param instrument represents the assigned Virtual Instrument.
   */
  public JMidiTrack(JVirtualInstrument instrument) {
    if (instrument == null) {
      throw new IllegalArgumentException("instrument cant be null!");
    }
    this.instrument = instrument;
    this.grid = new HashMap();
    this.maxPitch = 0;
    this.maxTick = 0;
  }

  /**
   * Gets the VI of the track.
   */
  public JVirtualInstrument getInstrument() {

    return instrument;

  }

  /**
   * sets the track's virtual instrument.
   *
   * @param instrument represents the assigned Virtual Instrument.
   */
  protected void setInstrument(JVirtualInstrument instrument) {

    this.instrument = instrument;

  }

  /**
   * Moves a MIDI event in a midi track.
   *
   * @param event    the event you want to update.
   * @param newPitch the pitch you want to update to.
   * @param newTick  the tick you want to update to.
   * @throws IllegalArgumentException if it collides with other in the region or there is no such
   *                                  event.
   */
  protected void moveEvent(JMidiEvent event, int newPitch, int newTick)
          throws IllegalArgumentException {

    if (event == null) {
      throw new IllegalArgumentException("event cant be null!");
    }

    this.editEvent(event, newPitch, newTick, event.getDuration(),
            event.getVelocity(), event.getChannel());

  }

  /**
   * Edits a MIDI event in a midi track.
   *
   * @param event       the event you want to update.
   * @param newTick     the tick you want to update to.
   * @param newDuration the pitch you want to update to.
   * @param newVelocity the belocity you want to update to.
   * @throws IllegalArgumentException if it collides with others in the region or there is no such
   *                                  event.
   */
  protected void editEvent(JMidiEvent event, int newPitch, int newTick, int newDuration,
                           int newVelocity, int newChannel) throws IllegalArgumentException {

    //remove from the grid to simulate the actual new state of the grid
    this.removeEvent(event);

    //check if there is space for it
    if (!this.available(newTick, newPitch, newDuration)) {

      //put back to its original position
      this.addEvent(event);

      throw new IllegalArgumentException("There is something there already!");
    }

    //Create new event
    JMidiEvent newEvent = JMidiEvent.builder().pitch(newPitch).tick(newTick)
            .duration(newDuration).velocity(newVelocity).channel(newChannel).build();

    //Add the new event
    this.addEvent(newEvent);

    //update canvas
    this.updateMaxValues();

  }

  /**
   * Adds a midi event to the region.
   *
   * @param event the event you want to add.
   * @throws IllegalArgumentException if there it collides with other in the region.
   */
  protected void addEvent(JMidiEvent event) throws IllegalArgumentException {

    //invalid when null
    if (event == null) {
      throw new IllegalArgumentException("event cant be null!");
    }

    //retrieve data of the block for positioning
    int pitch = event.getPitch();
    int tick = event.getTick();

    //determine if there is anything in the way
    if (this.available(tick, pitch, event.getDuration())) {
      //assign the appropriate sectors
      for (int i = 0; i < event.getDuration(); i++) {
      
        HashMap<Integer, JMidiEvent> sector = grid.getOrDefault(tick + i, new HashMap<>());
      
        sector.put(pitch, event);
      
        grid.put(tick + i, sector);
      
      }
    }

    //update canvas
    this.updateMaxValues();

  }

  /**
   * Removes al the occurrences of a MIDI event in a midi track.
   *
   * @param event The event you want to remove.
   * @throws IllegalArgumentException if there is no such event.
   */
  protected void removeEvent(JMidiEvent event) throws IllegalArgumentException {

    //Check for null
    if (event == null) {
      throw new IllegalArgumentException("event cant be null!");
    }

    ArrayList<Integer> sectors = getTicksOfEvent(event);

    //Check for not found
    if (sectors.isEmpty()) {
      throw new IllegalArgumentException("There is no such event!");
    }

    //remove the related sectors
    for (int i : sectors) {

      grid.get(i).remove(event.getPitch());

      //if row is empty remove row as well
      if (grid.get(i).size() == 0) {

        grid.remove(i);

      }

    }

    //update canvas
    this.updateMaxValues();

  }

  /**
   * Creates a list of the ticks a MIDI event is covering from left to right.
   *
   * @param event you want to get the information from.
   * @throws IllegalArgumentException if there is no such event in the region.
   */
  protected ArrayList<Integer> getTicksOfEvent(JMidiEvent event) throws IllegalArgumentException {

    //Check for null
    if (event == null) {
      throw new IllegalArgumentException("event cant be null!");
    }

    //check if exists
    if (this.available(event.getTick(), event.getPitch(), event.getDuration())) {
      throw new IllegalArgumentException("there is no such event in the region!");
    }

    //create the list
    ArrayList<Integer> ticks = new ArrayList<>();

    for (int i = 0; i < event.getDuration(); i++) {

      ticks.add(event.getTick() + i);

    }

    return ticks;

  }

  /**
   * Determines if there is enough free space in the grid on the specified location and distance.
   *
   * @param tick     the tick you ar looking for
   * @param pitch    the pitch you want to verify
   * @param distance how big is the area?
   */
  private boolean available(int tick, int pitch, int distance) throws IllegalArgumentException {

    //invalid negative numbers
    if (tick < 0 || pitch < 0 || distance < 0) {
      throw new IllegalArgumentException("negative values not supported!");
    }
  
    //need to add + 1 to tolerate new triggers beginning in the end beat
    for (int i = 1; i < distance; i++) {

      if (grid.getOrDefault(tick + i, null) != null) {
        if (grid.get(tick + i).getOrDefault(pitch, null) != null) {
          return false;
        }
      }

    }

    return true;

  }

  /**
   * Updates the maxPitch and maxTick of the grid.
   */
  protected void updateMaxValues() {

    //update maxTick
    if (grid.size() == 0) {
      this.maxTick = 0;
    } else {
      this.maxTick = Collections.max(grid.keySet());
    }

    List<Integer> pitches = new ArrayList<>();

    //find all pitches
    for (Integer i : grid.keySet()) {

      pitches.addAll(grid.get(i).keySet());

    }

    //determine the biggest pitch
    if (pitches.size() == 0) {
      this.maxPitch = 0;
    } else {
      this.maxPitch = Collections.max(pitches);
    }

  }
  
  /**
   * Returns a clone of all the different MIDI events on a given point in time.
   *
   * @param tick the tick where the events are.
   */
  public ArrayList<JMidiEvent> getEventsOnTick(int tick) {

    ArrayList<JMidiEvent> events = new ArrayList<JMidiEvent>();

    if (grid.getOrDefault(tick, null) != null) {

      for (Integer pitch : grid.get(tick).keySet()) {

        if (grid.get(tick).getOrDefault(pitch, null) != null) {

          events.add(grid.get(tick).get(pitch));

        }

      }

    }

    return (ArrayList<JMidiEvent>) events.clone();

  }

  /**
   * Returns a string grid with all the element in the region.
   */
  @Override
  public String toString() {

    //if region is empty
    if (grid.size() == 0) {
      return "";
    }

    //determine the actual dimensions of the grid
    int width = (maxPitch + 2) * 5;
    int height = (int) maxTick + 2;

    //build a grid according to its current state
    StringBuilder grid = buildGrid(width, height);

    //Place the elements in the grid
    for (Integer tick : this.grid.keySet()) {
      for (Integer pitch : this.grid.get(tick).keySet()) {
        int index = tick * (width + 1) + (pitch * 5) + 6 + width;
        grid.setCharAt(index + 2, getSectorType(tick, pitch).toString().charAt(0));
      }
    }

    return grid.toString();
  }

  /**
   * Returns the type of sector in an specific position of the grid.
   *
   * @param tick  the tick where the sector is
   * @param pitch the pitch where the sector is
   */
  protected SectorType getSectorType(int tick, int pitch) {

    //if there is nothing
    if (this.available(tick, pitch, 1)) {
      return SectorType.REST;
    }

    JMidiEvent event = getEventOnPosition(tick, pitch);

    //if the events origin is the same then is a head
    if (tick == event.getTick()) {
      return SectorType.HEAD;
    }

    return SectorType.BODY;

  }

  /**
   * Returns a MIDI events on a given point in time and pitch.
   *
   * @param tick  the tick where is supposed to be located at.
   * @param pitch the pitch where is supposed to be located at.
   * @throws IllegalArgumentException if there is no such event.
   */
  protected JMidiEvent getEventOnPosition(int tick, int pitch)
          throws IllegalArgumentException {

    //verify if there is something there
    if (this.available(tick, pitch, 1)) {
      throw new IllegalArgumentException("no event in specified position");
    }

    return grid.get(tick).get(pitch);

  }

  /**
   * Draws a grid with the given dimensions.
   */
  private StringBuilder buildGrid(int width, int height) {

    int length = height * width;
    StringBuilder grid = new StringBuilder(length);
    int i = 0;
    int row = 0;

    while (i < length) {
      if (i >= width && i % (width) == 0) {
        grid.append('\n');
        row++;
      }
      if (i % (width) == 0 && i >= width) {
        grid.append(String.format("%5s", row).substring(0, 5));
        i += 5;
      } else if (i > 4 && width - 4 > i) {
        String note = instrument.getNoteRepresentation((i - 4) / 5);
        grid.append(String.format(" %2s  ", note).substring(0, 5));
        i += 5;
      } else {
        grid.append(" ");
        i++;
      }
    }

    return grid;

  }

  /**
   * Returns a the maximum tick in the track.
   */
  public int getMaxTick() {

    return maxTick;

  }

  /**
   * Returns a the maximum pitch in the track.
   */
  public int getMaxPitch() {

    return maxPitch;

  }

  /**
   * Returns a the maximum pitch in the track.
   */
  protected HashMap<Integer, HashMap<Integer, JMidiEvent>> getGrid() {

    return (HashMap<Integer, HashMap<Integer, JMidiEvent>>) this.grid.clone();
  }

}
