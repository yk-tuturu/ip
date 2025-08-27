package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.DeadlineTask;
import miku.tasks.EventTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

public class DeleteTaskCommand extends Command {
    public DeleteTaskCommand() {
        super("delete", "delete <index>");
    }

    @Override
    public void Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
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
        String output = String.format("Miku has deleted the task:\n    %s\nNow you have %d task%s remaining",
                task, len, len != 1 ? "s" : "");
        ui.Print(output);
    }
}
