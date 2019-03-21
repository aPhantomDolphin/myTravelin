package com.example.travelin;

import java.util.ArrayList;
import java.util.Date;

//import io.realm.RealmList;
//import io.realm.RealmObject;

public class Event /*extends RealmObject*/ {

    private EventLocation location;

    private Date date;

    private String name;

    private ArrayList<Tag> tags;

    private ArrayList<User> rsvp;

    //constructors
    public Event(){}

    public Event(EventLocation location, Date date, String name) {
        this.location = location;
        this.date = date;
        this.name = name;
    }

    //getters and setters
    public void setLocation(EventLocation location) {
        this.location = location;
    }

    public EventLocation getLocation() {
        return location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRSVP(User newRsvp) {
        rsvp.add(newRsvp);
    }

    public void removeRSVP(User oldRsvp) {
        rsvp.remove(oldRsvp);
    }
}
