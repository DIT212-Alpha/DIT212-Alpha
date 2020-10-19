package cse.dit012.lost.model.user;

import androidx.annotation.NonNull;

import java.util.Objects;

import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.android.ui.screen.map.AddBroadcastFragment;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.persistance.firebase.FirebaseBroadcastRepository;
import cse.dit012.lost.service.authenticateduser.AuthenticatedUserService;
import cse.dit012.lost.service.broadcast.BroadcastService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of a unique user identifier.
 * <p>
 * Author: Benjamin Sannholm
 * Used by: {@link AddBroadcastFragment}, {@link AuthenticatedUserService}, {@link Broadcast},
 * {@link BroadcastInfoWindowFragment}, {@link BroadcastService}, BroadcastServiceImpl,
 * FirebaseAuthenticatedUserService, {@link FirebaseBroadcastRepository}
 */
public final class UserId {
    private final String id;

    /**
     * Creates a new {@link UserId} from the given {@link String}.
     *
     * @param id the {@link String} to use for the identifier
     * @throws NullPointerException     if id is null
     * @throws IllegalArgumentException if id is empty
     */
    public UserId(String id) {
        this.id = checkNotNull(id);
        checkArgument(!id.isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId that = (UserId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * @return a string representation of the user identifier.
     */
    @NonNull
    @Override
    public String toString() {
        return id;
    }
}