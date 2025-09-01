package miku.tasks;

import miku.util.DateTimeParser;

import java.time.LocalDateTime;

public class EventTask extends Task {
    public LocalDateTime from;
    public LocalDateTime to;

    public EventTask(String value, LocalDateTime from, LocalDateTime to) {
        super(value);
        this.from = from;
        this.to = to;
    }

    public EventTask(String value, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(value, isDone);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        String fromString = DateTimeParser.getDateString(from);
        String toString = DateTimeParser.getDateString(to);
        return String.format("[E]%s (from: %s to: %s)", super.toString(), fromString, toString);
    }

    @Override
    public String getSaveString() {
        String fromString = DateTimeParser.getDateString(from);
        String toString = DateTimeParser.getDateString(to);
        return "E|" + super.getSaveString() + "|" + fromString + "|" + toString;
    }
}
