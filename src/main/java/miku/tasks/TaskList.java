package miku.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public void Add(Task task) {
        taskList.add(task);
    }

    public Task Get(int index) {
        return taskList.get(index);
    }

    public void MarkTask(int index) {
        this.Get(index).mark();
    }

    public void UnmarkTask(int index) {
        this.Get(index).unmark();
    }

    public int GetLength() {
        return taskList.size();
    }

    public Task Delete(int index) {
        Task task = taskList.remove(index);
        return task;
    }
}
