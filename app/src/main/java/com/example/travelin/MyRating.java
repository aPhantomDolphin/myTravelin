package com.example.travelin;

import java.util.Date;

//import io.realm.RealmObject;

public class MyRating /*extends RealmObject*/ {

    private String type;

    private User author;

    private Date datePosted;

    private String body;

    private double rating=0.0;

    //constructors
    public MyRating(){}

    public MyRating(String type, User author, Date datePosted, String body) {
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}