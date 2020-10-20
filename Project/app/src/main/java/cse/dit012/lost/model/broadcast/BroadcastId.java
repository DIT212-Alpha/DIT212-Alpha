package cse.dit012.lost.model.broadcast;

import androidx.annotation.NonNull;

import java.util.Objects;

import cse.dit012.lost.android.service.ActiveBroadcastService;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;
import cse.dit012.lost.service.broadcast.BroadcastService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a unique broadcast id.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link ActiveBroadcastService}, {@link Broadcast}, {@link BroadcastInfoWindowFragment}
 * {@link BroadcastRepository}, {@link BroadcastService}, BroadcastServiceImpl, {@link FirebaseBroadcastRepository}
 */
public final class BroadcastId {
    private final String id;

    /**
     * Creates a new {@link BroadcastId} from the given {@link String}.
     *
     * @param id the {@link String} to use for the identifier
     * @throws NullPointerException     if id is null
     * @throws IllegalArgumentException if id is empty
     */
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
