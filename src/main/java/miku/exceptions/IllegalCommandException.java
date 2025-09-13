package miku.exceptions;

import miku.util.Constants;

/**
 * When user enters commands wrongly
 */
public class IllegalCommandException extends Exception {
    public IllegalCommandException() {
        super("Miku can't understand this command :(");
    }

    public IllegalCommandException(String msg) {
        super(msg);
    }

    public IllegalCommandException(String msg, String usage) {
        super(String.format("%s\n%sUsage: %s", msg, Constants.INDENT, usage));
    }
}
