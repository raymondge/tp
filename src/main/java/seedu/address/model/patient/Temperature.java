package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chiamyunqing
/**
 * Represents a Patient's temperature recorded in the app.
 * A valid temperature must be to 1 decimal place and is between 32.0 (inclusive)
 * to 41.0 (inclusive) degree Celsius as declared in {@link #isValidTemperature(String)}.
 * Guarantees: immutable.
 */
public class Temperature {
    public static final String MESSAGE_CONSTRAINTS =
            "Temperature should only contain numbers and it should be to one decimal place. (E.g. 36.0).\n"
                    + "The range of temperature should be between 32.0 (inclusive) to 41.0 (inclusive) degree Celsius.";

    public static final String VALIDATION_REGEX = "\\d\\d[.]\\d";
    private final double value;

    /**
     * Constructs a {@code Temperature}.
     * @param temperature A valid temperature.
     */
    public Temperature(String temperature) {
        requireNonNull(temperature);
        checkArgument(isValidTemperature(temperature), MESSAGE_CONSTRAINTS); //ensures temp is always to 1 d.p.
        value = Double.parseDouble(temperature);
    }

    /**
     * Returns true if a given string matches the regex.
     */
    private static boolean isRegexCorrect(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is within temperature range of 32.0 to 41.0 degree Celsius.
     */
    private static boolean isWithinTempRange(String test) {
        assert(isRegexCorrect(test));
        Double temperature = Double.parseDouble(test);
        return temperature >= 32.0 && temperature <= 41.0;
    }

    /**
     * Returns true if a given string is a valid temperature.
     */
    public static boolean isValidTemperature(String test) {
        return isRegexCorrect(test) && isWithinTempRange(test);
    }

    /**
     * Returns the float value of the temperature reading.
     */
    public double getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Temperature // instanceof handles nulls
                && value == ((Temperature) other).getValue()); // state check
    }

    @Override
    public int hashCode() {
        return Double.toString(value).hashCode();
    }
}
