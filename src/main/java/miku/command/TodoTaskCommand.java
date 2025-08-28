package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UIHandler;
import miku.util.Constants;

import java.util.Map;

public class TodoTaskCommand extends Command {
    public TodoTaskCommand() {
        super("todo", "todo <task>");
    }

    @Override
    public String Run(Map<String, String> args, TaskList tasks, SaveDataManager saveData, UIHandler ui)
            throws IllegalCommandException {
        if (args.get("default") == null) {
            throw new IllegalCommandException("Miku cannot add an empty task!");
        }

        TodoTask task = new TodoTask(args.get("default"));

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
