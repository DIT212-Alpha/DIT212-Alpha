package cse.dit012.lost;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.Objects;

import static com.google.common.base.Preconditions.*;

public class Broadcast {
    private final BroadcastRepository broadcastRepository;

    private final String id;
    private Date lastActive;
    private final double latitude;
    private final double longitude;
    private final BroadcastObject course;
    private String description;

    Broadcast(BroadcastRepository broadcastRepository, String id, Date lastActive, double latitude, double longitude, BroadcastObject course, String description) {
        this.broadcastRepository = checkNotNull(broadcastRepository);
        this.id = checkNotNull(id);
        this.lastActive = checkNotNull(lastActive);
        this.course = checkNotNull(course);
        this.description = checkNotNull(description);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Date getLastActive() {
        return new Date(lastActive.getTime());
    }

    public void setLastActive(Date lastActive) {
        this.lastActive = new Date(checkNotNull(lastActive).getTime());
    }

    public BroadcastObject getCourse() {
        return course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = checkNotNull(description);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
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
                .add("id", id)
                .add("lastActive", lastActive)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .add("course", course)
                .add("description", description)
                .toString();
    }
}