package miku.exceptions;

public class IllegalSaveException extends Exception {
    public IllegalSaveException() {
        super("Error handling save file");
    }
    public IllegalSaveException(String str) {
        super(str);
    }
}
