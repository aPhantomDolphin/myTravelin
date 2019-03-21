package com.example.travelin;

//import io.realm.RealmList;
//import io.realm.RealmObject;

import java.util.ArrayList;

public class Tag /*extends RealmObject*/ {

    private String tagName;

    private ArrayList<Tag> relatedTags;


    //constructors
    public Tag(){}

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(String tagName, ArrayList<Tag> relatedTags) {
        this.tagName = tagName;
        this.relatedTags = relatedTags;
    }


    //getters and setters
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void addRelatedTag(Tag tag) {
        relatedTags.add(tag);
    }

    public void removeRelatedTag(Tag tag) {
        relatedTags.remove(tag);
    }
}
