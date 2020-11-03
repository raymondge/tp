package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

//@@author chiamyunqing
/**
 * Represents important comments of the Patient in the app.
 * Guarantees: immutable; is an optional attribute of patient.
 */
public class Comment {
    public final String value;

    /**
     * Constructs a {@code Comment}.
     *
     * @param comment A comment that is represented by any string.
     */
    public Comment(String comment) {
        requireNonNull(comment);
        assert(comment.length() != 0);
        value = comment;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Comment // instanceof handles nulls
                && value.equals(((Comment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
