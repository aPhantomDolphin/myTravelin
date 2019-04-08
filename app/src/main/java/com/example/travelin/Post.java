package com.example.travelin;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post implements Serializable {
    private User user;
    private String postText;
    private String postImageUrl = "";
    private String postId;
    private String author;
    private String postTitle;
    private long numLikes;
    private long numComments;
    private long timeCreated;
    private Date datePosted;
    private long numReports = 0;
    private String usersReported = "";

    public Post() {
    }

    public Post(User user, String postText, String postId, Date datePosted) {

        this.user = user;
        this.postText = postText;
        this.postId = postId;
        this.numLikes = 0;
        this.numComments = 0;
        this.datePosted = datePosted;
    }

    public void addReported(String userKey) {
        if (usersReported.equals("")) {
            usersReported = usersReported + userKey;
        } else {
            usersReported = usersReported + "|" + userKey;
        }
    }

    public String getUsersReported() {
        return usersReported;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void addReport() {
        if (numReports >= 3) {
            author = "Deleted";
            postText = "Deleted";
            postTitle = "Deleted";
            numLikes = 0;
        }

        numReports++;
    }

    public long getNumReports() {
        return numReports;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public User getUser() {

        return user;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePosted() {
        DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        String strDate = dateFormat.format(datePosted);
        return strDate;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(long numLikes) {
        this.numLikes = numLikes;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
}