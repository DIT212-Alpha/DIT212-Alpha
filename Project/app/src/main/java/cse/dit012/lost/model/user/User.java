package cse.dit012.lost.model.user;

import cse.dit012.lost.model.broadcast.Broadcast;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Holds and manages user information.
 * Author: Mathias Drage
 * Used by: {@link Broadcast}
 */
public final class User {
    private final String id;
    private String name;
    private String surname;

    public User(String id, String name, String surname) {
        this.id = id;
        this.name = checkNotNull(name);
        this.surname = checkNotNull(surname);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String newName) {
        name = checkNotNull(newName);
    }

    public String getSurname() {
        return surname;
    }

    public void changeSurname(String newName) {
        surname = checkNotNull(newName);
    }
}
