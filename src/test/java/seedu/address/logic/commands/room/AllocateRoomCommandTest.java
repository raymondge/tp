package seedu.address.logic.commands.room;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PATIENT_ALREADY_ASSIGNED;
import static seedu.address.logic.commands.room.AllocateRoomCommand.MESSAGE_ALLOCATE_ROOM_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientRecords;
import static seedu.address.testutil.TypicalRooms.getTypicalRoomList;
import static seedu.address.testutil.command.GeneralCommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.command.GeneralCommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RoomList;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.room.Room;
import seedu.address.testutil.AllocateRoomDescriptorBuilder;
import seedu.address.testutil.RoomBuilder;


//@@author chiamyunqing
/**
 * Contains integration tests and unit tests for AllocateRoomCommand.
 * Hardcoding getTypicalRoomList due to its unexpected behaviours
 */
class AllocateRoomCommandTest {

    //patient records -> [ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE]
    //Alice is in Room 7, Benson in Room 8, Room 11 with tasks
    private Model model =
            new ModelManager(getTypicalPatientRecords(), getTypicalRoomList(), new UserPrefs());

    @Test
    public void constructor_nullInputs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AllocateRoomCommand(null, null, false));
    }

    @Test
    public void execute_invalidRoomNumber_failure() {
        AllocateRoomCommand.AllocateRoomDescriptor descriptor = new AllocateRoomDescriptorBuilder().build();
        AllocateRoomCommand allocateRoomCommand = new AllocateRoomCommand(-100, descriptor, false);
        assertThrows(AssertionError.class, () -> allocateRoomCommand.execute(model));
    }

    @Test
    public void execute_changePatientInRoom_success() {
        Room roomToAllocatePatient = model.getRoomListObservableList().get(6);
        Integer roomNumberForAllocation = roomToAllocatePatient.getRoomNumber();
        Room editedRoom = new RoomBuilder(roomToAllocatePatient).withPatient(CARL).build();

        AllocateRoomCommand.AllocateRoomDescriptor descriptor = new AllocateRoomDescriptorBuilder()
                .withPatient(CARL.getName()).build();
        AllocateRoomCommand allocateRoomCommand = new AllocateRoomCommand(roomNumberForAllocation, descriptor, false);

        String expectedMessage = String.format(MESSAGE_ALLOCATE_ROOM_SUCCESS, editedRoom);
        Model expectedModel = new ModelManager(getTypicalPatientRecords(),
                new RoomList(model.getModifiableRoomList()), new UserPrefs());
        expectedModel.setSingleRoom(roomToAllocatePatient, editedRoom);

        assertCommandSuccess(allocateRoomCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearRoom_success() {
        Room roomToAllocatePatient = model.getRoomListObservableList().get(6);

        Integer roomNumberForAllocation = roomToAllocatePatient.getRoomNumber();
        Room allocatedRoom = new RoomBuilder(roomToAllocatePatient).withIsOccupied(false).withPatient(null).build();

        AllocateRoomCommand.AllocateRoomDescriptor descriptor = new AllocateRoomDescriptorBuilder()
                .withOccupancy(false).build();

        AllocateRoomCommand allocateRoomCommand = new AllocateRoomCommand(roomNumberForAllocation, descriptor, true);

        String expectedMessage = String.format(MESSAGE_ALLOCATE_ROOM_SUCCESS, allocatedRoom);
        Model expectedModel = new ModelManager(getTypicalPatientRecords(),
                new RoomList(model.getModifiableRoomList()), new UserPrefs());
        expectedModel.setSingleRoom(roomToAllocatePatient, allocatedRoom);

        assertCommandSuccess(allocateRoomCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allocatePatientAlreadyInAnotherRoom_failure() {
        Room roomToAllocatePatient = model.getRoomListObservableList().get(0); //the empty room
        Integer roomNumberForAllocation = roomToAllocatePatient.getRoomNumber();
        Patient patientInAnotherRoom = model.getRoomListObservableList().get(6).getPatient().get();

        AllocateRoomCommand.AllocateRoomDescriptor descriptor = new AllocateRoomDescriptorBuilder()
                .withPatient(patientInAnotherRoom.getName()).build();
        AllocateRoomCommand allocateRoomCommand = new AllocateRoomCommand(roomNumberForAllocation, descriptor, false);

        assertCommandFailure(allocateRoomCommand, model, MESSAGE_PATIENT_ALREADY_ASSIGNED);
    }

    @Test
    public void execute_invalidPatientAllocated_failure() {
        Room roomToAllocatePatient = model.getRoomListObservableList().get(0);
        Integer roomNumberForAllocation = roomToAllocatePatient.getRoomNumber();

        AllocateRoomCommand.AllocateRoomDescriptor descriptor = new AllocateRoomDescriptorBuilder()
                .withPatient(BOB.getName()).build();
        AllocateRoomCommand allocateRoomCommand = new AllocateRoomCommand(roomNumberForAllocation, descriptor, false);

        assertCommandFailure(allocateRoomCommand, model, Messages.MESSAGE_INVALID_PATIENT_NAME);
    }

    @Test
    public void equals() {
        Room allocateRoom7 = model.getRoomListObservableList().get(6);
        AllocateRoomCommand.AllocateRoomDescriptor descriptor1 = new AllocateRoomDescriptorBuilder(allocateRoom7)
                .withRoomNumber(20).build();
        AllocateRoomCommand allocateRoomNumberCommand = new AllocateRoomCommand(allocateRoom7.getRoomNumber(),
                descriptor1, false);

        Room allocateRoom8 = model.getRoomListObservableList().get(7);
        AllocateRoomCommand.AllocateRoomDescriptor descriptor2 = new AllocateRoomDescriptorBuilder(allocateRoom8)
                .withPatient(CARL.getName()).build();
        AllocateRoomCommand allocatePatientInRoomCommand = new AllocateRoomCommand(allocateRoom8.getRoomNumber(),
                descriptor2, false);

        // same object -> returns true
        assertTrue(allocateRoomNumberCommand.equals(allocateRoomNumberCommand));

        // same values -> returns true
        AllocateRoomCommand editRoomNumberCommandCopy = new AllocateRoomCommand(allocateRoom7.getRoomNumber(),
                descriptor1, false);
        assertTrue(allocateRoomNumberCommand.equals(editRoomNumberCommandCopy));

        // different types -> returns false
        assertFalse(allocateRoomNumberCommand.equals(1));

        // null -> returns false
        assertFalse(allocateRoomNumberCommand.equals(null));

        // different attributes to edit
        assertFalse(allocateRoomNumberCommand.equals(allocatePatientInRoomCommand));
    }
}
