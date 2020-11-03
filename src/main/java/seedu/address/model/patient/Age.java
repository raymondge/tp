package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chiamyunqing
/**
 * Represents a Patient's age recorded in the app.
 * A valid patient age is between 0 (inclusive) and 120 (exclusive),
 * as declared in {@link #isValidAge(String)}.
 * If the age is invalid, the corresponding error message should be retrieved
 * via {@link #getErrorMessage(String)}.
 * Guarantees: immutable.
 */
public class Age {

    public static final String MESSAGE_AGE_NOT_NUMBER =
            "Age should only contain numbers.";

    public static final String MESSAGE_INVALID_AGE_RANGE =
            "Age should be between the range 0 (inclusive) to 120 (exclusive).";

    public static final String VALIDATION_REGEX = "\\d{1,3}";
    private final int value;

    /**
     * Constructs a {@code Age}.
     *
     * @param age A valid age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_AGE_NOT_NUMBER);
        value = Integer.parseInt(age);
    }

    /**
     * Returns true if a given string matches the validation regex.
     */
    private static boolean isRegexCorrect(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is within the age range 0 (inclusive) to
     * 120 (exclusive).
     */
    private static boolean isWithinValidAgeRange(String test) {
        assert(isRegexCorrect(test));
        return (Integer.parseInt(test) >= 0) && (Integer.parseInt(test) < 120);
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        return isRegexCorrect(test) && isWithinValidAgeRange(test);
    }

    /**
     * Returns the precise error message according to the error that arises.
     * This method should only be called if isValidAge(test) returns false.
     *
     * @param test the string with error in converting to age.
     * @return the exact error message that corresponds to the error.
     */
    public static String getErrorMessage(String test) {
        assert(!isValidAge(test));
        if (!isRegexCorrect(test)) {
            return MESSAGE_AGE_NOT_NUMBER;
        } else {
            return MESSAGE_INVALID_AGE_RANGE;
        }
    }

    /**
     * Returns the value of the age.
     */
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value == ((Age) other).getValue()); // state check
    }

    @Override
    public int hashCode() {
        return Integer.toString(value).hashCode();
    }
}
