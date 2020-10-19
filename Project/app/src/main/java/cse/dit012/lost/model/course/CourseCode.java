package cse.dit012.lost.model.course;

import androidx.annotation.NonNull;

import java.util.Objects;

import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;
import cse.dit012.lost.service.broadcast.BroadcastService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a unique course identifier.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link AddBroadcastFragment}, {@link Broadcast}, {@link BroadcastInfoWindowFragment},
 * {@link BroadcastService}, BroadcastServiceImpl, {@link FirebaseBroadcastRepository}
 */
public final class CourseCode {
    private final String courseCode;

    /**
     * Creates a new {@link CourseCode} from the given {@link String}.
     *
     * @param courseCode the {@link String} to use for the identifier
     * @throws NullPointerException     if courseCode is null
     * @throws IllegalArgumentException if courseCode is empty
     */
    public CourseCode(String courseCode) {
        this.courseCode = checkNotNull(courseCode);
        checkArgument(!courseCode.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCode that = (CourseCode) o;
        return courseCode.equals(that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

    /**
     * @return a string representation of the course identifier.
     */
    @NonNull
    @Override
    public String toString() {
        return courseCode;
    }
}