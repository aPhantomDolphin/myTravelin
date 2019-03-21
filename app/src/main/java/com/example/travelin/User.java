package com.example.travelin;

//package com.example.travelin;

import android.media.Rating;

import java.util.ArrayList;
import java.util.List;

//import io.realm.RealmList;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;

public class User /*extends RealmObject*/ {

    private String password;

    private String email;

    private int age;

    private String gender;

    //private RealmList<Post> review;
    //private RealmList<MyRating> myRatings; //for me
    //private RealmList<MyRating> reviews;   //I left other people
    private ArrayList<MyRating> myRatings;
    private ArrayList<MyRating> reviews;

    private int reportCount = 0;

    private String bio;

    //private RealmList<Tag> interests;
    private ArrayList<Tag> interests;

    //private RealmList<User> blocked;
    private ArrayList<User> blocked;

    private boolean deleted = false;

    private String name;

    //private RealmList<DirectMessage> messages;
    private ArrayList<DirectMessage> messages;

    //@PrimaryKey
    private String username;

    private String profilePictureURL;

    //private String ident;

    //private RealmList<EventLocation> previousTrips;
    private ArrayList<EventLocation> previousTrips;

    //private RealmList<User> favorites;
    private ArrayList<User> favorites;

    //private RealmList<Post> posts;
    private ArrayList<Post> posts;

    private double avgRating=0.0;

    private byte[] img;

    //private RealmList<byte[]> profileImages;
    private ArrayList<byte[]> profileImages;

    public User(){}

    public User(String email, String password/*, String username, int age*/) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.age = age;
        this.avgRating=0.0;
    }

    /*public String getIdent(){return ident;}

    public void setIdent(String ident) {
        this.ident = ident;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) { this.age = age; }

    public void setGender(String gender) { this.gender = gender; }

    public String getGender() { return gender; }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public int getAge() { return age; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addReview(MyRating review) {
        this.reviews.add(review);
    }

    public ArrayList<MyRating> getReviews() {
        return reviews;
    }

    public void removeReview(MyRating review) {
        this.reviews.remove(review);
    }

    public void addReport() {
        this.reportCount++;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void addInterest(Tag interest) {
        this.interests.add(interest);
    }

    public void removeInterest(Tag interest) {
        this.interests.remove(interest);
    }

    public ArrayList<Tag> getInterests() {
        return this.interests;
    }

    public void addBlockedUser(User user) {
        blocked.add(user);
    }

    public void removeBlockedUser(User user) {
        blocked.remove(user);
    }

    public ArrayList<User> getBlockedUsers() { return blocked; }

    public void addMessage(DirectMessage message) {
        messages.add(message);
    }

    public void deleteMessage(DirectMessage message) {
        messages.remove(message);
    }

    public ArrayList<DirectMessage> getMessages() { return messages; }

    public String getUsername() {
        return username;
    }

    public void setProfilePictureURL(String url) {
        this.profilePictureURL = url;
    }

    public String getProfilePictureURL(String url) {
        return profilePictureURL;
    }

    public ArrayList<EventLocation> getPreviousTrips() {
        return previousTrips;
    }

    public void addTrip(EventLocation trip) {
        previousTrips.add(trip);
    }

    public void removeTrip(EventLocation trip) {
        previousTrips.remove(trip);
    }

    public void addFavorite(User user) {
        favorites.add(user);
    }

    public void removeFavorite(User user) {
        favorites.remove(user);
    }

    public ArrayList<User> getFavorites() {
        return favorites;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public ArrayList<Post> showPosts() {
        return posts;
    }

    public void addRating(MyRating rating1) {
        this.myRatings.add(rating1);
        int x=myRatings.size();
        double sum=0.0;
        for(int i=0;i<x;i++){
            MyRating temp = myRatings.get(i);
            sum += temp.getRating();
        }
        this.avgRating=sum/x;
    }

    public ArrayList<MyRating> getRatings() {
        return myRatings;
    }

    public ArrayList<MyRating> getReview() {
        return reviews;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public ArrayList<byte[]> getProfileImages() {
        return profileImages;
    }

    public void addProfileImage(byte[] img) {
        this.profileImages.add(img);
    }

    public void deleteUser() {
        deleted = true;
        setAge(0);
        setBio("");
        setProfilePictureURL("");
        setGender("");
        setName("");
        for(EventLocation loc: previousTrips){
            previousTrips.remove(loc);
        }
        for(User user: favorites){
            favorites.remove(user);
        }
        for(Post post: posts){
            posts.remove(post);
        }
        for(DirectMessage dm: messages){
            messages.remove(dm);
        }
        for(Tag tag: interests){
            interests.remove(tag);
        }
    }
}