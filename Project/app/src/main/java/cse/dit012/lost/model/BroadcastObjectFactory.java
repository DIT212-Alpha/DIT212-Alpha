package cse.dit012.lost.model;

import cse.dit012.lost.model.course.Course;

/**
 * Author: Mathias Drage
 */
public final class BroadcastObjectFactory {
    private BroadcastObjectFactory() {
    }

    public static BroadcastObject createBroadcastObject(String name) {
        return new Course(name);
    }
}