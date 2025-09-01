package miku.tasks;

/**
 * Abstract base class representing a generic task.
 */
public abstract class Task {
    public String value;
    public boolean done;

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
        this.done = false;
    }

    /**
     * Creates a new task with the given description and completion status.
     *
     * @param value the task description (required)
     * @param done  whether the task is completed
     */
    public Task(String value, boolean done) {
        this.value = value;
        this.done = done;
    }

    /**
     * Returns a user-readable string representation of the task.
     *
     * @return a string in the format "[status] task description"
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", done ? "x" : " ", value);
    }

    /**
     * Returns a string representation suitable for saving to a file.
     *
     * @return a string in the format "status|description" where status is 1 for done, 0 otherwise
     */
    public String getSaveString() {
        return String.format("%d|%s", done ? 1 : 0, value);
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        done = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        done = false;
    }
}
