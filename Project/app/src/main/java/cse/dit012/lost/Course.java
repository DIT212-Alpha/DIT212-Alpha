package cse.dit012.lost;

public class Course {
    private String name;

    public Course(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void changeName(String newName){
        name = newName;
    }

}
