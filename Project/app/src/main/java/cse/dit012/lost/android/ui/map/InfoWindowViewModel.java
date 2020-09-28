package cse.dit012.lost.android.ui.map;

import androidx.lifecycle.ViewModel;

import cse.dit012.lost.BroadcastRepository;

public class InfoWindowViewModel extends ViewModel {
    private final BroadcastRepository broadcastRepository = new BroadcastRepository();

    public void updateEditedInfoWindow(String course, String description, String id) {
        broadcastRepository.updateCourseDescription(course, description, id);
    }
}
