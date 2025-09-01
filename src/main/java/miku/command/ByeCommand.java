package miku.command;

import java.util.Map;

import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

/**
 * Command that says goodbye and exits the program
 */
public class ByeCommand extends Command {
    /**
     * Creates a new bye command with keyword {@code "bye"}.
     */
    public ByeCommand() {
        super("bye", "bye", true);
    }

    /**
     * Says goodbye
     * @param arg the arguments for the command, if any.
     *            Parsed into a Map mapping the argument keyword to argument value.
     *            If command doesn't have arguments, the default key is 'default'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return string that says goodbye
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui) {
        return "See you in the next sekai!";
    }
}
