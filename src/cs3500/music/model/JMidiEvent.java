package cs3500.music.model;

/**
 * The class {@JMidiEvent} Represents a midi event inside a MIDI track. This events usually
 * represent NOTES but sometimes will also represent other events in the composition, such as
 * not-NOTE related events like percussive instructions. For further expansion once the
 * keyboard view is finished, I decided to keep the technical term.
 * - The J prefix is used to avoid overriding the java midi class MidiEvent.
 */
public class JMidiEvent implements IjMidiEvent {

  /**
   * tick     represents the starting tick of the midi event.
   */
  private final int tick;

  /**
   * pitch    represents the pitch of the event represents (later defined as a note depending.
   */
  private final int pitch;
  /**
   * duration represents how long the key or trigger was held.
   */
  private final int duration;
  /**
   * velocity represents how 'strong' the key or trigger was pressed.
   */
  private final int velocity;
  /**
   * channel  represents where the midi message is coming from or where is supposed to head.
   * at
   */
  private final int channel;


  /**
   * Constructs a MIDI Event.
   *
   * @param tick     represents the starting tick of the midi event
   * @param pitch    represents the pitch of the event represents (later defined as a note depending
   *                 on the scale of the {@JVirtualInstrument})
   * @param channel  represents where the midi message is coming from or where is supposed to head
   *                 at
   * @param duration represents how long the key or trigger was held
   * @param velocity represents how 'strong' the key or trigger was pressed
   */
  private JMidiEvent(int tick, int pitch, int velocity, int channel, int duration) {
    this.tick = tick;
    this.pitch = pitch;
    this.velocity = velocity;
    this.channel = channel;
    this.duration = duration;
  }

  /**
   * starts a builder for its construction.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Retrieves the tick of the event.
   * - A 'Tick' the standard measure we will use to store note changes, this changes will then be
   * synchronized with the actual tempo of a composition. A tick is fixed to represent 120 BPM.
   */
  @Override
  public int getTick() {
    return tick;
  }

  /**
   * Retrieves the absolute pitch of the event. This pitch doe not take the octave ina count,
   * this will be handled by the assigned virtual instrument in the track.
   */
  @Override
  public int getPitch() {
    return pitch;
  }

  /**
   * Retrieves the velocity of the event.
   */
  @Override
  public int getVelocity() {
    return velocity;
  }

  /**
   * Retrieves the channel of the event.
   */
  @Override
  public int getChannel() {
    return channel;
  }

  /**
   * Retrieves the duration of the event.
   */
  @Override
  public int getDuration() {
    return duration;
  }

  /**
   * Returns a string representation of the event.
   */
  @Override
  public String toString() {
    return "cs3500.music.model.JMidiEvent{" + "tick=" + tick + ", pitch=" + pitch + ", velocity="
            + velocity + ", channel=" + channel + ", duration=" + duration + '}';
  }

  /**
   * the class {@Builder} Builds a cs3500.music.model.JMidiEvent according to the default values or
   * the ones directed by the user.
   */
  public static final class Builder {

    private int tick = 0;
    private int pitch = 0;
    private int velocity = 64;
    private int channel = 0;
    private int duration = 1;

    /**
     * Assigns the same values of other Midi Event.
     *
     * @param event the event used as a template
     */
    public Builder clone(JMidiEvent event) throws IllegalArgumentException {

      if (event == null) {
        throw new IllegalArgumentException("only positive values are allowed!");
      }

      this.tick = event.getTick();
      this.pitch = event.getPitch();
      this.velocity = event.getVelocity();
      this.channel = event.getChannel();
      this.duration = event.getDuration();

      return this;

    }

    /**
     * Assigns the tick the midi message is located at.
     *
     * @param tick of the midi message (position in time)
     */
    public Builder tick(int tick) throws IllegalArgumentException {

      if (tick < 0) {
        throw new IllegalArgumentException("only positive values are allowed!");
      }

      this.tick = tick;

      return this;

    }

    /**
     * Sets the pitch the event is going to.
     *
     * @param semitoneValue its semitone value beginning from C = 0, C# = 1 ... B = 12, C = 13
     */
    public Builder pitch(int semitoneValue) throws IllegalArgumentException {

      if (semitoneValue < 0 || semitoneValue > 127) {
        throw new IllegalArgumentException("only positive values are allowed!");
      }

      this.pitch = semitoneValue;

      return this;

    }

    /**
     * Assigns velocity of the midi message (how hard the message was triggered).
     *
     * @param velocity the octave the block is
     */
    public Builder velocity(int velocity) throws IllegalArgumentException {

      if (velocity < 0 || velocity > 127) {
        throw new IllegalArgumentException("Midi messages only handle values from 0 to 127!");
      }

      this.velocity = velocity;

      return this;

    }

    /**
     * Assigns channel of the midi message.
     *
     * @param channel of the midi message
     */
    public Builder channel(int channel) throws IllegalArgumentException {

      if (velocity < 0 || velocity > 127) {
        throw new IllegalArgumentException("Midi messages only handle values from 0 to 127!");
      }

      this.channel = channel;

      return this;

    }


    /**
     * Assigns duration of the midi message from 1 to inf...
     *
     * @param duration of the midi message
     */
    public Builder duration(int duration) throws IllegalArgumentException {

      if (duration < 1) {
        throw new IllegalArgumentException("duration must at least be 1");
      }

      this.duration = duration;

      return this;

    }

    /**
     * Returns the final midi event.
     */
    public JMidiEvent build() {

      return new JMidiEvent(tick, pitch, velocity, channel, duration);

    }

  }

}
