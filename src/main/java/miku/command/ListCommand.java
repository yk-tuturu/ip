package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UIHandler;

import java.util.Map;

/**
 * Command that lists all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Creates a new list command with keyword {@code "list"}.
     */
    public ListCommand() {
        super("list", "list");
    }

    /**
     * Displays all tasks currently in the task list.
     * <p>
     * This command does not require any arguments.
     * </p>
     *
     * @param arg      arguments (ignored for this command)
     * @param tasks    the task list
     * @param saveData manager for saving tasks (not used here)
     * @param ui       UI handler (not used here)
     * @return a formatted string containing all tasks
     * @throws IllegalCommandException never thrown in this command
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.GetLength(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.Get(i)));
        }

        return sb.toString();
    }
}
