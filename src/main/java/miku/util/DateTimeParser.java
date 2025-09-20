package miku.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import miku.exceptions.BadDateException;
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
                    .parseCaseInsensitive() // Accept "am"/"pm" lowercase
                    .appendOptional(DateTimeFormatter.ofPattern("d/M/uuuu[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("uuuu-MM-dd[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("uuuu/MM/dd[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("d-M-uuuu[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("uuuu/M/d[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("MMM d uuuu[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
                    .appendOptional(DateTimeFormatter.ofPattern("MMMM d uuuu[ HHmm][ HH:mm][ h:mma][ h:mm a]", Locale.ENGLISH))
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
     * @throws BadDateException if parsing fails
     */
    public static LocalDateTime parse(String input) throws BadDateException {
        try {
            return LocalDateTime.parse(input, FLEXIBLE_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                // If that fails, try parsing as LocalDate and default to start of day
                LocalDate date = LocalDate.parse(input, FLEXIBLE_FORMATTER);
                return date.atStartOfDay();
            } catch (DateTimeParseException e2) {
                // If both fail, throw a clear exception
                throw new BadDateException(input);
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
        String formatted = time.format(DateTimeFormatter.ofPattern("MMM d yyyy h:mma"));

        return formatted.replaceAll("AM", "am").replaceAll("PM", "pm");
    }

    /**
     * Turns a datetime object into a string in iso format, for saving
     * @param time the LocalDateTime object
     * @return a date string
     */
    public static String getIsoDate(LocalDateTime time) {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Turns a saved string in iso format back into a datetime object
     * @param saved the savestring
     * @return a LocalDateTime object
     */
    public static LocalDateTime parseIsoDate(String saved) throws BadDateException {
        try {
            return LocalDateTime.parse(saved, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new BadDateException(saved);
        }

    }
}
