package com.example.travelin;

import android.media.Rating;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {
    @PrimaryKey
    private int id;

    private String password;

    private String email;

    private int age;

    private String gender;

    private RealmList<Post> review;

    private int reportCount = 0;

    private String bio;

    private RealmList<Tag> interests;

    private RealmList<User> blocked;

    private String name;

    private RealmList<DirectMessage> messages;

    private String username;

    private String profilePictureURL;

    private RealmList<EventLocation> previousTrips;

    private RealmList<User> favorites;

    private RealmList<Post> posts;

    public User(){}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) { this.age = age; }

    public int getAge() { return age; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addReview(Post review) {
        this.review.add(review);
    }

    public RealmList<Post> getReviews() {
        return review;
    }

    public void removeReview(Post review) {
        this.review.remove(review);
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

    public RealmList<Tag> getInterests() {
        return this.interests;
    }

    public void addBlockedUser(User user) {
        blocked.add(user);
    }

    public void removeBlockedUser(User user) {
        blocked.remove(user);
    }

    public RealmList<User> getBlockedUsers() { return blocked; }

    public void addMessage(DirectMessage message) {
        messages.add(message);
    }

    public void deleteMessage(DirectMessage message) {
        messages.remove(message);
    }

    public RealmList<DirectMessage> getMessages() { return messages; }

    public String getUsername() {
        return username;
    }

    public void setProfilePictureURL(String url) {
        this.profilePictureURL = url;
    }

    public String getProfilePictureURL(String url) {
        return profilePictureURL;
    }

    public RealmList<EventLocation> getPreviousTrips() {
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

    public RealmList getFavorites() {
        return favorites;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public RealmList showPosts() {
        return posts;
    }
}