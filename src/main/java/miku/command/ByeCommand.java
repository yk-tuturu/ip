package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

/**
 * Exits the bot programme when run. Usage: bye
 * No arguments
 */
public class ByeCommand extends Command {
    public ByeCommand() {
        super("bye", "bye", true);
    }

    /**
     * Prints a simple goodbye message and exits
     * @param arg
     * @param tasks
     * @param saveData
     * @param ui
     * @return the goodbye message
     */
    @Override
    public String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) {
        return "See you in the next sekai!";
    }
}
