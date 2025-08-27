package miku.tasks;

import miku.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DeadlineTask extends Task {
    private LocalDateTime deadline;

    public DeadlineTask(String value, LocalDateTime deadline) {
        super(value);
        this.deadline = deadline;
    }

    public DeadlineTask(String value, LocalDateTime deadline, boolean done) {
        super(value, done);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", DateTimeParser.getDateString(deadline));
    }

    @Override
    public String getSaveString() {
        return "D|" + super.getSaveString() + "|" + DateTimeParser.getDateString(deadline);
    }
}
