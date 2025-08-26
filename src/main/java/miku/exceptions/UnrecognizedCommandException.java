package miku.exceptions;

public class UnrecognizedCommandException extends Exception {
    public UnrecognizedCommandException() {
        super("Miku doesn't recognize this command :(");
    }
    public UnrecognizedCommandException(String msg) {
        super(msg);
    }
}
