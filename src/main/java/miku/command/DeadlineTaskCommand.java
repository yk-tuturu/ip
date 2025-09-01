package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.DeadlineTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;
import miku.util.DateTimeParser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Inserts a task with a deadline to the task list. Usage: deadline (task) /by (deadline)
 * Deadline given must be a valid date
 */
public class DeadlineTaskCommand extends Command {
    public DeadlineTaskCommand() {
        super("deadline", "deadline <task> /by <time>");
    }

    /**
     * Runs the command to add a deadline task. Does not add the task if there are any errors
     * @param arg the arguments for the command, if any. The given task name is passed into 'default' and the deadline is stored under 'by'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return a string declaring success or failure on addition
     * @throws IllegalCommandException if bad argument formatting (eg bad dates, empty task, etc)
     */
    @Override
    public String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
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
