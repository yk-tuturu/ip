package miku.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import miku.exceptions.IllegalCommandException;

/**
 * Utility class for parsing and formatting dates and times.
 * <p>
 * Provides flexible parsing of user input into {@link LocalDateTime} and formatting
 * {@link LocalDateTime} objects into a readable string.
 * </p>
 */
public class DateTimeParser {
    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    // Day / Month / Year patterns
                    .appendPattern("[d/M/uuuu][uuuu-MM-dd][uuuu/MM/dd][d-M-uuuu][uuuu/M/d]")
                    .optionalStart()
                    .appendLiteral(' ')
                    // Time patterns
                    .appendPattern("[HHmm][HH:mm][h:mma]")
                    .optionalEnd()
                    .toFormatter(Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.SMART);

    /**
     * Parses a string into a {@link LocalDateTime}.
     * <p>
     * Supports multiple date and time formats. If the time is omitted, defaults to start of day.
     * </p>
     *
     * @param input the date/time string to parse (required)
     * @return the parsed LocalDateTime
     * @throws IllegalCommandException if parsing fails
     */
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

    /**
     * Formats a {@link LocalDateTime} into a readable string.
     *
     * @param time the LocalDateTime to format (required)
     * @return a formatted string in the pattern "MMM d yyyy h:mma"
     */
    public static String getDateString(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma"));
    }
}
