package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

public class HelloCommand extends Command {
    public HelloCommand() {
        super("hello", "hello");
    }

    @Override
    public void Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        ui.Print("Hello sekai!");
    }
}
