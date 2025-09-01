package miku.command;

import java.util.Map;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;


/**
 * Basic command. Says hello
 */
public class HelloCommand extends Command {
    public HelloCommand() {
        super("hello", "hello");
    }

    /**
     * Just prints hello. No arguments needed
     * @param arg the arguments for the command, if any.
     *            Parsed into a Map mapping the argument keyword to argument value.
     *            If command doesn't have arguments, the default key is 'default'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return
     * @throws IllegalCommandException
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException {
        return "Hello sekai!";
    }
}
