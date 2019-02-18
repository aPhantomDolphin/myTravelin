package com.example.travelin;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Post extends RealmObject {

    //because Realm doesn't work with inheritance, we need a type
    //to make sure that we add the posts to the correct lists

    private String type;

    private User author;

    private Date datePosted;

    private String body;

    private int rateUp = 0;

    private int rateDown = 0;

    private RealmList<Post> replies;

    private RealmList<String> imageURLs;


    //constructors
    public Post(){}

    public Post(String type, User author, Date datePosted, String body) {
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

    public void upvote() {
        this.rateUp++;
    }

    public void downvote() {
        this.rateDown++;
    }

    public void addReply(Post reply) {
        this.replies.add(reply);
    }

    public void deleteReply(Post reply) {
        replies.remove(reply);
    }

    public void addImageURL(String url) {
        imageURLs.add(url);
    }

    public void deleteImageURL(String url) {
        imageURLs.remove(url);
    }

    public int getupvotes() { return rateUp; }

    public int getdownvotes() { return rateDown; }
}
