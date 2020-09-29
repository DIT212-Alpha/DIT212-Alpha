package cse.dit012.lost.model;

import cse.dit012.lost.model.course.Course;

public class BroadcastObjectFactory {
    public static BroadcastObject createBroadcastObject(String name){
        return new Course(name);
    }
}
