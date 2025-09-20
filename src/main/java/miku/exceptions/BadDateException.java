package miku.exceptions;

public class BadDateException extends Exception {
    public BadDateException() {
        super("Failed to parse date");
    }

    public BadDateException(String date) {
        super("Failed to parse date: " + date);
    }
}
