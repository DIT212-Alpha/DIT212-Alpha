package cse.dit012.lost.android.ui.screen.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.BroadcastRepository;
import cse.dit012.lost.Gps;
import cse.dit012.lost.User;

/**
 * View model handling data to be displayed on the map screen.
 */
public class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = new BroadcastRepository();
    public MutableLiveData<String> courseCode = new MutableLiveData<>();




    public MutableLiveData<String> getCurrentName() {

        if (courseCode == null) {
            courseCode = new MutableLiveData<>();
        }
        return courseCode;
    }

    public void setCourseCode(String coure){
        courseCode.setValue(coure);

    }




    /**
     * Retrieves an immutable live list of all currently active broadcasts.
     * @return the list of broadcasts
     */

    public LiveData<List<Broadcast>> getActiveBroadcasts() {

        MediatorLiveData<List<Broadcast>> mediatorLiveDataMerger = new MediatorLiveData<>();

        LiveData<List<Broadcast>> activebroadCasts = broadcastRepository.getActiveBroadcasts();

        mediatorLiveDataMerger.addSource(activebroadCasts, broadcasts -> {
            mediatorLiveDataMerger.setValue(checkForNull(activebroadCasts,courseCode));
        });

        mediatorLiveDataMerger.addSource(courseCode, broadcasts -> {
            mediatorLiveDataMerger.setValue(checkForNull(activebroadCasts,courseCode));
        });



            return mediatorLiveDataMerger;
    }


    public List<Broadcast> checkForNull(LiveData<List<Broadcast>> broadcasts,LiveData<String> liveData2){

        if (broadcasts.getValue() == null|| liveData2.getValue() == null){
            return Collections.emptyList();
        }

        List<Broadcast> filteredBroadcasts = new ArrayList<>();

        for (Broadcast broadcast : broadcasts.getValue()){

            if (broadcast.getCourse().getName().equals(liveData2.getValue())){

                filteredBroadcasts.add(broadcast);
            }
        }

        return filteredBroadcasts;
    }
}
