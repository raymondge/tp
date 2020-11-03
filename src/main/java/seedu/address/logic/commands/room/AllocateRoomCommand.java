package seedu.address.logic.commands.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATIENT_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ROOM_NUMBER;
import static seedu.address.commons.core.Messages.MESSAGE_PATIENT_ALREADY_ASSIGNED;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.patient.PatientCliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomTasks;

//@@author LeeMingDe
/**
 * Edits the details of a room identified by the room number in the app.
 * Allows setting/removing/changing patient in the room and changing the room number of the room.
 */
public class AllocateRoomCommand extends Command {

    public static final String COMMAND_WORD = "allocateroom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocate a patient to a room. \n"
            + "Existing values will be overwritten by the input values. \n"
            + "To make an occupied room empty, the parameter for patient name should be set to '-'.\n"
            + "Parameters: ROOM NUMBER "
            + "[" + PREFIX_NAME + "PATIENT NAME]\n"
            + "Example: " + COMMAND_WORD + " 23 "
            + PREFIX_NAME + "Mary Doe";

    public static final String MESSAGE_ALLOCATE_ROOM_SUCCESS = "Allocated Room: %1$s";
    public static final String MESSAGE_DUPLICATE_ROOM = "The patient is already allocated to a room.";
    public static final String MESSAGE_NO_PATIENT_TO_REMOVE = "There is no patient in this room to remove.";

    private final Integer roomNumberToAllocate;
    private final AllocateRoomDescriptor allocateRoomDescriptor;
    private final boolean toRemove;

    /**
     * Constructs an AllocateRoomCommand to edit the room with the room number {@code Integer}.
     *
     * @param roomNumberToAllocate Room number to edit.
     * @param allocateRoomDescriptor Details to edit the room with.
     * @param toRemove Room if it is true.
     */
    public AllocateRoomCommand(Integer roomNumberToAllocate, AllocateRoomDescriptor allocateRoomDescriptor,
                               boolean toRemove) {
        requireAllNonNull(roomNumberToAllocate, allocateRoomDescriptor, toRemove);
        this.roomNumberToAllocate = roomNumberToAllocate;
        this.allocateRoomDescriptor = new AllocateRoomDescriptor(allocateRoomDescriptor);
        this.toRemove = toRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Room> roomOptional = model.getRoomWithRoomNumber(roomNumberToAllocate);

        if (roomOptional.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_ROOM_NUMBER);
        }

        Room roomToAllocate = roomOptional.get();
        Room roomWithAllocatedPatient = allocatePatientToRoom(model, roomToAllocate, allocateRoomDescriptor);

        if (!roomToAllocate.isSameRoom(roomWithAllocatedPatient) && model.hasRoom(roomWithAllocatedPatient)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.setSingleRoom(roomToAllocate, roomWithAllocatedPatient);
        return new CommandResult(String.format(MESSAGE_ALLOCATE_ROOM_SUCCESS, roomWithAllocatedPatient));
    }

    /**
     * Creates and returns a {@code Room} with the details of {@code roomToAllocate}
     * edited with {@code allocateRoomDescriptor}.
     *
     * @param model Current model.
     * @param roomToAllocate Room that is to be edited.
     * @param allocateRoomDescriptor Details to edit the room with.
     * @return Room that has been edited.
     */
    private Room allocatePatientToRoom(Model model, Room roomToAllocate,
                                       AllocateRoomDescriptor allocateRoomDescriptor) throws CommandException {
        assert (roomToAllocate != null);
        assert (allocateRoomDescriptor != null);

        int roomNumber = roomToAllocate.getRoomNumber();
        RoomTasks roomTaskList = new RoomTasks(roomToAllocate.getReadOnlyTasks());
        if (toRemove) {
            if (roomToAllocate.getPatient().isEmpty()) {
                throw new CommandException(MESSAGE_NO_PATIENT_TO_REMOVE);
            }
            return new Room(roomNumber, false, Optional.empty(), roomTaskList);
        }
        Name patientName = allocateRoomDescriptor.getPatientName().get(); //definitely has name
        if (model.isPatientAssignedToRoom(patientName)) {
            throw new CommandException(MESSAGE_PATIENT_ALREADY_ASSIGNED);
        }
        Optional<Patient> updatedPatient = model.getPatientWithName(patientName);
        if (updatedPatient.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_NAME);
        } else {
            Room updatedRoom = new Room(roomNumber, updatedPatient, roomTaskList);

            return updatedRoom;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { //short circuit if same object
            return true;
        }

        if (!(other instanceof AllocateRoomCommand)) { // instanceof handles nulls
            return false;
        }

        AllocateRoomCommand e = (AllocateRoomCommand) other; // state check
        return roomNumberToAllocate.equals(e.roomNumberToAllocate)
                && allocateRoomDescriptor.equals(e.allocateRoomDescriptor);
    }

    /**
     * Stores the details to edit the room with. Each non-empty field value will replace the
     * corresponding field value of the room.
     */
    public static class AllocateRoomDescriptor {
        private Integer roomNumber;
        private Boolean isOccupied;
        private Name patientName;

        public AllocateRoomDescriptor() {}

        /**
         * Constructs an AllocateRoomDescriptor object with the following fields.
         *
         * @param toCopy AllocateRoomDescriptor to copy the fields from.
         */
        public AllocateRoomDescriptor(AllocateRoomDescriptor toCopy) {
            setOccupied(toCopy.isOccupied);
            setPatientName(toCopy.patientName);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(patientName, isOccupied);
        }

        public void setRoomNumber(Integer roomNumber) {
            this.roomNumber = roomNumber;
        }

        public Optional<Integer> getRoomNumber() {
            return Optional.ofNullable(roomNumber);
        }

        public void setOccupied(Boolean isOccupied) {
            this.isOccupied = isOccupied;
        }

        public Optional<Boolean> getIsOccupied() {
            return Optional.ofNullable(isOccupied);
        }

        public void setPatientName(Name patientName) {
            this.patientName = patientName;
        }

        public Optional<Name> getPatientName() {
            return Optional.ofNullable(patientName);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) { // short circuit if same object
                return true;
            }

            if (!(other instanceof AllocateRoomDescriptor)) { // instanceof handles nulls
                return false;
            }

            AllocateRoomDescriptor e = (AllocateRoomDescriptor) other; // state check
            return getIsOccupied().equals(e.getIsOccupied())
                    && getPatientName().equals(e.getPatientName());
        }

        @Override
        public String toString() {
            return "AllocateRoomDescriptor{"
                    + ", isOccupied=" + isOccupied
                    + ", patientName=" + patientName
                    + '}';
        }
    }

    @Override
    public String toString() {
        return "AllocateRoomCommand{"
                + "roomNumberToEdit=" + roomNumberToAllocate
                + ", allocateRoomDescriptor=" + allocateRoomDescriptor
                + '}';
    }
    //@@author LeeMingDe
}
