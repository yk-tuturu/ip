public class TodoTask extends Task {
    public TodoTask(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
