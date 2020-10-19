package cse.dit012.lost.model.broadcast;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.model.user.UserId;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a single broadcast owned by a user and placed at a location on the map.
 * Author: Mathias Drage, Benjamin Sannholm, Sophia Pham
 */
public final class Broadcast {
    /**
     * How long ago a broadcast had to be last active to be considered alive, in seconds
     */
    public static final long ACTIVE_TIME_MARGIN_SECONDS = 60;
    // How far away the owner of a broadcast may move and still have the broadcast be considered active.
    private static final double BROADCAST_ACTIVATION_RADIUS_METERS = 20;

    private final BroadcastId id;
    private final UserId ownerUID;
    private final Date createdAt;
    private Date lastActive;
    private final MapCoordinates coordinates;
    private CourseCode course;
    private String description;

    /**
     * Creates a new {@link Broadcast} with the given properties.
     *
     * @param id          the {@link BroadcastId} of the {@link Broadcast}
     * @param ownerUID    the {@link UserId} who owns the {@link Broadcast}
     * @param createdAt   the {@link Date} at which the {@link Broadcast} was created
     * @param lastActive  the {@link Date} at which the {@link Broadcast} was last kept active
     * @param coordinates the {@link MapCoordinates} at which the {@link Broadcast} was created
     * @param course      the {@link CourseCode} of the course the {@link Broadcast} is for
     * @param description the user given description for the {@link Broadcast}
     */
    public Broadcast(BroadcastId id, UserId ownerUID, Date createdAt, Date lastActive, MapCoordinates coordinates, CourseCode course, String description) {
        this.id = checkNotNull(id);
        this.ownerUID = checkNotNull(ownerUID);
        this.createdAt = checkNotNull(createdAt);
        this.lastActive = checkNotNull(lastActive);
        this.coordinates = coordinates;
        this.course = checkNotNull(course);
        this.description = checkNotNull(description);
    }

    /**
     * @return the {@link BroadcastId} of the {@link Broadcast}
     */
    public BroadcastId getId() {
        return id;
    }

    /**
     * @return the {@link UserId} who owns the {@link Broadcast}
     */
    public UserId getOwnerUID() {
        return ownerUID;
    }

    /**
     * @return the {@link Date} at which the {@link Broadcast} was created
     */
    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    /**
     * @return the {@link Date} at which the {@link Broadcast} was last kept active
     */
    public Date getLastActive() {
        return new Date(lastActive.getTime());
    }

    /**
     * Updates the the broadcast's last active time to the current time.
     */
    public void updateLastActive() {
        this.lastActive = new Date(System.currentTimeMillis());
    }

    /**
     * Makes the broadcast permanently inactive.
     */
    public void setToInactive() {
        // Sets the last active time 70 seconds back, making the broadcast "inactive"
        this.lastActive = new Date(System.currentTimeMillis() - 70000);
    }

    /**
     * @return the {@link MapCoordinates} at which the {@link Broadcast} was created
     */
    public MapCoordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return the {@link CourseCode} of the course the {@link Broadcast} is for
     */
    public CourseCode getCourse() {
        return course;
    }

    /**
     * Updates which course the broadcast is for.
     *
     * @param course the {@link CourseCode} of the course
     */
    public void updateCourse(CourseCode course) {
        this.course = checkNotNull(course);
    }

    /**
     * @return the user given description for the {@link Broadcast}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the user given description for the broadcast.
     *
     * @param description the new description
     */
    public void updateDescription(String description) {
        this.description = checkNotNull(description);
    }

    /**
     * Determines whether the broadcast is still active at the given time.
     *
     * @param atTime the {@link Date} at which to check if the {@link Broadcast} is still active
     * @return true if the broadcast is still active, false otherwise
     */
    public boolean isActive(Date atTime) {
        long ageSinceLastActive = (atTime.getTime() - getLastActive().getTime()) / 1000;
        return ageSinceLastActive <= ACTIVE_TIME_MARGIN_SECONDS;
    }

    /**
     * Checks if a point is within range to keep the given broadcast alive.
     *
     * @param coords the coordinates of the point
     * @return true, if it is in range, false otherwise
     */
    public boolean isPointInRangeOfBroadcast(MapCoordinates coords) {
        return getCoordinates().distanceTo(coords) <= BROADCAST_ACTIVATION_RADIUS_METERS;
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
                .add("owner", getOwnerUID())
                .add("course", getCourse())
                .add("description", getDescription())
                .toString();
    }
}