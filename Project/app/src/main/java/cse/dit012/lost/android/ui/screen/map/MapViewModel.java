package cse.dit012.lost.android.ui.screen.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.model.broadcast.BroadcastRepository;

/**
 * View model handling data to be displayed on the map screen.
 */
public class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = BroadcastRepository.create();
    public MutableLiveData<String> courseCode = new MutableLiveData<>();

    public MutableLiveData<String> getCurrentName() {
        return courseCode;
    }

    public void setCourseCode(String course){
        courseCode.setValue(course);
    }

    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     * @return the list of broadcasts
     */
    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        MediatorLiveData<List<Broadcast>> mediatorLiveDataMerger = new MediatorLiveData<>();

        LiveData<List<Broadcast>> activeBroadcasts = broadcastRepository.getActiveBroadcasts();

        mediatorLiveDataMerger.addSource(activeBroadcasts, broadcasts -> {
            mediatorLiveDataMerger.setValue(filterBroadcastsOnCourse(activeBroadcasts, courseCode));
        });
        mediatorLiveDataMerger.addSource(courseCode, broadcasts -> {
            mediatorLiveDataMerger.setValue(filterBroadcastsOnCourse(activeBroadcasts, courseCode));
        });

        return mediatorLiveDataMerger;
    }

    public List<Broadcast> filterBroadcastsOnCourse(LiveData<List<Broadcast>> broadcasts, LiveData<String> courseCode) {
        if (broadcasts.getValue() == null || courseCode.getValue() == null){
            return Collections.emptyList();
        }

        List<Broadcast> filteredBroadcasts = new ArrayList<>();
        for (Broadcast broadcast : broadcasts.getValue()) {
            if (broadcast.getCourse().toString().equals(courseCode.getValue())) {
                filteredBroadcasts.add(broadcast);
            }
        }
        return filteredBroadcasts;
    }
}
