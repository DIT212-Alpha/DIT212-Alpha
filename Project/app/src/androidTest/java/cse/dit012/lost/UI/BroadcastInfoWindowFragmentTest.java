package cse.dit012.lost.UI;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import cse.dit012.lost.android.ui.map.BroadcastInfoWindowFragment;
import cse.dit012.lost.service.BroadcastService;
import cse.dit012.lost.model.broadcast.BroadcastId;
import cse.dit012.lost.model.course.CourseCode;
import cse.dit012.lost.service.BroadcastService;

class BroadcastInfoWindowFragmentTest {

    //The course displayed should be the same as the one in the database
    @Test
    public void onViewCreatedCourse() {
        BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();

    }

    //The description displayed should be the same as the one in the database
    @Test
    public void onViewCreatedDescription() {

    }

    //The course should not change in the View after clicking the cancel button
    @Test
    public void cancelCourseInfoWindowView() {
        BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        //TODO click cancel
        //broadcastInfoWindowFragment.getView().cancelInfoWindowButton;
        String courseAfterClick = broadcastInfoWindowFragment.getCourse();
        String courseExpected = "DIT000";
        assertEquals(courseExpected, courseAfterClick);
    }

    //The description should not change in the View after clicking the cancel button
    @Test
    public void cancelDescriptionInfoWindowView() {

    }

    //The course should change to the right course in the View after clicking the save button
    @Test
    public void saveCourseInfoWindowView() {

    }

    //The description should change to the right description in the View after clicking the save button
    @Test
    public void saveDescriptionInfoWindowView() {

    }


    //The course edited and then canceled should be not be changed in the database
    @Test
    public void cancelCourseInfoWindowDatabase() {

    }

    //The description edited and then canceled should be not be changed in the database
    @Test
    public void cancelDescriptionInfoWindowDatabase() {

    }


    //The course edited should be updated in the database
    @Test
    public void editCourseInfoWindowDatabase() {
        BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        BroadcastService broadcastService = BroadcastService.get();
        String courseInput = "DIT123";
        //TODO get the updated value
        String courseUpdated = broadcastInfoWindowFragment.broadcastService.updateBroadcastEdit(new BroadcastId(id), new CourseCode(courseInput), "");
        String courseExpected = "DIT123";
        assertEquals(courseExpected, courseUpdated);
    }

    //The description edited should be updated in the database
    @Test
    public void editDescriptionInfoWindowDatabase() {
        BroadcastInfoWindowFragment broadcastInfoWindowFragment = new BroadcastInfoWindowFragment();
        BroadcastService broadcastService = BroadcastService.get();
        String descriptionInput = "I am studying here!";
        //TODO get the updated value
        String descriptionUpdated = broadcastInfoWindowFragment.broadcastService.updateBroadcastEdit(new BroadcastId(id), new CourseCode(""), descriptionInput);
        String descriptionExpected = "DIT123";
        assertEquals(descriptionExpected, descriptionUpdated);
    }


}