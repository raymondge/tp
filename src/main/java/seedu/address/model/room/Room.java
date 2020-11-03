package seedu.address.model.room;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.patient.Patient;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents Room in the app
 */
public class Room {

    private int roomNumber;
    private boolean isOccupied;
    private Optional<Patient> patient;
    private RoomTasks tasks;

    /**
     * Creates room object where isOccupied is always false
     */
    public Room(int roomNumber) {
        requireAllNonNull(roomNumber);
        this.roomNumber = roomNumber;
        this.isOccupied = false;
        this.patient = Optional.empty();
        tasks = new RoomTasks();
    }

    /**
     * Creates room object where roomNumber and isOccupied values are values given by user
     */
    public Room(int roomNumber, boolean isOccupied) {
        requireAllNonNull(roomNumber, isOccupied);
        this.roomNumber = roomNumber;
        this.isOccupied = isOccupied;
        this.patient = Optional.empty();
        tasks = new RoomTasks();
    }

    /**
     * Creates a room object containing a patient that can be found in the application.
     *
     * @param roomNumber Room Number of the room.
     * @param patient Patient to be added to the room.
     * @param tasks RoomTasks containing tasks for the room.
     */
    public Room(int roomNumber, Optional<Patient> patient, RoomTasks tasks) {
        requireAllNonNull(roomNumber, patient, tasks);
        this.roomNumber = roomNumber;
        this.isOccupied = true;
        this.patient = patient;
        this.tasks = tasks;
    }

    /**
     * Creates a Room object where none of the values are pre determined by app
     */
    public Room(int roomNumber, boolean isOccupied, Optional<Patient> patient, RoomTasks tasks) {
        requireAllNonNull(roomNumber, isOccupied, patient, tasks);
        this.roomNumber = roomNumber;
        this.isOccupied = isOccupied;
        this.patient = patient;
        this.tasks = tasks;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    //// patient

    public Optional<Patient> getPatient() {
        return this.patient;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public void setPatient(Patient patient) {
        this.patient = Optional.ofNullable(patient);
    }

    //// tasks

    /**
     * Returns an unmodifiable version of the list of tasks in this room as an {@code ObservableList}.
     */
    public ObservableList<Task> getReadOnlyTasks() {
        return tasks.getReadOnlyList();
    }

    /**
     * Returns a filtered list as an {@code FilteredList}.
     */
    public FilteredList<Task> getFilteredTasks() {
        return tasks.getFilteredList();
    }

    /**
     * Returns an unmodifiable version of the list of tasks in this room as a {@code ReadOnlyList}.
     */
    public ReadOnlyList<Task> getReadOnlyList() {
        return tasks;
    }

    /**
     * Returns the task with the provided {@code taskIndex} from this room.
     * An empty optional is returned if such a task is not found in the room.
     *
     * @param taskIndex The index of the task in this room.
     * @return the optional-wrapped task if found, otherwise an empty optional
     */
    public Optional<Task> getTaskWithTaskIndex(Index taskIndex) {
        return tasks.getTaskWithTaskIndex(taskIndex);
    }

    /**
     * Adds a task to the task list of this room.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.addTask(task);
    }

    /**
     * Adds tasks in {@code roomTasks} to this room.
     *
     * @param roomTasks The tasks to add.
     */
    public void addTasks(List<Task> roomTasks) {
        tasks.addTasks(roomTasks);
    }

    /**
     * Deletes a task from the task list of this room.
     * The task must be in this room.
     *
     * @param task The task to delete.
     * @throws TaskNotFoundException if task is not found in the task list of this room.
     */
    public void deleteTask(Task task) {
        try {
            tasks.removeTask(task);
        } catch (TaskNotFoundException e) {
            throw e;
        }
    }

    /**
     * Replaces a task from the task list of this room with {@code editedTask}.
     * The task must be in this room.
     *
     * @param target The task to replace.
     * @param editedTask The modified task to replace the original.
     * @throws TaskNotFoundException if task is not found in the task list of this room.
     */
    public void setTask(Task target, Task editedTask) {
        try {
            tasks.setTask(target, editedTask);
        } catch (TaskNotFoundException e) {
            throw e;
        }
    }

    /**
     * Sets the {@code predicate} to filter the tasks in this room.
     */
    public void setPredicateOnRoomTasks(Predicate<Task> predicate) {
        tasks.setPredicate(predicate);
    }

    /**
     * Enumerates the tasks in this room, numbering and specifying the details of each task.
     *
     * @return A print-friendly summary for the tasks in this room.
     */
    public String getPrintFriendlyTaskSummary() {
        return tasks.toString();
    }

    /**
     * Returns true if both rooms of the same number have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two rooms.
     */
    public boolean isSameRoom(Room otherRoom) {
        if (otherRoom == this) {
            return true;
        }

        return otherRoom != null
                && Integer.valueOf(otherRoom.getRoomNumber()).equals(getRoomNumber());
    }

    /**
     * Returns true if both rooms have the same identity and data fields.
     * This defines a stronger notion of equality between two rooms.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Room room = (Room) o;
        if (patient.isEmpty()) {
            return roomNumber == room.roomNumber
                    && room.patient.isEmpty()
                    && isOccupied == room.isOccupied
                    && tasks.equals(room.tasks);
        } else {
            return roomNumber == room.roomNumber
                    && isOccupied == room.isOccupied
                    && patient.equals(room.getPatient())
                    && tasks.equals(room.tasks);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, isOccupied, tasks);
    }


    @Override
    public String toString() {
        String patientDetails = getPatient().isEmpty() ? "-" : getPatient().toString();
        final StringBuilder builder = new StringBuilder();
        builder.append("Room Number: ")
                .append(getRoomNumber() + "\n")
                .append("Patient: ")
                .append(patientDetails + "\n")
                .append("Tasks: ")
                .append(tasks.toString() + "\n");
        return builder.toString();
    }
}
