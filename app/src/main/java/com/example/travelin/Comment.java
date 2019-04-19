package com.example.travelin;

import java.io.Serializable;
import java.util.Date;

public class Comment /*implements Serializable*/ {
    //private User user;
    private String commentId;
    private Date timeCreated;
    private String comment;
    private String postId;
    private String authorEmail;
    private String username;

    public Comment() {}

    public Comment(String authorEmail, String commentId, Date timeCreated, String comment, String postId, String username) {

        this.authorEmail = authorEmail;
        //this.user = user;
        this.commentId = commentId;
        this.timeCreated = timeCreated;
        this.comment = comment;
        this.postId = postId;
        this.username = username;

    }

    //public User getUser() { return user; }

    //public void setUser(User user) { this.user = user; }

    public String getAuthorEmail(){ return this.authorEmail; }

    public void setAuthorEmail(String authorEmail) { this.authorEmail = authorEmail; }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostId(){return postId;}

    public void setPostId(String postId){this.postId = postId;}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
