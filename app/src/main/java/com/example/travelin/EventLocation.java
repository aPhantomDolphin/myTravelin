package com.example.travelin;

import java.util.ArrayList;

public class EventLocation{

    private ArrayList<Tag> tags;

    private String locName;

    //constructors
    public EventLocation(){}

    public EventLocation(String locName) {
        this.locName = locName;
    }

    //getters and setters
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocName() {
        return locName;
    }
}
