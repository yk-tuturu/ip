public class DeadlineTask extends Task {
    private String deadline;

    public DeadlineTask(String value, String deadline) {
        super(value);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline);
    }

    @Override
    public String getSaveString() {
        return "D|" + super.getSaveString() + "|" + deadline;
    }
}
