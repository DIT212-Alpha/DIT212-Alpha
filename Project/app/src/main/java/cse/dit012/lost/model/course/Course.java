package cse.dit012.lost.model.course;

import cse.dit012.lost.model.BroadcastObject;

/**
 * Representation of a single course with a name.
 * Author: Mathias Drage
 */
public final class Course implements BroadcastObject {
    // TODO: Use CourseCode as identifier or remove class if fixed course entities are not needed.

    private String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName) {
        name = newName;
    }
}