package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private Image logoImage = new Image(this.getClass().getResourceAsStream("/images/covigent.png"));

    // attributes for tabs ----------------------
    private Image patientImage = new Image(this.getClass().getResourceAsStream("/images/patientlogo.png"));
    private Image roomImage = new Image(this.getClass().getResourceAsStream("/images/roomlogo.png"));
    private Image taskImage = new Image(this.getClass().getResourceAsStream("/images/tasklogo.png"));
    private final String patientTabContent = "PATIENTS";
    private final String roomTabContent = "ROOMS";
    private final String taskTabContent = "TASKS";

    // Independent Ui parts residing in this Ui container
    private PatientListPanel patientListPanel;
    private ResultDisplay resultDisplay;
    private RoomListPanel roomListPanel;
    private RoomTaskListPanel roomTaskListPanel;
    private HelpWindow helpWindow;

    @FXML
    private ImageView logoIcon;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane patientListPanelPlaceholder;

    @FXML
    private TabPane covigentTabs;

    @FXML
    private Tab patientTab;

    @FXML
    private Tab roomTab;

    @FXML
    private Tab taskTab;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusBarPlaceholder;

    @FXML
    private StackPane roomListPanelPlaceHolder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    //@@author chiamyunqing
    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        //set images
        this.setTabContent(patientTab, patientImage, patientTabContent);
        this.setTabContent(roomTab, roomImage, roomTabContent);
        this.setTabContent(taskTab, taskImage, taskTabContent);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    private void setTabContent(Tab tab, Image image, String text) {
        VBox content = new VBox();
        //set image
        ImageView icon = new ImageView(image);
        icon.setFitHeight(70);
        icon.setFitWidth(70);
        //set text
        Label label = new Label(text);
        label.setFont(Font.font("American Typewriter", FontWeight.BOLD, 15));
        //manually centre-align text
        if (!text.equals(patientTabContent)) {
            label.setPadding(new Insets(0, 0, 0, 10));
        }
        content.getChildren().addAll(icon, label);
        tab.setGraphic(content);
    }
    //@@author

    public void displayAppIcon() {
        logoIcon.setImage(logoImage);
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList());
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        roomListPanel = new RoomListPanel(logic.getFilteredRoomList());
        roomListPanelPlaceHolder.getChildren().add(roomListPanel.getRoot());

        roomTaskListPanel = new RoomTaskListPanel(logic.getFilteredRoomList());
        taskListPanelPlaceholder.getChildren().add(roomTaskListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getCovigentAppFilePath());
        statusBarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }


    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
