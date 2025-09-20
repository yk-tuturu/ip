package miku.command;

import java.util.Map;

import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UiHandler;

/**
 * Prints all commands and their correct usages and descriptions
 */
public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "help");
    }

    /**
     * Just prints a list of commands
     *
     * @param arg the arguments for the command, if any.
     *            Parsed into a Map mapping the argument keyword to argument value.
     *            If command doesn't have arguments, the default key is 'default'
     * @param tasks taskList from the main bot
     * @param saveData saveDataManager of the main bot
     * @param ui uiManager of the main bot
     * @return
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui) {
        String output = "hello: say hello to MikuBot!\n"
                + "bye: say goodbye to MikuBot :(\n"
                + "help: shows this command\n"
                + "todo <task>: adds a todo task to your task list\n"
                + "deadline <task> /by <time>: adds a deadline task to your task list\n"
                + "event <task> /from <time> /to <time>: adds an event to your task list\n"
                + "mark <index>: mark a task as done\n"
                + "unmark <index>: mark a task as not done\n"
                + "delete <index>: delete a task from the list\n"
                + "list: shows all tasks\n"
                + "find <searchterm>: returns all tasks matching the searchterm";
        return output;
    }
}
