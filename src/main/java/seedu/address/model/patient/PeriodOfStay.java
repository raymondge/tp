package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//@@author chiamyunqing
/**
 * Represents a Patient's period of stay in the facility.
 * Guarantees: immutable; is valid as declared in {@link #isValidPeriodOfStay(String)}
 * If the period of stay is invalid, the corresponding error message should be retrieved
 * via {@link #getErrorMessage(String)}.
 */
public class PeriodOfStay {

    public static final String MESSAGE_WRONG_REGEX =
            "Period of stay should be in the format YYYYMMDD-YYYYMMDD. \nE.g. 20200911-20200924";
    public static final String MESSAGE_INVALID_DATE =
            "The dates of the period of stay must be valid.\nIn particular, the start date of the period of stay "
            + "must be before or equals the end date.\nE.g. 20200901-20200901 or 20200901-20200914.";

    public static final String VALIDATION_REGEX = "\\d{8}[-]\\d{8}";

    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Constructs a {@code PeriodOfStay}.
     *
     * @param periodOfStay A valid period of stay.
     */
    public PeriodOfStay(String periodOfStay) {
        requireNonNull(periodOfStay);
        checkArgument(isRegexCorrect(periodOfStay), MESSAGE_WRONG_REGEX);
        checkArgument(isValidDates(periodOfStay), MESSAGE_INVALID_DATE);
        startDate = LocalDate.parse(periodOfStay.split("-")[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
        endDate = LocalDate.parse(periodOfStay.split("-")[1], DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * Returns true if a given string matches the validation regex.
     */
    private static boolean isRegexCorrect(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string has valid start and end dates.
     * Start and end dates are valid if start date is before or equals end date.
     */
    private static boolean isValidDates(String test) {
        assert(isRegexCorrect(test));
        String[] dates = test.split("-");
        try {
            LocalDate start = LocalDate.parse(dates[0], DateTimeFormatter.ofPattern("yyyyMMdd"));
            LocalDate end = LocalDate.parse(dates[1], DateTimeFormatter.ofPattern("yyyyMMdd"));
            return start.compareTo(end) <= 0; //check start date less than or equals end date
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns true if a given string is a valid period of stay.
     */
    public static boolean isValidPeriodOfStay(String test) {
        return isRegexCorrect(test) && isValidDates(test);
    }

    /**
     * Returns the precise error message according to the error that arises.
     * This method should only be called if isValidPeriodOfStay(test) returns false.
     *
     * @param test the string with error in converting to periodOfStay.
     * @return the exact error message that corresponds to the error.
     */
    public static String getErrorMessage(String test) {
        assert(!isValidPeriodOfStay(test));
        if (!isRegexCorrect(test)) {
            return MESSAGE_WRONG_REGEX;
        } else {
            return MESSAGE_INVALID_DATE;
        }
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Returns a string that is more human readable (e.g. 09 Jan 2020), meant for UI.
     *
     * @return reader friendly string.
     */
    public String readerFriendlyString() {
        return startDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + " to "
                + endDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    /**
     * Primarily used for storage of data.
     */
    @Override
    public String toString() {
        return startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-"
                + endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PeriodOfStay // instanceof handles nulls
                && startDate.compareTo(((PeriodOfStay) other).startDate) == 0
                && endDate.compareTo(((PeriodOfStay) other).endDate) == 0); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
