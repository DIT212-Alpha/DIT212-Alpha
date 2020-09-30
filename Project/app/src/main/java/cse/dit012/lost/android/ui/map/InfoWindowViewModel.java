package cse.dit012.lost.android.ui.map;

import androidx.lifecycle.ViewModel;

import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.broadcast.BroadcastRepository;

public class InfoWindowViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = BroadcastRepository.get();

    public void updateEditedInfoWindow(BroadcastId id, String course, String description) {
        broadcastRepository.updateCourseDescription(id, course, description);
    }
}
