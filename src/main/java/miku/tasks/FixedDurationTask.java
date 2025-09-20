package miku.tasks;

/**
 * Task that needs to take place within a duration, but no start or end date
 */
public class FixedDurationTask extends Task {
    private String duration;

    /**
     * creates a new durational task
     * @param value
     * @param duration
     */
    public FixedDurationTask(String value, String duration) {
        super(value);
        this.duration = duration;
    }

    /**
     * Constructor that takes in a boolean for whether the task is done
     * @param value
     * @param duration
     * @param done
     */
    public FixedDurationTask(String value, String duration, boolean done) {
        super(value, done);
        this.duration = duration;
    }

    /**
     * Returns a user-readable string representation of the deadline task. Used when user calls the list command
     *
     * @return a string in the format "[D][status] task description (by: deadline)"
     */
    @Override
    public String toString() {
        return "[F]" + super.toString() + String.format(" (needs %s)", duration);
    }

    /**
     * Returns a string representation to be saved to the save file
     *
     * @return a string in the format "E|status|description|from|to"
     */
    @Override
    public String getSaveString() {
        return "F|" + super.getSaveString() + "|" + duration;
    }

    /**
     * Getter for the duration
     * @return
     */
    public String getDuration() {
        return duration;
    }

}
