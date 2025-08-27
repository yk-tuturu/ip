package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.DeadlineTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class DeadlineTaskCommand extends Command {
    public DeadlineTaskCommand() {
        super("deadline", "deadline <task> /by <time>");
    }

    @Override
    public void Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Miku cannot add an empty task :(", this.usage);
        }

        if (!arg.containsKey("by")) {
            throw new IllegalCommandException("Deadline cannot be empty :(", this.usage);
        }

        String dateString = arg.get("by");
        LocalDateTime date;
        try {
            date = DateTimeParser.parse(dateString);
        } catch (IllegalArgumentException e) {
            throw new IllegalCommandException("Date must be formatted correctly!");
        }

        Task task = new DeadlineTask(arg.get("default"), date);
        // tries to write to save file first, if fail abort the whole thing
        try {
            saveData.Write(task);
        } catch (FileIOError e) {
            ui.Print(e.getMessage());
            return;
        }

        tasks.Add(task);

        int len = tasks.GetLength();

        ui.Print(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }
}
