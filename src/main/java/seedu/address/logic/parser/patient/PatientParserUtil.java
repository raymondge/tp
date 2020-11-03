package seedu.address.logic.parser.patient;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Comment;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.PeriodOfStay;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Temperature;
import seedu.address.model.patient.TemperatureRange;

/**
 * Contains utility methods used for parsing strings in the various patient-related Parser classes.
 */
public class PatientParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    //@@author chiamyunqing
    /**
     * Parses a {@code String temperature} into a {@code Temperature}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code temperature} is invalid.
     */
    public static Temperature parseTemperature(String temp) throws ParseException {
        requireNonNull(temp);
        String trimmedTemp = temp.trim();
        if (!Temperature.isValidTemperature(trimmedTemp)) {
            throw new ParseException(Temperature.MESSAGE_CONSTRAINTS);
        }
        return new Temperature(trimmedTemp);
    }

    /**
     * Parses a {@code String period of stay} into a {@code PeriodOfStay}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code period of stay} is invalid.
     */
    public static PeriodOfStay parsePeriodOfStay(String periodOfStay) throws ParseException {
        requireNonNull(periodOfStay);
        String trimmedPeriodOfStay = periodOfStay.trim();
        if (!PeriodOfStay.isValidPeriodOfStay(trimmedPeriodOfStay)) {
            throw new ParseException(PeriodOfStay.getErrorMessage(trimmedPeriodOfStay));
        }
        return new PeriodOfStay(trimmedPeriodOfStay);
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws ParseException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new ParseException(Age.getErrorMessage(trimmedAge));
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code String comment} into a {@code Comment}
     * Leading and trailing whitespaces will be trimmed
     *
     * @return an "-" comment if no comment is added
     */
    public static Comment parseComment(String comment) {
        if (comment == null || comment.trim().length() == 0) {
            return new Comment("-");
        } else {
            return new Comment(comment.trim());
        }
    }
    //@@author chiamyunqing

    //@@author raymondge
    /**
     * Parses a {@code String temperature} into a {@code Temperature}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code temperature} is invalid.
     */
    public static TemperatureRange parseTemperatureRange(String tempRange) throws ParseException {
        requireNonNull(tempRange);
        String trimmedTempRange = tempRange.trim();
        if (!TemperatureRange.isValidTemperatureRange(trimmedTempRange)) {
            throw new ParseException(TemperatureRange.MESSAGE_CONSTRAINTS_TEMPERATURERANGE);
        }
        return new TemperatureRange(trimmedTempRange);
    }
    //@@author raymondge
}
