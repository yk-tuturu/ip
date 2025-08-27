package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.DeadlineTask;
import miku.tasks.EventTask;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

public class EventTaskCommand extends Command {
    public EventTaskCommand() {
        super("event", "event <task> /from <time> /to <time>");
    }

    @Override
    public void Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default")) {
            throw new IllegalCommandException("Miku cannot add an empty task :(", this.usage);
        }

        if (!arg.containsKey("from")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.usage);
        }

        if (!arg.containsKey("to")) {
            throw new IllegalCommandException("Time range cannot be empty :(", this.usage);
        }

        Task task = new EventTask(arg.get("default"), arg.get("from"), arg.get("to"));
        tasks.Add(task);

        int len = tasks.GetLength();

        ui.Print(String.format("Miku has added this task to your list!\n    %s\nYou now have %d task%s in your list",
                task, len, len > 1 ? "s" : ""));
    }
}
