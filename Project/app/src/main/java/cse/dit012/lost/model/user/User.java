package cse.dit012.lost.model.user;

import cse.dit012.lost.model.broadcast.Broadcast;

/**
 * Holds and manages user information.
 * Author: Mathias Drage
 * Used by: {@link Broadcast}
 */
public final class User {
    private String id;
    private String name;
    private String surname;

    public User(String id,String name) {
        this.name = name;
        this.id = id;
    }

    public User(String id,String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void changeName(String newName) {
        name = newName;
    }

    public void changeSurname(String newName){surname = newName;}

    public String getId(){
        return id;
    }

}
