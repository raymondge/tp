package seedu.address.logic.parser.patient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.patient.EditPatientCommand.MESSAGE_PATIENT_NOT_EDITED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.command.PatientCommandTestUtil.AGE_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.AGE_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.COMMENT_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.COMMENT_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_PERIOD_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.INVALID_TEMP_DESC;
import static seedu.address.testutil.command.PatientCommandTestUtil.NAME_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.PERIOD_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.PERIOD_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.TEMP_DESC_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.TEMP_DESC_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_AGE_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_AGE_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PERIOD_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PERIOD_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_TEMP_AMY;
import static seedu.address.testutil.command.PatientCommandTestUtil.VALID_TEMP_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.patient.EditPatientCommand;
import seedu.address.model.patient.Age;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.PeriodOfStay;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Temperature;
import seedu.address.testutil.EditPatientDescriptorBuilder;

//@author LeeMingDe
/**
 * Contains unit tests for EditPatientCommandParser.
 */
public class EditPatientCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPatientCommand.MESSAGE_USAGE);

    private EditPatientCommandParser parser = new EditPatientCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No field specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_PATIENT_NOT_EDITED);

        // No index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_AGE_DESC,
                Age.MESSAGE_INVALID_AGE_RANGE); // invalid age
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PERIOD_DESC,
                PeriodOfStay.MESSAGE_INVALID_DATE); // invalid period
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_TEMP_DESC,
                Temperature.MESSAGE_CONSTRAINTS); // invalid temperature

        // Invalid phone followed by valid age
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC + AGE_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // Valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // Multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_AGE_DESC + VALID_PERIOD_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_NAME_AMY + PHONE_DESC_BOB + AGE_DESC_AMY
                + TEMP_DESC_AMY + PERIOD_DESC_AMY + NAME_DESC_AMY;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withAge(VALID_AGE_AMY)
                .withTemperature(VALID_TEMP_AMY).withPeriodOfStay(VALID_PERIOD_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = VALID_NAME_AMY + PHONE_DESC_BOB + AGE_DESC_AMY;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withAge(VALID_AGE_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // Name
        String userInput = VALID_NAME_AMY + NAME_DESC_AMY;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Phone
        userInput = VALID_NAME_AMY + PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Age
        userInput = VALID_NAME_AMY + AGE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAge(VALID_AGE_AMY).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Period
        userInput = VALID_NAME_AMY + PERIOD_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPeriodOfStay(VALID_PERIOD_AMY).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Temperature
        userInput = VALID_NAME_AMY + TEMP_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withTemperature(VALID_TEMP_AMY).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Comment
        userInput = VALID_NAME_AMY + COMMENT_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withComment(COMMENT_AMY).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        String userInput = VALID_NAME_AMY + PHONE_DESC_AMY + AGE_DESC_AMY + TEMP_DESC_AMY
                + PERIOD_DESC_AMY + PHONE_DESC_AMY + AGE_DESC_AMY + TEMP_DESC_AMY + PERIOD_DESC_AMY
                + PHONE_DESC_BOB + AGE_DESC_BOB + TEMP_DESC_BOB + PERIOD_DESC_BOB;

        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).withAge(VALID_AGE_BOB).withTemperature(VALID_TEMP_BOB)
                .withPeriodOfStay(VALID_PERIOD_BOB)
                .build();
        EditPatientCommand expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // No other valid values specified
        String userInput = VALID_NAME_AMY + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        EditPatientCommand.EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB).build();
        EditPatientCommand expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Other valid values specified
        userInput = VALID_NAME_AMY + TEMP_DESC_BOB + INVALID_PHONE_DESC + PERIOD_DESC_BOB
                + PHONE_DESC_BOB;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_BOB).withTemperature(VALID_TEMP_BOB)
                .withPeriodOfStay(VALID_PERIOD_BOB).build();
        expectedCommand = new EditPatientCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
//@author LeeMingDe
