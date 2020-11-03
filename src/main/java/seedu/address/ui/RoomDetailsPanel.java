package seedu.address.ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.room.Room;

//@@author LeeMingDe
/**
 * Panel containing the room details.
 */
public class RoomDetailsPanel extends UiPart<Region> {
    private static final String FXML = "RoomDetailsPanel.fxml";
    private Image imagePatient = new Image(getClass().getResourceAsStream("/images/patient_icon.png"));
    private Image imageTask = new Image(getClass().getResourceAsStream("/images/task_icon.png"));
    private final Logger logger = LogsCenter.getLogger(RoomDetailsPanel.class);

    @FXML
    private Label patientHeader;

    @FXML
    private Label taskHeader;

    @FXML
    private Label roomNumber;

    @FXML
    private Label patientDetails;

    @FXML
    private Label taskDetails;

    /**
     * Creates a {@code RoomDetailsPanel} with the given {@code Room}.
     */
    public RoomDetailsPanel() {
        super(FXML);
        setIcons();
    }

    private void setIcons() {
        ImageView imageView = new ImageView(imagePatient);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        patientHeader.setGraphic(imageView);
        imageView = new ImageView(imageTask);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        taskHeader.setGraphic(imageView);
    }

    /**
     * Sets the room patient and task details.
     *
     * @param room To set the details for.
     */
    public void setRoomDetails(Room room) {
        logger.log(Level.INFO, "Set non-empty room details successfully.");
        if (room.getPatient().isPresent()) {
            patientDetails.setText(room.getPatient().get().toString());
        } else {
            patientDetails.setText("No Patient Present.");
        }
        roomNumber.setText("Room #" + room.getRoomNumber());
        taskDetails.setText(room.getPrintFriendlyTaskSummary());
    }

    /**
     * Sets the details for an empty room.
     */
    public void setEmptyRoomDetails() {
        logger.log(Level.INFO, "Set empty room details successfully.");
        roomNumber.setText("NO ROOM PRESENT");
        patientDetails.setText("-");
        taskDetails.setText("-");
    }
    //@@author LeeMingDe
}
