package miku.exceptions;

public class IllegalSaveException extends Exception {
    public IllegalSaveException(String msg) {
        super(msg);
    }

    public IllegalSaveException() {
        super("Save file is corrupted!");
    }
}
