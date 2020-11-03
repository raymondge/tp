package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.commons.util.DateTimeUtil;

//@@author w-yeehong
/**
 * Represents the date-time when a Task is due.
 * Guarantees: immutable; is an optional attribute of task;
 * is valid as declared in {@link #isValidDateTimeDue(String)}
 */
public class DateTimeDue implements Comparable<DateTimeDue> {

    public static final String MESSAGE_CONSTRAINTS = "Due dates should adhere to one of the "
            + "following formats:\n"
            + "1. yyyyMMdd (e.g. 20201230)\n"
            + "2. yyyyMMdd HHmm (e.g. 20201230 2359)\n"
            + "3. d/M/yyyy (e.g. 30/12/2020)\n"
            + "4. d/M/yyyy HHmm (e.g. 30/12/2020 2359)\n"
            + "Format 3 and 4 allow a single digit for day and month (e.g. 1/1/2020); \n"
            + "Format 1 and 2 do not.";

    public static final DateTimeFormatter[] ALLOWED_DATETIME_FORMATS = {
        DateTimeUtil.DATETIME_FORMAT_YEAR_MONTH_DAY_OPTIONAL_TIME,
        DateTimeUtil.DATETIME_FORMAT_DAY_MONTH_YEAR_SLASH_DELIMITED_OPTIONAL_TIME
    };

    private static final String NULL_VALUE_TO_STRING = "-";

    public final Optional<LocalDateTime> value;
    private String val;
    /**
     * Constructs a {@code DateTimeDue}.
     * {@code optionalDueAt} must be non-null (but can be empty).
     *
     * If the date-time string is empty, the value defaults to {@code Optional.empty()}.
     * If the date-time string is present and valid, the value parsed into is a {@code LocalDateTime}
     * and wrapped in an {@code Optional}.
     *
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param optionalDueAt A valid optional date-time string.
     */
    public DateTimeDue(Optional<String> optionalDueAt) {
        requireNonNull(optionalDueAt);
        value = optionalDueAt
                .map((dueAt) -> {
                    String trimmedDueAt = dueAt.trim();
                    checkArgument(isValidDateTimeDue(trimmedDueAt), MESSAGE_CONSTRAINTS);
                    val = trimmedDueAt;
                    return Optional.of(DateTimeUtil
                            .parseFirstMatching(trimmedDueAt, LocalDateTime::from, ALLOWED_DATETIME_FORMATS));
                })
                .orElse(Optional.empty());
    }

    /**
     * Constructs a {@code DateTimeDue}.
     * {@code dueAt} must be non-null.
     *
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param dueAt A valid date-time string.
     */
    public DateTimeDue(String dueAt) {
        requireNonNull(dueAt);

        String trimmedDueAt = dueAt.trim();
        checkArgument(isValidDateTimeDue(trimmedDueAt), MESSAGE_CONSTRAINTS);

        value = Optional.of(DateTimeUtil
                .parseFirstMatching(trimmedDueAt, LocalDateTime::from, ALLOWED_DATETIME_FORMATS));
        val = trimmedDueAt;
    }

    public Optional<LocalDateTime> getValue() {
        return value;
    }
    public String getVal() {
        return val;
    }
    /**
     * Returns if a given string is a valid date-time.
     */
    public static boolean isValidDateTimeDue(String test) {
        try {
            DateTimeUtil.parseFirstMatching(test, LocalDateTime::from, ALLOWED_DATETIME_FORMATS);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value
                .map((dueAt) ->
                    dueAt.format(DateTimeUtil.DATETIME_FORMAT_DAY_MONTH_YEAR_LONG_SPACE_DELIMITED_OPTIONAL_TIME))
                .orElse(NULL_VALUE_TO_STRING);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeDue // instanceof handles nulls
                && value.equals(((DateTimeDue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(DateTimeDue other) {
        // empty vs other empty -> returns 0
        if (value.isEmpty() && other.value.isEmpty()) {
            return 0;
        }

        // empty vs other not empty -> returns -1
        if (value.isEmpty() && other.value.isPresent()) {
            return -1;
        }

        // not empty vs other empty -> returns 1
        if (value.isPresent() && other.value.isEmpty()) {
            return 1;
        }

        // not empty vs not empty -> returns 1 if date comes after, 0 if same date, -1 if date comes before
        assert (value.isPresent() && other.value.isPresent()) : "Both date-time values are present here.";
        LocalDateTime dueAt = value.get();
        LocalDateTime otherDueAt = other.value.get();

        if (dueAt.isEqual(otherDueAt)) {
            return 0;
        } else if (dueAt.isAfter(otherDueAt)) {
            return 1;
        } else {
            return -1;
        }
    }
}
