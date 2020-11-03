package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Patient;
import seedu.address.model.room.Room;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PatientRecords patientRecords;
    private final RoomList roomList;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private final FilteredList<Room> filteredRooms;

    /**
     * Initializes a ModelManager with the given patient records, room records and userPrefs.
     */
    public ModelManager(ReadOnlyList<Patient> patientRecords, ReadOnlyList<Room> roomList,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(patientRecords, userPrefs);

        logger.fine("Initializing with Covigent App: " + patientRecords + " and user prefs " + userPrefs);

        this.patientRecords = new PatientRecords(patientRecords);
        this.roomList = new RoomList(roomList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.patientRecords.getReadOnlyList());
        filteredRooms = new FilteredList<>(this.roomList.getReadOnlyList());
    }

    public ModelManager() {
        this(new PatientRecords(), new RoomList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCovigentAppFilePath() {
        return userPrefs.getCovigentAppFilePath();
    }

    @Override
    public void setCovigentAppFilePath(Path covigentAppFilePath) {
        requireNonNull(covigentAppFilePath);
        userPrefs.setCovigentAppFilePath(covigentAppFilePath);
    }

    //=========== Patient Records ================================================================================

    @Override
    public void setPatientRecords(ReadOnlyList<Patient> patientRecords) {
        this.patientRecords.resetData(patientRecords);
    }

    @Override
    public ReadOnlyList<Patient> getPatientRecords() {
        return patientRecords;
    }

    //=========== RoomList ================================================================================

    @Override
    public void setRoomList(ReadOnlyList<Room> rooms) {
        this.roomList.resetData(rooms);
    }

    //=========== Patients ====================================================================================

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patientRecords.hasPatient(patient);
    }

    //@@author chiamyunqing
    @Override
    public Optional<Patient> getPatientWithName(Name nameOfPatient) {
        requireNonNull(nameOfPatient);
        return patientRecords.getPatientWithName(nameOfPatient);
    }

    @Override
    public void deletePatient(Patient target) {
        patientRecords.removePatient(target);
        //model's responsibility to update room list when patient is updated
        this.updateRoomListWhenPatientsChanges(target, null);
    }
    //@@author chiamyunqing

    @Override
    public void addPatient(Patient patient) {
        patientRecords.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);
        patientRecords.setPatient(target, editedPatient);
    }

    //@@author LeeMingDe
    @Override
    public boolean isPatientAssignedToRoom(Name name) {
        requireNonNull(name);
        for (Room room : roomList.getRoomObservableList()) {
            if (room.getPatient().isPresent()) {
                Name patientNameInRoom = room.getPatient().get().getName();
                if (patientNameInRoom.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    //@@author LeeMingDe

    //=========== Filtered Patient List Accessors =============================================================

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    //=========== Room List ========================================================================================

    @Override
    public int numOfOccupiedRooms() {
        return roomList.numOfOccupiedRooms();
    }

    @Override
    public boolean hasSpaceForRooms() {
        return roomList.hasSpaceForRooms();
    }
    @Override
    public int getNumOfRooms() {
        return roomList.getNumOfRooms();
    }

    public void setInitNumOfRooms(int numOfRooms) {
        roomList.setNumOfRooms(numOfRooms);
    }
    @Override
    public void addRooms(int num) {
        roomList.addRooms(num);
    }

    //@@author LeeMingDe
    @Override
    public boolean hasRoom(Room room) {
        requireNonNull(room);
        return roomList.containsRoom(room);
    }

    @Override
    public void setSingleRoom(Room target, Room editedRoom) {
        requireAllNonNull(target, editedRoom);
        roomList.setSingleRoom(target, editedRoom);
    }
    //@@author LeeMingDe

    //@@author chiamyunqing
    @Override
    public void removePatientFromRoom(Name patientName) {
        assert (isPatientAssignedToRoom(patientName));
        roomList.removePatientFromRoom(patientName);
    }
    //@@author chiamyunqing

    //@@author LeeMingDe
    @Override
    public Index checkIfRoomPresent(Integer roomNumber) {
        ObservableList<Room> roomObservableList = this.getRoomListObservableList();
        Index index = Index.fromZeroBased(0);
        for (int i = 1; i <= roomObservableList.size(); i++) {
            int roomNum = roomObservableList.get(i - 1).getRoomNumber();
            boolean isValidRoom = (Integer.valueOf(roomNum)).equals(roomNumber);
            if (isValidRoom) {
                index = Index.fromZeroBased(i);
                break;
            }
        }
        return index;
    }

    @Override
    public void updateRoomListWhenPatientsChanges(Patient patientToEdit, Patient editedPatient) {
        requireNonNull(patientToEdit);
        ObservableList<Room> roomObservableList = this.roomList.getRoomObservableList();
        for (int i = 0; i < roomObservableList.size(); i++) {
            Optional<Patient> patient = roomObservableList.get(i).getPatient();
            if (isPatientAssignedToRoom(patientToEdit.getName()) && patient.isPresent()
                    && patient.get().isSamePatient(patientToEdit)) {
                Room updatedRoom = roomObservableList.get(i);
                if (editedPatient == null) {
                    updatedRoom.setOccupied(false);
                }
                updatedRoom.setPatient(editedPatient);
                roomObservableList.set(i, updatedRoom);
                break;
            }
        }
    }
    //@@author LeeMingDe

    //@@author w-yeehong
    @Override
    public Optional<Room> getRoomWithRoomNumber(int roomNumber) {
        assert (roomNumber > 0) : "Room number should be greater than 0.";
        return roomList.getRoomWithRoomNumber(roomNumber);
    }
    //@@author w-yeehong

    //=========== Filtered RoomList Accessors ==========================================================================

    @Override
    public ObservableList<Room> getRoomListObservableList() {
        return roomList.getReadOnlyList();
    }

    @Override
    public RoomList getModifiableRoomList() {
        return roomList;
    }

    @Override
    public PriorityQueue<Room> getRooms() {
        return this.getModifiableRoomList().getRooms();
    }

    @Override
    public ObservableList<Room> getFilteredRoomList() {
        return filteredRooms;
    }

    @Override
    public void updateFilteredRoomList(Predicate<Room> predicate) {
        requireNonNull(predicate);
        filteredRooms.setPredicate(predicate);

    }

    //=========== Tasks ========================================================================================

    //@@author w-yeehong
    @Override
    public Optional<Task> getTaskFromRoomWithTaskIndex(Index taskIndex, Room room) {
        requireAllNonNull(taskIndex, room);
        return room.getTaskWithTaskIndex(taskIndex);
    }

    @Override
    public void addTaskToRoom(Task task, Room room) {
        requireAllNonNull(task, room);
        assert roomList.containsRoom(room) : "Room must be one of the rooms in the RoomList.";
        room.addTask(task);
    }

    @Override
    public void deleteTaskFromRoom(Task task, Room room) {
        requireAllNonNull(task, room);
        assert roomList.containsRoom(room) : "Room must be one of the rooms in the RoomList.";
        room.deleteTask(task);
    }

    @Override
    public void setTaskToRoom(Task target, Task editedTask, Room room) {
        requireAllNonNull(target, editedTask, room);
        assert roomList.containsRoom(room) : "Room must be one of the rooms in the RoomList.";
        room.setTask(target, editedTask);
    }

    //@@author w-yeehong

    @Override
    public void updateFilteredTaskList(Predicate<Task> datePredicate) {
        for (int i = 0; i < roomList.getNumOfRooms(); i++) {
            roomList.getRoomObservableList().get(i).setPredicateOnRoomTasks(datePredicate);
        }
    }

    //=========== Miscellaneous ========================================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;

        return patientRecords.equals(other.patientRecords)
                && roomList.equals(other.roomList)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients)
                && filteredRooms.equals(other.filteredRooms);
    }
}
