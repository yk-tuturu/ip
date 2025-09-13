package miku.exceptions;

/**
 * When user types random gibberish that don't correspond to a command, throw this error
 */
public class UnrecognizedCommandException extends Exception {
    public UnrecognizedCommandException() {
        super("Miku doesn't recognize this command :(");
    }
    public UnrecognizedCommandException(String msg) {
        super(msg);
    }
}
