package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;

import java.util.Map;

public class DeleteTaskCommand extends Command {
    public DeleteTaskCommand() {
        super("delete", "delete <index>");
    }

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
