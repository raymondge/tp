package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author chiamyunqing
/**
 * Test cases for the attribute temperature of patient.
 */
public class TemperatureTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Temperature(null));
    }

    @Test
    public void constructor_invalidTemperature_throwsIllegalArgumentException() {
        String emptyString = ""; //EP: empty strings
        assertThrows(IllegalArgumentException.class, () -> new Temperature(emptyString));

        String invalidTemperature = "37"; //EP: invalid temperature
        assertThrows(IllegalArgumentException.class, () -> new Temperature(invalidTemperature));
    }

    @Test
    public void isValidTemperature() {
        // null temperature
        assertThrows(NullPointerException.class, () -> Temperature.isValidTemperature(null));

        // invalid temperature
        assertFalse(Temperature.isValidTemperature("")); //EP: empty string
        assertFalse(Temperature.isValidTemperature(" ")); //EP: spaces only
        assertFalse(Temperature.isValidTemperature("37")); //EP: not 1 decimal place
        assertFalse(Temperature.isValidTemperature("temp")); //EP: non-numeric
        assertFalse(Temperature.isValidTemperature("37.a")); //EP: alphabets within digits
        assertFalse(Temperature.isValidTemperature("37.")); //EP: no digit after decimal
        assertFalse(Temperature.isValidTemperature("3 7.6")); //EP: spaces within digits
        assertFalse(Temperature.isValidTemperature("31.9")); //Boundary value
        assertFalse(Temperature.isValidTemperature("41.1")); //Boundary value

        // valid temperature
        assertTrue(Temperature.isValidTemperature("32.0"));
        assertTrue(Temperature.isValidTemperature("36.0"));
        assertTrue(Temperature.isValidTemperature("41.0"));
    }
}
