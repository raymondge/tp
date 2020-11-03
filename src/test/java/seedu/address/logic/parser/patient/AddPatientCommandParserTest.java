package seedu.address.logic.parser.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.command.GeneralCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.testutil.command.GeneralCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.testutil.command.PatientCommandTestUtil.AGE_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.AGE_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_PERIOD_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_TEMP_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.NAME_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.NAME_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.PERIOD_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.PERIOD_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.TEMP_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.TEMP_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_AGE_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PERIOD_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_TEMP_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PeriodOfStay;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Temperature;
import seedu.address.testutil.PatientBuilder;

//@@author chiamyunqing
/**
 * Contains unit tests for AddPatientCommandParser.
 */
public class AddPatientCommandParserTest {

    private AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TEMP_DESC_BOB
                + PERIOD_DESC_BOB + PHONE_DESC_BOB + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + TEMP_DESC_BOB
                + PERIOD_DESC_BOB + PHONE_DESC_BOB + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));

        //multiple temperature - last temp accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TEMP_DESC_AMY + TEMP_DESC_BOB
                + PERIOD_DESC_BOB + PHONE_DESC_BOB + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));

        //multiple period of stay - last period of stay accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_AMY
                + PERIOD_DESC_BOB + PHONE_DESC_BOB + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));

        //multiple phone - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_AMY + PHONE_DESC_BOB + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));

        //multiple age - last age accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_AMY + AGE_DESC_BOB, new AddPatientCommand(expectedPatient));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no comments
        Patient expectedPatient = new PatientBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + TEMP_DESC_AMY + PERIOD_DESC_AMY
                + PHONE_DESC_AMY + AGE_DESC_AMY, new AddPatientCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                        + PHONE_DESC_BOB + AGE_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                        + VALID_PHONE_BOB + AGE_DESC_BOB, expectedMessage);

        //missing temperature prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TEMP_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB, expectedMessage);

        //missing period of stay prefix
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + VALID_PERIOD_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB, expectedMessage);

        //missing age prefix
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + VALID_AGE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TEMP_BOB + VALID_PERIOD_BOB
                        + VALID_PHONE_BOB + VALID_AGE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + INVALID_PHONE_DESC + AGE_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        //invalid temperature
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TEMP_DESC + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB, Temperature.MESSAGE_CONSTRAINTS);

        //invalid period of stay
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + INVALID_PERIOD_DESC
                + PHONE_DESC_BOB + AGE_DESC_BOB, PeriodOfStay.MESSAGE_INVALID_DATE);

        //invalid age
        assertParseFailure(parser, NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + INVALID_AGE_DESC, Age.MESSAGE_INVALID_AGE_RANGE);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_TEMP_DESC + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB
                + PHONE_DESC_BOB + AGE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
    }
}
