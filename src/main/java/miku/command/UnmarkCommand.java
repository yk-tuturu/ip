package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;

import java.util.Map;

/**
 * Command that marks a task in the task list as not completed.
 */
public class UnmarkCommand extends Command {

    /**
     * Creates a new unmark command with keyword {@code "unmark"}.
     */
    public UnmarkCommand() {
        super("unmark", "unmark <index>");
    }

    /**
     * Marks the task at the given index as not completed.
     * <p>
     * Requires:
     * <ul>
     *   <li>{@code default} – the task index (1-based)</li>
     * </ul>
     * If the index is missing, cannot be parsed, or is out of bounds, an
     * {@link IllegalCommandException} is thrown.
     * </p>
     *
     * @param arg      arguments containing the task index
     * @param tasks    the task list
     * @param saveData manager for saving the updated task list
     * @param ui       UI handler (not used here)
     * @return a message confirming the task was unmarked
     * @throws IllegalCommandException if the index is missing, invalid, or out of bounds
     */
    @Override
    public String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Parsing index number failed :(");
        }

        int index;
        try {
            index = Integer.parseInt(arg.get("default")) - 1;
        } catch (Exception e) {
            throw new IllegalCommandException("Parsing index number failed :(");
        }

        if (index < 0 || index >= tasks.GetLength()) {
            throw new IllegalCommandException("Index provided is out of bounds :(");
        }

        tasks.UnmarkTask(index);

        String output = "Aw man! You still haven't finished the task:\n" + Constants.INDENT + tasks.Get(index);

        saveData.WriteListToFile(tasks);

        return output;
    }
}