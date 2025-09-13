package miku.command;

import java.util.Map;

import miku.exceptions.FileIoError;
import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UiHandler;
import miku.util.Constants;


/**
 * Command that creates and adds a {@link TodoTask}.
 */
public class TodoTaskCommand extends Command {

    /**
     * Creates a new todo command with keyword {@code "todo"}.
     */
    public TodoTaskCommand() {
        super("todo", "todo <task>");
    }

    /**
     * Adds a todo task to the task list.
     * <p>
     * Requires:
     * <ul>
     *   <li>{@code default} – the task description</li>
     * </ul>
     * If the description is missing, an {@link IllegalCommandException} is thrown.
     * </p>
     *
     * @param args     arguments containing the task description
     * @param tasks    the task list
     * @param saveData manager for saving tasks
     * @param ui       UI handler (not used here)
     * @return a message confirming the added todo task
     * @throws IllegalCommandException if the task description is missing
     */
    @Override
    public String run(Map<String, String> args, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException {
        if (args.get("default") == null) {
            throw new IllegalCommandException("Miku cannot add an empty task!", this.getUsage());
        }

        TodoTask task = new TodoTask(args.get("default"));

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
