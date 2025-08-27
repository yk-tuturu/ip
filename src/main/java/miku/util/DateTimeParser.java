package miku.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    private static final DateTimeFormatter[] formatters = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),   // 2019-12-02
            DateTimeFormatter.ofPattern("d/M/yyyy"),     // 2/12/2019
            DateTimeFormatter.ofPattern("MMM d yyyy"),   // Dec 2 2019
            DateTimeFormatter.ofPattern("MMMM d, yyyy")  // December 2, 2019
    };

    public static LocalDate parseDate(String input) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ignored) {
                // try the next one
            }
        }
        throw new IllegalArgumentException("Unsupported date format: " + input);
    }
}
