public class EventTask extends Task {
    public String from;
    public String to;

    public EventTask(String value, String from, String to) {
        super(value);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), from, to);
    }

    @Override
    public String getSaveString() {
        return "E|" + super.getSaveString() + "|" + from + "|" + "to";
    }
}
