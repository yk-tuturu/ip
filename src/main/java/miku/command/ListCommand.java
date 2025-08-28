package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.tasks.TodoTask;
import miku.ui.UIHandler;

import java.util.Map;

public class ListCommand extends Command {
    public ListCommand() {
        super("list", "list");
    }

    @Override
    public String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tasks.GetLength(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, tasks.Get(i)));
        }

        return sb.toString();
    }
}
