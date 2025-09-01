package miku.tasks;

public abstract class Task {
    public String value;
    public boolean isDone;

    public Task(String value) {
        this.value = value;
        this.isDone = false;
    }

    public Task(String value, boolean isDone) {
        this.value = value;
        this.isDone = isDone;
    }

    public String toString() {
        return String.format("[%s] %s", isDone ? "x" : " ", value);
    }

    public String getSaveString() {
        return String.format("%d|%s", isDone ? 1 : 0, value);
    }

    public void mark() {
        isDone = true;
    }

    public void unmark() {
        isDone = false;
    }
}
