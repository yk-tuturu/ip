package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.IllegalSaveException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UIHandler;

import java.util.Map;

public class TodoTaskCommand extends Command {
    public TodoTaskCommand() {
        super("todo", "todo <task>");
    }

    @Override
    public void Run(Map<String, String> args, TaskList tasks, SaveDataManager saveData, UIHandler ui)
            throws IllegalCommandException {
        if (args.get("default") == null) {
            throw new IllegalCommandException("Miku cannot add an empty task!");
        }

        TodoTask task = new TodoTask(args.get("default"));

        // tries to write to save file first, if fail abort the whole thing
        try {
            saveData.Write(task);
        } catch (IllegalSaveException e) {
            ui.Print(e.getMessage());
            return;
        }

        tasks.Add(task);

        int len = tasks.GetLength();
        ui.Print(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }
}
