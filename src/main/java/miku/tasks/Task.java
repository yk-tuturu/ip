package miku.tasks;

/**
 * Abstract base class representing a generic task.
 */
public abstract class Task {
    private String value;
    private boolean isDone;

    /**
     * Creates a new task with the given description.
     * <p>
     * The task is initially not completed.
     * </p>
     *
     * @param value the task description (required)
     */
    public Task(String value) {
        this.value = value;
        this.isDone = false;
    }

    /**
     * Creates a new task with the given description and completion status.
     *
     * @param value the task description (required)
     * @param isDone  whether the task is completed
     */
    public Task(String value, boolean isDone) {
        this.value = value;
        this.isDone = isDone;
    }

    /**
     * Gets the value of the task
     *
     * @return a string containing the task description
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a user-readable string representation of the task.
     *
     * @return a string in the format "[status] task description"
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "x" : " ", value);
    }

    /**
     * Returns a string representation suitable for saving to a file.
     *
     * @return a string in the format "status|description" where status is 1 for done, 0 otherwise
     */
    public String getSaveString() {
        return String.format("%d|%s", isDone ? 1 : 0, value);
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        isDone = false;
    }
}
