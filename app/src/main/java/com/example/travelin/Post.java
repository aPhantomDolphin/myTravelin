package com.example.travelin;

import java.util.ArrayList;
import java.util.Date;

public class Post{

    private String authorEmail;

    private String username;

    private Date datePosted;

    private String body;

    private int rateUp;

    private int rateDown;

    private String replies;

    private String imageURLs;

    private String postID;

    //constructors
    public Post(){}

    public Post(String authorEmail,/*Date datePosted,*/ String body, String postId) {
        this.authorEmail = authorEmail;
       // this.datePosted = datePosted;
        this.body = body;
        this.postID = postId;
        rateUp=0;
        rateDown=0;
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

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getRateUp() {
        return rateUp;
    }

    public int getRateDown() {
        return rateDown;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getReplies() {
        return replies;
    }

    public void setReplies(String replies) {
        this.replies = replies;
    }

    public String getImageURLs() {
        return imageURLs;
    }

    public void setImageURLs(String imageURLs) {
        this.imageURLs = imageURLs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
