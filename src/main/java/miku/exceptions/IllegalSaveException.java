package miku.exceptions;

/**
 * Exception for corrupted save files
 */
public class IllegalSaveException extends Exception {
    public IllegalSaveException(String msg) {
        super(msg);
    }

    public IllegalSaveException() {
        super("Save file is corrupted!");
    }
}
