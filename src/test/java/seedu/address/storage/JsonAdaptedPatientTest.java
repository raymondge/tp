package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.PeriodOfStay;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Temperature;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TEMP = "39.p";
    private static final String INVALID_PERIOD = "20200011-20209900";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_AGE = "1000";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_TEMP = BENSON.getTemperature().toString();
    private static final String VALID_PERIOD = BENSON.getPeriodOfStay().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_AGE = BENSON.getAge().toString();

    private static final String COMMENT = "Vegan";

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(INVALID_NAME, VALID_TEMP, VALID_PERIOD,
                        VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(null, VALID_TEMP, VALID_PERIOD,
                VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidTemp_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, INVALID_TEMP, VALID_PERIOD,
                        VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = Temperature.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullTemp_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, null, VALID_PERIOD,
                VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Temperature.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPeriod_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, INVALID_PERIOD,
                        VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = PeriodOfStay.MESSAGE_INVALID_DATE;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPeriod_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, null,
                VALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PeriodOfStay.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, VALID_PERIOD,
                        INVALID_PHONE, VALID_AGE, COMMENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, VALID_PERIOD,
                null, VALID_AGE, COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, VALID_PERIOD,
                        VALID_PHONE, INVALID_AGE, COMMENT);
        String expectedMessage = Age.MESSAGE_AGE_NOT_NUMBER;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_TEMP, VALID_PERIOD,
                VALID_PHONE, null, COMMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }
}
