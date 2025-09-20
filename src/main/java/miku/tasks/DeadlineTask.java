package miku.tasks;

import java.time.LocalDateTime;

import miku.util.DateTimeParser;

/**
 * Represents a task with a specific deadline.
 */
public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    /**
     * Creates a new deadline task.
     *
     * @param value    the task description (required)
     * @param deadline the deadline date and time (required)
     */
    public DeadlineTask(String value, LocalDateTime deadline) {
        super(value);
        this.deadline = deadline;
    }

    /**
     * Creates a new deadline task, but with specification on whether it's done
     * @param value the task description
     * @param deadline the deadline date and time
     * @param isDone whether the task is done or not
     */
    public DeadlineTask(String value, LocalDateTime deadline, boolean isDone) {
        super(value, isDone);
        this.deadline = deadline;
    }

    /**
     * Returns a user-readable string representation of the deadline task. Used when user calls the list command
     *
     * @return a string in the format "[D][status] task description (by: deadline)"
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", DateTimeParser.getDateString(deadline));
    }

    /**
     * Returns a string representation for saving to the save file
     *
     * @return a string in the format "D|status|description|deadline"
     */
    @Override
    public String getSaveString() {
        return "D|" + super.getSaveString() + "|" + DateTimeParser.getIsoDate(deadline);
    }
}
