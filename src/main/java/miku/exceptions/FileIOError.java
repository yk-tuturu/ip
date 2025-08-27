package miku.exceptions;

public class FileIOError extends Exception {
    public FileIOError() {
        super("Error handling save file");
    }
    public FileIOError(String str) {
        super(str);
    }
}
