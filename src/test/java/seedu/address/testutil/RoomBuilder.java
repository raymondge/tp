package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.model.patient.Patient;
import seedu.address.model.room.Room;
import seedu.address.model.room.RoomTasks;
import seedu.address.model.task.Task;

public class RoomBuilder {

    public static final int DEFAULT_ROOM_NUMBER = 10;
    public static final boolean DEFAULT_IS_OCCUPIED = true;
    public static final Patient DEFAULT_PATIENT = TypicalPatients.ALICE;

    private int roomNumber;
    private boolean isOccupied;
    private Patient patient;
    private RoomTasks roomTasks;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public RoomBuilder() {
        roomNumber = DEFAULT_ROOM_NUMBER;
        isOccupied = DEFAULT_IS_OCCUPIED;
        patient = DEFAULT_PATIENT;
        roomTasks = new RoomTasks();
    }

    /**
     * Initializes the RoomBuilder with the data of {@code roomToCopy}.
     *
     * The {@code RoomTasks} is a shallow copy of the original (i.e. the {@code Task} within
     * the list are not copied).
     */
    public RoomBuilder(Room roomToCopy) {
        roomNumber = roomToCopy.getRoomNumber();
        isOccupied = roomToCopy.isOccupied();
        patient = roomToCopy.getPatient().orElse(null);
        roomTasks = new RoomTasks(roomToCopy.getReadOnlyTasks()); // shallow copy
    }

    /**
     * Sets the {@code roomNumber} of the {@code Room} that we are building.
     */
    public RoomBuilder withRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }
    /**
     * Sets the {@code isOccupied} of the {@code Room} that we are building.
     */
    public RoomBuilder withIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
        return this;
    }

    /**
     * Sets the {@code patient} of the {@code Room} that we are building.
     */
    public RoomBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Sets the {@code RoomTasks} of the {@code Room} that we are building
     * with the provided tasks.
     */
    public RoomBuilder withTasks(Task... tasks) {
        RoomTasks roomTasks = new RoomTasks(Arrays.asList(tasks));
        this.roomTasks = roomTasks;
        return this;
    }

    /**
     * Sets the {@code RoomTasks} of the {@code Room} that we are building.
     */
    public RoomBuilder withTasks(RoomTasks roomTasks) {
        this.roomTasks = roomTasks;
        return this;
    }

    public Room build() {
        return new Room(roomNumber, isOccupied, Optional.ofNullable(patient), roomTasks);
    }
}

