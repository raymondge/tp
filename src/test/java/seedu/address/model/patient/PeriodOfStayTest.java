package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author chiamyunqing
/**
 * Test cases for the attribute period of stay of patient.
 */
public class PeriodOfStayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PeriodOfStay(null));
    }

    @Test
    public void constructor_invalidPeriodOfStay_throwsIllegalArgumentException() {
        String invalidPeriodOfStay = "20200101-20209999";
        assertThrows(IllegalArgumentException.class, () -> new PeriodOfStay(invalidPeriodOfStay));
    }

    @Test
    public void isValidPeriodOfStay() {
        // null period of stay
        assertThrows(NullPointerException.class, () -> PeriodOfStay.isValidPeriodOfStay(null));

        // invalid period of stay
        assertFalse(PeriodOfStay.isValidPeriodOfStay("")); // empty string
        assertFalse(PeriodOfStay.isValidPeriodOfStay(" ")); // spaces only
        assertFalse(PeriodOfStay.isValidPeriodOfStay("stay")); //non-numeric
        assertFalse(PeriodOfStay.isValidPeriodOfStay("20200709")); //no end date
        assertFalse(PeriodOfStay.isValidPeriodOfStay("2020090p-20201009")); //alphabets within digits
        assertFalse(PeriodOfStay.isValidPeriodOfStay("20200101 - 20200202")); //spaces in input
        assertFalse(PeriodOfStay.isValidPeriodOfStay("2020090120201009")); //missing dash
        assertFalse(PeriodOfStay.isValidPeriodOfStay("20200901-20201038")); //no such date
        assertFalse(PeriodOfStay.isValidPeriodOfStay("20201801-20201001")); //no such month
        assertFalse(PeriodOfStay.isValidPeriodOfStay("20200902-20200901")); //end date before start date

        //valid period of stay
        assertTrue(PeriodOfStay.isValidPeriodOfStay("20190308-20190506"));
        assertTrue(PeriodOfStay.isValidPeriodOfStay("20200913-20200914"));
        assertTrue(PeriodOfStay.isValidPeriodOfStay("20200909-20200909")); //1 day stay
    }

    @Test
    public void invalidPeriodOfStay_getErrorMessage() {
        assertEquals(PeriodOfStay.getErrorMessage("2020abc-203"), PeriodOfStay.MESSAGE_WRONG_REGEX);
        assertEquals(PeriodOfStay.getErrorMessage("20200304-20200101"), PeriodOfStay.MESSAGE_INVALID_DATE);
    }
}
