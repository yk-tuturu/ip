package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.util.Constants;

import java.util.Map;

public class MarkCommand extends Command {
    public MarkCommand() {
        super("mark", "mark <index>");
    }

    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        // not error handling here pains me but we'll save that for a future commit
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

        tasks.MarkTask(index);

        String output = "Omedetou! You've finished a task:\n" + Constants.INDENT + tasks.Get(index);

        saveData.writeListToFile(tasks);

        return output;
    }
}
