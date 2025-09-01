package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;

import java.util.Map;

/**
 * Command that deletes a task from the task list by its index.
 */
public class DeleteTaskCommand extends Command {

    /**
     * Creates a new delete command with keyword {@code "delete"}.
     */
    public DeleteTaskCommand() {
        super("delete", "delete <index>");
    }

    /**
     * Deletes the task at the given index.
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
     * @return a message confirming deletion
     * @throws IllegalCommandException if the index is missing, invalid, or out of bounds
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Parsing index number failed :(", this.usage);
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

        Task task = tasks.Delete(index);
        int len = tasks.GetLength();
        String output = String.format("Miku has deleted the task:\n" +
                        "%s%s\n" +
                        "Now you have %d task%s remaining",
                Constants.INDENT, task, len, len != 1 ? "s" : "");

        saveData.writeListToFile(tasks);

        return output;
    }
}
