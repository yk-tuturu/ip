package miku.util;

import miku.exceptions.IllegalCommandException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class DateTimeParser {
    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    // Day / Month / Year
                    .appendPattern("[d/M/uuuu][uuuu-MM-dd][uuuu/MM/dd][d-M-uuuu][uuuu/M/d]")
                    .optionalStart()
                    .appendLiteral(' ')
                    // Time patterns
                    .appendPattern("[HHmm][HH:mm][h:mma]")
                    .optionalEnd()
                    .toFormatter(Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.SMART);

    public static LocalDateTime parse(String input) throws IllegalCommandException {
        try {
            return LocalDateTime.parse(input, FLEXIBLE_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                // If that fails, try parsing as LocalDate and default to start of day
                LocalDate date = LocalDate.parse(input, FLEXIBLE_FORMATTER);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                // If both fail, throw a clear exception
                throw new IllegalCommandException("Date parsing failed :(");
            }
        }
    }

    public static String getDateString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma"));
    }
}
