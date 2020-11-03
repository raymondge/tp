package seedu.address.logic.commands.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATIENT_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ROOM_NUMBER;
import static seedu.address.commons.core.Messages.MESSAGE_PATIENT_NO_ROOM;
import static seedu.address.logic.parser.patient.PatientCliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.room.RoomCliSyntax.PREFIX_ROOM_NUMBER;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Name;

//@@author chiamyunqing
/**
 * Searches a room by the given room number or by the patient name who resides in the room.
 */
public class SearchRoomCommand extends Command {

    public static final String COMMAND_WORD = "searchroom";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches the room with the given room number or with the given individual.\n"
            + "Only one of the parameter can be provided.\n"
            + "Parameters: "
            + PREFIX_ROOM_NUMBER + "ROOM NUMBER or "
            + PREFIX_NAME + "NAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ROOM_NUMBER + "130 or "
            + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_SUCCESS = "Room has been found and listed.";
    private final SearchRoomDescriptor descriptor;

    /**
     * Creates a SearchRoomCommand to look for the specified room based on the inputs
     * in searchRoomDescriptor.
     *
     * @param descriptor Details to search the room with.
     */
    public SearchRoomCommand(SearchRoomDescriptor descriptor) {
        requireNonNull(descriptor);
        this.descriptor = descriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert(descriptor.hasParameter());

        if (descriptor.getRoomNumber().isPresent()) {
            Integer roomNumber = descriptor.roomNumber;
            Index index = model.checkIfRoomPresent(roomNumber);
            if (index.getZeroBased() == 0) {
                throw new CommandException(MESSAGE_INVALID_ROOM_NUMBER);
            }
            model.updateFilteredRoomList(room -> room.getRoomNumber() == roomNumber);
            return new CommandResult(MESSAGE_SUCCESS);

        }

        Name patientName = descriptor.patientName;
        if (model.getPatientWithName(patientName).isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_NAME);
        } else if (!model.isPatientAssignedToRoom(patientName)) {
            throw new CommandException(MESSAGE_PATIENT_NO_ROOM);
        }
        model.updateFilteredRoomList(room -> room.isOccupied()
                && room.getPatient().get().getName().equals(patientName));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchRoomCommand // instanceof handles nulls
                && descriptor.equals(((SearchRoomCommand) other).descriptor));
    }

    /**
     * Stores the details to search the room.
     * Each non-empty field value will replace the corresponding field value of the room.
     */
    public static class SearchRoomDescriptor {
        private Integer roomNumber;
        private Name patientName;

        public SearchRoomDescriptor() {
        }
        /**
         * Constructs a SearchRoomDescriptor object with the following fields.
         *
         * @param toCopy SearchRoomDescriptor to copy the fields from.
         */
        public SearchRoomDescriptor(SearchRoomDescriptor toCopy) {
            setRoomNumber(toCopy.roomNumber);
            setPatientName(toCopy.patientName);
        }

        public boolean hasParameter() {
            return CollectionUtil.isAnyNonNull(roomNumber, patientName);
        }

        public void setRoomNumber(Integer roomNumber) {
            this.roomNumber = roomNumber;
        }

        public Optional<Integer> getRoomNumber() {
            return Optional.ofNullable(roomNumber);
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

            if (!(other instanceof SearchRoomDescriptor)) { // instanceof handles nulls
                return false;
            }

            SearchRoomDescriptor e = (SearchRoomDescriptor) other; // state check
            return getRoomNumber().equals(e.getRoomNumber())
                    && getPatientName().equals(e.getPatientName());
        }
    }
}


