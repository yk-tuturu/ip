package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.EventTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;
import miku.util.DateTimeParser;

import java.time.LocalDateTime;
import java.util.Map;

public class EventTaskCommand extends Command {
    public EventTaskCommand() {
        super("event", "event <task> /from <time> /to <time>");
    }

    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Miku cannot add an empty task :(", this.usage);
        }

        if (!arg.containsKey("from")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.usage);
        }

        if (!arg.containsKey("to")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.usage);
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

        tasks.Add(task);

        int len = tasks.GetLength();

        return String.format("Miku has added this task to your list!\n" +
                        "%s%s\n" +
                        "You now have %d task%s in your list",
                Constants.INDENT, task, len, len > 1 ? "s" : "");
    }
}
