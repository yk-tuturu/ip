package miku.command;

import miku.exceptions.IllegalCommandException;
import miku.exceptions.FileIOError;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;

import java.util.Map;

/**
 * A base abstract class for all bot commands
 */
public abstract class Command {
    public String key;
    public String usage;
    public boolean toExit;

    /**
     * Constructor
     * @param key the keyword for the command
     * @param usage the correct usage for the command, provided in errors and help functions
     */
    public Command(String key, String usage) {
        this.key = key;
        this.usage = usage;
    }

    /**
     * Alternate constructor
     * @param key the keyword for the command
     * @param usage the correct usage for the command, provided in errors and help functions
     * @param toExit defaults to false if not given
     */
    public Command(String key, String usage, boolean toExit) {
        this.key = key;
        this.usage = usage;
        this.toExit = true;
    }

    /**
     * Just returns the usage of the command
     * @return string containing the usage of the command
     */
    public String toString() {
        return usage;
    }

    /**
     * Executes the command
     * @param arg the arguments for the command, if any. Parsed into a Map mapping the argument keyword to argument value.
     *            If command doesn't have arguments, the default key is 'default'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return string containing the output of the bot
     * @throws IllegalCommandException if the command arguments are incorrectly formatted
     * @throws FileIOError thrown with regards to save file issues
     */
    public abstract String Run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui)
            throws IllegalCommandException, FileIOError;
}
