package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.room.Room;


public interface ReadOnlyRoomList {

    /**
     * Returns an unmodifiable view of the room list.
     */
    ObservableList<Room> getRoomList();

}
