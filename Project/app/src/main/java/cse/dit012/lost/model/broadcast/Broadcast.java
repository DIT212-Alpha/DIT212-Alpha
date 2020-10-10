package cse.dit012.lost.model.broadcast;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.User;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a single broadcast owned by a user and placed at a location on the map.
 * Author: Mathias Drage, Benjamin Sannholm, Sophia Pham
 */
public final class Broadcast {
    // How long ago a broadcast had to be last active to be considered alive, in seconds
    public static final long ACTIVE_TIME_MARGIN_SECONDS = 60;

    private final BroadcastId id;
    private final Date createdAt;
    private Date lastActive;
    private final MapCoordinates coordinates;
    private final User owner;
    private CourseCode course;
    private String description;

    public Broadcast(BroadcastId id, Date createdAt, Date lastActive, MapCoordinates coordinates, User owner, CourseCode course, String description) {
        this.id = checkNotNull(id);
        this.createdAt = checkNotNull(createdAt);
        this.lastActive = checkNotNull(lastActive);
        this.coordinates = coordinates;
        this.owner = checkNotNull(owner);
        this.course = checkNotNull(course);
        this.description = checkNotNull(description);
    }

    public BroadcastId getId() {
        return id;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public Date getLastActive() {
        return new Date(lastActive.getTime());
    }

    public void updateLastActive() {
        this.lastActive = new Date(System.currentTimeMillis());
    }

    public MapCoordinates getCoordinates() {
        return coordinates;
    }

    public String getOwner() {
        return owner.getName();
    }

    public CourseCode getCourse() {
        return course;
    }

    public String getDescription() {
        return description;
    }

    public void updateDescription(String description) {
        this.description = checkNotNull(description);
    }

    public void updateCourse(CourseCode course) {
        this.course = checkNotNull(course);
    }

    public boolean isActive(Date currentTime) {
        long ageSinceLastActive = (currentTime.getTime() - getLastActive().getTime()) / 1000;
        return ageSinceLastActive <= ACTIVE_TIME_MARGIN_SECONDS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Broadcast broadcast = (Broadcast) o;
        return id.equals(broadcast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("createdAt", getCreatedAt())
                .add("lastActive", getLastActive())
                .add("coordinates", getCoordinates())
                .add("owner", getOwner())
                .add("course", getCourse())
                .add("description", getDescription())
                .toString();
    }
}