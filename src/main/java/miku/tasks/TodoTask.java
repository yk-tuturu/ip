package miku.tasks;

/**
 * Represents a simple task without a specific deadline or time range.
 */
public class TodoTask extends Task {

    /**
     * Creates a new todo task.
     *
     * @param value the task description (required)
     */
    public TodoTask(String value) {
        super(value);
    }

    /**
     * Creates a new todo task with completion status.
     *
     * @param value the task description (required)
     * @param isDone  whether the task is completed
     */
    public TodoTask(String value, boolean isDone) {
        super(value, isDone);
    }

    /**
     * Returns a user-readable string representation of the todo task. Used when user calls the list command
     *
     * @return a string in the format "[T][status] task description"
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string representation for saving to the save file
     *
     * @return a string in the format "T|status|description"
     */
    @Override
    public String getSaveString() {
        return "T|" + super.getSaveString();
    }
}
