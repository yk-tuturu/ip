package miku.command;

import java.util.Map;

import miku.exceptions.FileIoError;
import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.FixedDurationTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UiHandler;
import miku.util.Constants;

/**
 * A task that needs to be done in a certain duration
 */
public class FixedDurationTaskCommand extends Command {
    public FixedDurationTaskCommand() {
        super("duration", "duration <task> /in <time>");
    }

    /**
     * Runs the command to add a deadline task. Does not add the task if there are any errors
     * @param arg the arguments for the command, if any.
     *            The given task name is passed into 'default' and the deadline is stored under 'by'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return a string declaring success or failure on addition
     * @throws IllegalCommandException if bad argument formatting (eg bad dates, empty task, etc)
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Miku cannot add an empty task :(", this.getUsage());
        }

        if (!arg.containsKey("in")) {
            throw new IllegalCommandException("Duration cannot be empty :(", this.getUsage());
        }

        String duration = arg.get("in");

        Task task = new FixedDurationTask(arg.get("default"), duration);
        // tries to write to save file first, if fail abort the whole thing
        try {
            saveData.write(task);
        } catch (FileIoError e) {
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
