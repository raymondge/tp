package seedu.address.model.task;

import java.util.function.Predicate;

/**
 * Tests that a {@code Task}'s {@code DateTimeDue} falls within the range.
 */
public class DueDatePredicate implements Predicate<Task> {
    private final DateTimeDue duedate;

    /**
     * Constructs a {@code DueDatePredicate}.
     *
     * @param duedate The start of temperature range.
     */
    public DueDatePredicate(DateTimeDue duedate) {
        assert duedate.value.isPresent();
        this.duedate = duedate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDatePredicate // instanceof handles nulls
                && duedate == (((DueDatePredicate) other).duedate)); // state check
    }

    @Override
    public boolean test(Task task) {
        if (task.getDueAt().value.isEmpty()) {
            return false;
        }

        if (duedate.compareTo(task.getDueAt()) >= 0) {
            return true;
        }

        return false;
    }
}
