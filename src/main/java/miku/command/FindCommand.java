package miku.command;

import java.util.Map;
import java.util.ArrayList;

import miku.exceptions.IllegalCommandException;
import miku.storage.SaveDataManager;
import miku.tasks.TaskList;
import miku.ui.UIHandler;
import miku.tasks.Task;

public class FindCommand extends Command {
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
    public String run(Map<String, String> arg, TaskList tasks, SaveDataManager saveData, UIHandler ui) throws IllegalCommandException {
        if (!arg.containsKey("default") || arg.get("default").isEmpty()) {
            throw new IllegalCommandException("Search term cannot be empty!", this.usage);
        }

        ArrayList<Task> matchingTasks = new ArrayList<Task>();
        String search = arg.get("default").toLowerCase();

        for (int i = 0; i < tasks.GetLength(); i++) {
            String taskDesc = tasks.Get(i).value.toLowerCase();

            if (taskDesc.contains(search)) {
                matchingTasks.add(tasks.Get(i));
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
