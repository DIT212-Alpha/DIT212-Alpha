package cse.dit012.lost;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Course> courses;

    public User(String name){
        this.name = name;
        courses = new ArrayList<>();
    }

    public User(String name,List<Course> courses){
        this.name = name;
        this.courses = courses;
    }

    public String getName(){
        return name;
    }

    public void changeName(String newName){
        name = newName;
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void deleteCourse(int i){
        courses.remove(i);
    }

}
