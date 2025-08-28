package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

public class ByeCommand extends Command {
    public ByeCommand() {

        super("bye", "bye", true);
    }

    @Override
    public String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        return "See you in the next sekai!";
    }
}
