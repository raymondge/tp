package seedu.address.model.room;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskList;

/**
 * Wraps a {@code TaskList}.
 * Represents the tasks in a room.
 */
public class RoomTasks implements ReadOnlyList<Task> {

    private final TaskList tasks;
    private final FilteredList<Task> filteredTaskList;

    /**
     * Creates an empty list of tasks in the room.
     */
    public RoomTasks() {
        tasks = new TaskList();
        filteredTaskList = new FilteredList<>(tasks.asUnmodifiableObservableList());
    }

    /**
     * Creates a list of tasks in the room and populates it with the tasks in {@code roomTasks}.
     */
    public RoomTasks(List<Task> tasksToAdd) {
        requireAllNonNull(tasksToAdd);
        tasks = new TaskList();
        tasks.setTasks(tasksToAdd);
        filteredTaskList = new FilteredList<>(tasks.asUnmodifiableObservableList());
    }

    //// task-level operations

    /**
     * Returns the task with the provided {@code taskIndex} from the room.
     * An empty optional is returned if such a task is not found in the room.
     *
     * @param taskIndex The index of the task in the room.
     * @return the optional-wrapped task if found, otherwise an empty optional
     */
    public Optional<Task> getTaskWithTaskIndex(Index taskIndex) {
        requireNonNull(taskIndex);

        List<Task> tasks = getReadOnlyList();
        if (taskIndex.getZeroBased() >= tasks.size()) {
            return Optional.empty();
        }
        return Optional.of(tasks.get(taskIndex.getZeroBased()));
    }

    /**
     * Adds a task to the room.
     */
    public void addTask(Task t) {
        requireNonNull(t);
        tasks.add(t);
    }

    /**
     * Adds all tasks in {@code roomTasks} to the list.
     */
    public void addTasks(List<Task> tasksToAdd) {
        requireAllNonNull(tasksToAdd);
        tasks.add(tasksToAdd);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the room.
     */
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);
        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from {@code RoomTasks}.
     * {@code key} must exist in the room.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    /**
     * Returns true if a the room has no task.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    //// filtered-task operations

    /**
     * Returns the filtered list of tasks for this room.
     */
    public FilteredList<Task> getFilteredList() {
        return filteredTaskList;
    }

    /**
     * Sets the {@code predicate} to filter the tasks in this room.
     */
    public void setPredicate(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTaskList.setPredicate(predicate);
    }

    @Override
    public ObservableList<Task> getReadOnlyList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        if (isEmpty()) {
            builder.append("-");
            return builder.toString().trim();
        }

        int taskIndex = 1;
        for (Task task : getReadOnlyList()) {
            // Results in "1. <task>\n2. <task>..."
            builder.append(taskIndex++);
            builder.append(". ");
            builder.append(task);
            builder.append("\n\n");
        }

        return builder.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoomTasks // instanceof handles nulls
                && tasks.equals(((RoomTasks) other).tasks));
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
