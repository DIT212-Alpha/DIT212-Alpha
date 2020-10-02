package cse.dit012.lost.model.course;

import java.util.Objects;

import cse.dit012.lost.model.broadcast.Broadcast;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represents course code
 * AUTHOR: Benjamin Sannholm
 */

public final class CourseCode {
    private final String courseCode;

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
     * @return a string representation of the {@link Course} identifier.
     */
    @Override
    public String toString() {
        return courseCode;
    }
}
