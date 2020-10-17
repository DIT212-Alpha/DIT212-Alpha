package cse.dit012.lost.model.broadcast;

import androidx.annotation.NonNull;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class represent a unique broadcast id.
 * Author: Benjamin Sannholm
 */
public final class BroadcastId {
    private final String id;

    public BroadcastId(String id) {
        this.id = checkNotNull(id);
        checkArgument(!id.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BroadcastId that = (BroadcastId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return a string representation of the {@link Broadcast} identifier.
     */
    @NonNull
    @Override
    public String toString() {
        return id;
    }
}
