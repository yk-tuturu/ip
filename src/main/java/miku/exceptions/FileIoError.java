package miku.exceptions;

/**
 * Exception thrown when there is an error reading a file
 */
public class FileIoError extends Exception {
    public FileIoError() {
        super("Error handling save file");
    }
    public FileIoError(String str) {
        super(str);
    }
}
