package miku.command;

import java.util.ArrayList;
import java.util.Map;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.Task;
import miku.tasks.TaskList;
import miku.ui.UiHandler;


public class FindCommand extends Command {
    /**
     * Creates new find command
     */
    public FindCommand() {
        super("find", "find <searchterm>");
    }

    /**
     * Takes a search term and returns a list of tasks that matches the search term
     * @param arg
     * @param tasks
     * @param saveData
     * @param ui
     * @return a list of tasks
     * @throws IllegalCommandException if no search term provided
     */
    @Override
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UiHandler ui)
            throws IllegalCommandException {
        if (!arg.containsKey("default") || arg.get("default").isEmpty()) {
            throw new IllegalCommandException("Search term cannot be empty!", this.getUsage());
        }

        ArrayList<Task> matchingTasks = new ArrayList<Task>();
        String search = arg.get("default").toLowerCase();

        for (int i = 0; i < tasks.getLength(); i++) {
            String taskDesc = tasks.get(i).value.toLowerCase();

            if (taskDesc.contains(search)) {
                matchingTasks.add(tasks.get(i));
            }
        }

        if (matchingTasks.isEmpty()) {
            return "Miku didn't find any matching tasks!";
        }

        StringBuilder sb = new StringBuilder("Miku found the following tasks:\n");


        for (int i = 0; i < matchingTasks.size(); i++) {
            Task task = matchingTasks.get(i);

            sb.append(i + ". ").append(task.toString()).append("\n");
        }

        return sb.toString();
    }
}
