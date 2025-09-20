package miku.tasks;

import java.time.LocalDateTime;

import miku.util.DateTimeParser;

/**
 * Represents a task that occurs within a specific time range.
 */
public class EventTask extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates a new event task.
     *
     * @param value the task description (required)
     * @param from  the start date and time (required)
     * @param to    the end date and time (required)
     */
    public EventTask(String value, LocalDateTime from, LocalDateTime to) {
        super(value);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a new event task with completion status.
     *
     * @param value the task description (required)
     * @param from  the start date and time (required)
     * @param to    the end date and time (required)
     * @param isDone  whether the task is completed
     */
    public EventTask(String value, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(value, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event task. Used when user calls the list command
     *
     * @return a string in the format "[E][status] task description (from: start to: end)"
     */
    @Override
    public String toString() {
        String fromString = DateTimeParser.getDateString(from);
        String toString = DateTimeParser.getDateString(to);
        return String.format("[E]%s (from: %s to: %s)", super.toString(), fromString, toString);
    }

    /**
     * Returns a string representation to be saved to the save file
     *
     * @return a string in the format "E|status|description|from|to"
     */
    @Override
    public String getSaveString() {
        String fromString = DateTimeParser.getIsoDate(from);
        String toString = DateTimeParser.getIsoDate(to);
        return "E|" + super.getSaveString() + "|" + fromString + "|" + toString;
    }

    /**
     * Returns the datetime object for "from"
     *
     * @return a LocalDateTime object
     */
    public LocalDateTime getFrom() {
        return this.from;
    }

    /**
     * Returns the datetime object for "to"
     *
     * @return a LocalDateTime object
     */
    public LocalDateTime getTo() {
        return this.to;
    }
}
