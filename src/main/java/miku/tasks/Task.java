package miku.tasks;

public abstract class Task {
    public String value;
    public boolean done;

    public Task(String value) {
        this.value = value;
        this.done = false;
    }

    public Task(String value, boolean done) {
        this.value = value;
        this.done = done;
    }

    public String toString() {
        return String.format("[%s] %s", done ? "x" : " ", value);
    }

    public String getSaveString() {
        return String.format("%d|%s", done ? 1 : 0, value);
    }

    public void mark() {
        done = true;
    }

    public void unmark() {
        done = false;
    }
}
