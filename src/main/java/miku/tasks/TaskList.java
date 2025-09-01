package miku.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and provides operations to manipulate them.
 */
public class TaskList {
    private List<Task> taskList;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add (required)
     */
    public void Add(Task task) {
        taskList.add(task);
    }

    /**
     * Retrieves a task at the given index.
     *
     * @param index the index of the task (0-based)
     * @return the task at the specified index
     */
    public Task Get(int index) {
        return taskList.get(index);
    }

    /**
     * Marks the task at the given index as completed.
     *
     * @param index the index of the task (0-based)
     */
    public void MarkTask(int index) {
        this.Get(index).mark();
    }

    /**
     * Marks the task at the given index as not completed.
     *
     * @param index the index of the task (0-based)
     */
    public void UnmarkTask(int index) {
        this.Get(index).unmark();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int GetLength() {
        return taskList.size();
    }

    /**
     * Deletes and returns the task at the given index.
     *
     * @param index the index of the task (0-based)
     * @return the task that was removed
     */
    public Task Delete(int index) {
        Task task = taskList.remove(index);
        return task;
    }
}
