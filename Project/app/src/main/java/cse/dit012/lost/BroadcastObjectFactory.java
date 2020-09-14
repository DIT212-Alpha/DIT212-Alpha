package cse.dit012.lost;

public class BroadcastObjectFactory {
    public static BroadcastObject createBroadcastObject(String name){
        return new Course(name);
    }
}
