package com.example.travelin;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Rating extends RealmObject {

    private String type;

    private User author;

    private Date datePosted;

    private String body;

    private double rating;

    //constructors
    public Rating(){}

    public Rating(String type, User author, Date datePosted, String body) {
        this.type = type;
        this.author = author;
        this.datePosted = datePosted;
        this.body = body;
    }

    //getters and setters
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

}
