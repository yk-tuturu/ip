package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UIHandler;

public class TodoTaskCommand extends Command {
    public TodoTaskCommand() {
        super("todo", "todo <task>");
    }

    @Override
    public void Run(String arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (arg.isEmpty()) {
            throw new IllegalCommandException("Miku cannot add an empty task!");
        }

        TodoTask task = new TodoTask(arg);
        tasks.Add(task);

        int len = tasks.GetLength();
        ui.Print(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }
}
