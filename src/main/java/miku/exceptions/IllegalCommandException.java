package miku.exceptions;

public class IllegalCommandException  extends Exception {
    public IllegalCommandException() {
        super("Miku can't understand this command :(");
    }

    public IllegalCommandException(String msg) {
        super(msg);
    }

    public IllegalCommandException(String msg, String usage) {
        super(String.format("%s\n    Usage: %s", msg, usage));
    }
}
