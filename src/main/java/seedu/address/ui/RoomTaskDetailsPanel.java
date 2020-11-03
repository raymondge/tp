package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.room.Room;
import seedu.address.model.task.Task;

//@@author w-yeehong
/**
 * Panel containing the list of tasks in a room.
 */
public class RoomTaskDetailsPanel extends UiPart<Region> {

    public static final double LIST_CELL_HEIGHT = 90.0;

    private static final String FXML = "RoomTaskDetailsPanel.fxml";

    public final Room room;

    @FXML
    private VBox taskDetailsBox;
    @FXML
    private ListView<Task> taskListView;

    /**
     * Creates a RoomTaskDetailsPanel to display tasks in a room.
     *
     * @param room The room containing the task.
     */
    public RoomTaskDetailsPanel(Room room) {
        super(FXML);
        this.room = room;
        setTaskDetails(room.getFilteredTasks());
        setCellSize(room.getFilteredTasks());
    }

    /**
     * Sets {@code TaskListViewCell} in {@code taskListView} based on the tasks found in {@code taskList}.
     *
     * @param taskList The list of tasks to create the {@code TaskListViewCell}.
     */
    //@@author raymondge
    private void setTaskDetails(ObservableList<Task> taskList) {
        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        taskListView.getStyleClass().add("pretty-list-view");
    }
    //@@author raymondge

    /**
     * Sets a fixed size for each {@code TaskListViewCell}. This is necessary to dynamically set the height
     * of the cell.
     *
     * The height of each cell is given by {@code LIST_CELL_HEIGHT} multiplied by the number of tasks
     * in {@code taskList}.
     *
     * Solution adapted from
     * https://stackoverflow.com/questions/17429508/how-do-you-get-javafx-listview-to-be-the-height-of-its-items
     */
    private void setCellSize(ObservableList<Task> taskList) {
        taskListView.setFixedCellSize(LIST_CELL_HEIGHT);
        taskListView.prefHeightProperty().bind(Bindings.size(taskList).multiply(LIST_CELL_HEIGHT));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RoomTaskDetailsPanel // instanceof handles nulls
            && room.equals(((RoomTaskDetailsPanel) other).room)); // state check
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Task} using a {@code TaskCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                int roomNumber = room.getRoomNumber();
                int taskNumber = room.getFilteredTasks().getSourceIndex(getIndex()) + 1;
                int totalNumberOfTasksInRoom = room.getReadOnlyTasks().size();
                setGraphic(new TaskCard(roomNumber, taskNumber, totalNumberOfTasksInRoom, task)
                        .getRoot());
            }
        }
    }
}
