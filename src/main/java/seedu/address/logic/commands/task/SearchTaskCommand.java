package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.task.TaskCliSyntax.PREFIX_DUE_DATE;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.Room;
import seedu.address.model.task.DateTimeDue;
import seedu.address.model.task.DueDatePredicate;
import seedu.address.model.task.Task;

/**
 * Searches a {@code Task} before a {@code DateTimeDue}.
 */
public class SearchTaskCommand extends Command {

    public static final String COMMAND_WORD = "searchtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches tasks in Covigent. "
            + "\nParameters: "
            + "[" + PREFIX_DUE_DATE + "DUE DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DUE_DATE + "20200928 2359";

    public static final String MESSAGE_SEARCH_TASK_SUCCESS = "Tasks before the due date found.";
    public static final String MESSAGE_TASK_NOT_FOUND = "There is no task that matches your criteria.";


    private final DateTimeDue duedate;
    private ObservableList<Task> tasks;
    private DueDatePredicate datePredicate;

    /**
     * Creates an SearchTaskCommand to Search the specified {@code Task} with {@code DateTimeDue}
     */
    public SearchTaskCommand(DateTimeDue duedate) {
        requireAllNonNull(duedate);
        this.duedate = duedate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Room> rooms = model.getRoomListObservableList();
        ArrayList<Task> taskListWithDesirableResult = new ArrayList<>();
        datePredicate = new DueDatePredicate(duedate);

        for (Room room : rooms) {
            tasks = room.getReadOnlyTasks();
            for (Task task : tasks) {
<<<<<<< HEAD
                if (task.getDueAt().compareTo(duedate) == 1
                        || task.getDueAt().compareTo(duedate) == 2) {
=======
                if (datePredicate.test(task)) {
>>>>>>> 349d37455c5b3d873938c5e434b1e830d5073c47
                    taskListWithDesirableResult.add(task);
                    break;
                }
            }
        }

        if (taskListWithDesirableResult.size() < 1) {
            model.updateFilteredTaskList(datePredicate);
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }
        assert taskListWithDesirableResult.size() >= 1;
        model.updateFilteredTaskList(datePredicate);
        return new CommandResult(String.format(MESSAGE_SEARCH_TASK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchTaskCommand
                && duedate.equals(((SearchTaskCommand) other).duedate));
    }
}
