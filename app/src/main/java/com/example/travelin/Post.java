package com.example.travelin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Post{

    private String authorEmail;

    private String username;

    private Date datePosted;

    private String createdAt;

    private String body;

    private int rateUp;

    private int rateDown;

    private String replies;

    private String imageURLs;

    private String postID;

    private String postTitle;

    private String peopleReported="";

    private ArrayList<Comment> commentsList;

    private int numReports;

    public Post(){}

    public Post(String authorEmail, Date datePosted, String body, String postId, String postTitle, String username) {
        this.authorEmail = authorEmail;
        this.datePosted = datePosted;
        this.body = body;
        this.postID = postId;
        this.postTitle = postTitle;
        this.username = username;
        this.commentsList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdAt=sdf.format(datePosted.getTime());
        rateUp=0;
        rateDown=0;
        numReports=0;
    }

    public int getNumReports() {return this.numReports;}

    public void addReport() {this.numReports++;}

    public String getScore(){
        return String.valueOf(getUpvotes()-getDownvotes());
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitle() {
        return postTitle;
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

    public int getRateUp() {
        return this.rateUp;
    }

    public int getUpvotes() { return rateUp; }

    public void downvote() {
        this.rateDown++;
    }

    public int getRateDown(){
        return this.rateDown;
    }

    public int getDownvotes() { return rateDown; }

    public void undoUpvote(){
        this.rateUp--;
    }
    public void undoDownVote(){
        this.rateDown--;
    }


    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

    public void addReported(String email) {
        if(this.peopleReported.equals("")){
            this.peopleReported += email;
        }
        else{
            peopleReported += "|";
            peopleReported += email;
        }
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPeopleReported() {
        return this.peopleReported;
    }

    public static Comparator<Post> compareByScore = new Comparator<Post>() {
        @Override
        public int compare(Post o1, Post o2) {
            return o2.getScore().compareTo(o1.getScore());
        }
    };

    public static Comparator<Post> compareByTime = new Comparator<Post>() {
        @Override
        public int compare(Post o1, Post o2) {
            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
        }
    };
}