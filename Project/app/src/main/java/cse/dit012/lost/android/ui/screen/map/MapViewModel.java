package cse.dit012.lost.android.ui.screen.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cse.dit012.lost.persistance.BroadcastRepositoryProvider;
import cse.dit012.lost.android.ui.map.LostMapFragment;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * View model handling data to be displayed on the map screen.
 * <p>
 * Author: Benjamin Sannholm, Bashar Oumari
 * Uses: {@link BroadcastRepositoryProvider}, {@link Broadcast}, {@link BroadcastRepository}
 * Used by: {@link LostMapFragment}, {@link MapScreenFragment}
 */
public final class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = BroadcastRepositoryProvider.get();
    private final MutableLiveData<String> courseCode = new MutableLiveData<>("");

    public String getCourseCode() {
        return courseCode.getValue();
    }

    public void setCourseCode(String course) {
        courseCode.setValue(checkNotNull(course));
    }

    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     *
     * @return the list of broadcasts
     */
    public LiveData<List<Broadcast>> getActiveBroadcastsFilteredByCourse() {
        MediatorLiveData<List<Broadcast>> mediatorLiveDataMerger = new MediatorLiveData<>();

        LiveData<List<Broadcast>> activeBroadcasts = broadcastRepository.observeActiveBroadcasts();

        Observer<Object> onInputsChanged = unused -> {
            // Only propagate changes to broadcast list when both course filter and broadcasts list is available
            if (activeBroadcasts.getValue() != null && courseCode.getValue() != null) {
                mediatorLiveDataMerger.setValue(filterBroadcastsOnCourse(activeBroadcasts.getValue(), courseCode.getValue()));
            }
        };
        // Trigger onInputsChanged either when list of broadcasts or course filter changes
        mediatorLiveDataMerger.addSource(activeBroadcasts, onInputsChanged);
        mediatorLiveDataMerger.addSource(courseCode, onInputsChanged);

        return mediatorLiveDataMerger;
    }

    /**
     * Takes a list of broadcasts, the a course to filter on and gives back the list of broadcasts
     * where broadcasts for other courses are filtered away.
     *
     * @param broadcasts the list of broadcasts
     * @param courseCode the course to filter on
     * @return a filtered list containing only relevant broadcasts
     */
    private static List<Broadcast> filterBroadcastsOnCourse(List<Broadcast> broadcasts, String courseCode) {
        boolean noCourseSelected = courseCode.isEmpty();
        if (noCourseSelected) {
            return broadcasts;
        }

        List<Broadcast> filteredBroadcasts = new ArrayList<>();
        for (Broadcast broadcast : broadcasts) {
            if (broadcast.getCourse().toString().equals(courseCode)) {
                filteredBroadcasts.add(broadcast);
            }
        }
        return filteredBroadcasts;
    }
}