package cse.dit012.lost.android.ui.screen.map;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cse.dit012.lost.Broadcast;
import cse.dit012.lost.BroadcastRepository;

public class MapViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = new BroadcastRepository();

    private LiveData<List<Broadcast>> broadcasts;

    public LiveData<List<Broadcast>> getActiveBroadcasts() {
        if (broadcasts == null) {
            broadcasts = broadcastRepository.getActiveBroadcasts();
        }
        return broadcasts;
    }
}
