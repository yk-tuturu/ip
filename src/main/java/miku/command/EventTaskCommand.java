package miku.command;

import java.time.LocalDateTime;
import java.util.Map;

import miku.exceptions.FileIOError;
import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.EventTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UiHandler;
import miku.util.Constants;
import miku.util.DateTimeParser;


/**
 * Command that creates and adds an {@link EventTask} with a start and end time.
 */
public class EventTaskCommand extends Command {

    /**
     * Creates a new event command with keyword {@code "event"}.
     */
    public EventTaskCommand() {
        super("event", "event <task> /from <time> /to <time>");
    }

    /**
     * Adds an event task to the task list.
     * <p>
     * Requires:
     * <ul>
     *   <li>{@code default} – the task description</li>
     *   <li>{@code from} – the start time</li>
     *   <li>{@code to} – the end time</li>
     * </ul>
     * If any of these arguments are missing or the times cannot be parsed, an
     * {@link IllegalCommandException} is thrown.
     * </p>
     *
     * @param arg      arguments containing the task description and time range
     * @param tasks    the task list
     * @param saveData manager for saving tasks
     * @param ui       UI handler (not used here)
     * @return a message confirming the added event
     * @throws IllegalCommandException if required arguments are missing or invalid
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Miku cannot add an empty task :(", this.getUsage());
        }

        if (!arg.containsKey("from")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.getUsage());
        }

        if (!arg.containsKey("to")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.getUsage());
        }

        LocalDateTime from;
        LocalDateTime to;
        try {
            from = DateTimeParser.parse(arg.get("from"));
            to = DateTimeParser.parse(arg.get("to"));
        } catch (IllegalArgumentException e) {
            throw new IllegalCommandException("Date must be formatted correctly!");
        }

        Task task = new EventTask(arg.get("default"), from, to);

        // tries to write to save file first, if fail abort the whole thing
        try {
            saveData.write(task);
        } catch (FileIOError e) {
            return e.getMessage();
        }

        tasks.add(task);

        int len = tasks.getLength();

        return String.format("Miku has added this task to your list!\n"
                        + "%s%s\n"
                        + "You now have %d task%s in your list",
                Constants.INDENT, task, len, len > 1 ? "s" : "");
    }
}
