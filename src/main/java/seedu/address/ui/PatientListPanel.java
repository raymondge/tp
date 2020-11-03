package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.patient.Patient;

//@@author chiamyunqing-reused
//Reused from RoomListPanel class
/**
 * Panel containing the list of patients.
 */
public class PatientListPanel extends UiPart<Region> {
    private static final String FXML = "PatientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientListPanel.class);

    private PatientDetailsPanel patientDetailsPanel;

    @FXML
    private ListView<Patient> patientListView;

    @FXML
    private AnchorPane patientDetailsPanelPlaceholder;

    /**
     * Creates a {@code PatientListPanel} with the given {@code ObservableList}.
     */
    public PatientListPanel(ObservableList<Patient> patientList) {
        super(FXML);
        updateDetailsIfChanged(patientList);
        patientListView.setItems(patientList);
        patientListView.setCellFactory(listView -> new PatientListViewCell());
    }

    /**
     * Attach listener to {@code patientList} and update details panel.
     * Fixes issue of editPatient changes not immediately reflected.
     *
     * @param patientList to listen for changes.
     */
    private void updateDetailsIfChanged(ObservableList<Patient> patientList) {
        patientList.addListener(new ListChangeListener<Patient>() {
            @Override
            public void onChanged(Change<? extends Patient> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        int indexToChange = change.getFrom();
                        Patient patientToDisplay = change.getList().get(indexToChange);
                        patientListView.scrollTo(indexToChange);
                        patientListView.getSelectionModel().select(indexToChange);
                        patientListView.getFocusModel().focus(indexToChange);
                        patientDetailsPanel = new PatientDetailsPanel(patientToDisplay);
                        patientDetailsPanelPlaceholder.getChildren().setAll(patientDetailsPanel.getRoot());
                    }
                }
            }
        });
    }

    /**
     * Handles mouse click event on the patient card.
     *
     * @param mouseEvent created by the user
     */
    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        Patient patientToDisplay = patientListView.getSelectionModel().getSelectedItem();
        patientDetailsPanel = new PatientDetailsPanel(patientToDisplay);
        patientDetailsPanelPlaceholder.getChildren().setAll(patientDetailsPanel.getRoot());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);
            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatientCard(patient, getIndex() + 1).getRoot());
            }
        }
    }
}
