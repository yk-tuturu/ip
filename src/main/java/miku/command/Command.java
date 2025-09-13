package miku.command;

import java.util.Map;

import miku.exceptions.FileIoError;
import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

/**
 * A base abstract class for all bot commands
 */
public abstract class Command {
    private String key;
    private String usage;
    private boolean isToExit;

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
     * @param isToExit defaults to false if not given
     */
    public Command(String key, String usage, boolean isToExit) {
        this.key = key;
        this.usage = usage;
        this.isToExit = isToExit;
    }

    public String getKey() {
        return key;
    }

    public String getUsage() {
        return usage;
    }

    public boolean checkToExit() {
        return isToExit;
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
     * @param arg the arguments for the command, if any.
     *            Parsed into a Map mapping the argument keyword to argument value.
     *            If command doesn't have arguments, the default key is 'default'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return string containing the output of the bot
     * @throws IllegalCommandException if the command arguments are incorrectly formatted
     * @throws FileIoError thrown with regards to save file issues
     */
    public abstract String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException, FileIoError;
}
