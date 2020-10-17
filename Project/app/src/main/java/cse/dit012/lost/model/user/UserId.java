package cse.dit012.lost.model.user;

import androidx.annotation.NonNull;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Representation of a unique user id.
 * Author: Benjamin Sannholm
 */
public final class UserId {
    private final String id;

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
     * @return a string representation of the {@link User} identifier.
     */
    @NonNull
    @Override
    public String toString() {
        return id;
    }
}
