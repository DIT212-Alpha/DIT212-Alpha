package cse.dit012.lost;

import androidx.lifecycle.LiveData;
import java.util.List;

public interface BroadcastRepository {
    /**
     * Retrieves a live list of all currently active broadcasts.
     * @return the list of broadcasts
     */
    LiveData<List<Broadcast>> getActiveBroadcasts();

    /**
     * Creates a new broadcast placed at the given coordinates for a specific course and with a given description.
     * @param latitude latitude part of coordinate
     * @param longitude longitude part of coordinate
     * @param course the course the broadcast if for
     * @param description the description of the broadcast
     * @return the newly created broadcast
     */
    Broadcast createBroadcast(double latitude,
                              double longitude,
                              BroadcastObject course,
                              String description);
}