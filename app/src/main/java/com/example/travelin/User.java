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

    private ArrayList<MyRating> myRatings;
    private ArrayList<MyRating> reviews;

    private int reportCount = 0;

    private String bio;

    //private RealmList<Tag> interests;
    private ArrayList<Tag> interests;

    private String interestsNew;

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



    private double avg;
    private int numR;
    private String profURL="https://firebasestorage.googleapis.com/v0/b/travelin-65f94.appspot.com/o/New%2Fprofilepic.png?alt=media&token=4a4f0885-395b-4c32-9094-190bd35a8dab";
    private String pics = "";
    private String rev;
    private String rat;
    private String block;
    private String rooms = "";



    public User(){}

    public User(String email, /*String password,*/ String name /*,int age*/) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.avgRating=0.0;
        /////////////////////////////////////////////////////////////////////////////////
        this.gender="";
        this.myRatings=new ArrayList<MyRating>();
        this.reviews=new ArrayList<MyRating>();
        /*MyRating r=new MyRating();
        r.setRating(3.3);
        reviews.add(r);*/
        this.reportCount = 0;
        this.bio="";
        this.interests=new ArrayList<Tag>();
        /*Tag tag =new Tag();
        tag.setTagName("ADDTAGS");
        interests.add(tag);*/
        this.blocked=new ArrayList<>();
        this.deleted = false;
        this.messages=new ArrayList<>();
        this.username="";
        this.profURL="https://firebasestorage.googleapis.com/v0/b/travelin-65f94.appspot.com/o/New%2Fprofilepic.png?alt=media&token=4a4f0885-395b-4c32-9094-190bd35a8dab";
        this.profilePictureURL="";
        this.previousTrips=new ArrayList<>();
        this.favorites=new ArrayList<>();
        this.posts=new ArrayList<>();
        //private byte[] img;
        this.profileImages=new ArrayList<>();

        this.interestsNew="";
        this.numR=0;
        this.rev="";
        this.rat="";
        this.block="";
        /////////////////////////////////////////////////////////////////////////////////

    }

    /*public String getIdent(){return ident;}

    public void setIdent(String ident) {
        this.ident = ident;
    }*/

    public void addPics(String pic) {
        if (pics.equals("")) {
            pics = pics+pic;
        } else {
            pics = pics + "|" + pic;
        }
    }

    public String getRooms() {
        return rooms;
    }

    public void addRoom(String room) {
        if (rooms.equals("")) {
            rooms = rooms + room;
        } else {
            rooms = rooms + "|" + room;
        }
    }

    public String getPics() {return pics;}

    public void setProfURL(String profURL) {
        this.profURL = profURL;
    }

    public String getProfURL() {
        return profURL;
    }

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
    /*public void addInterest(String interest) {
        this.interests=interest;
    }

    public String getInterests() {
        return this.interests;
    }*/


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

    public String getInterestsNew() {
        return interestsNew;
    }

    public void addInterestsNew(String intr) {
        if(this.interestsNew.equals("")){
            this.interestsNew+=(intr);
        }
        else {
            this.interestsNew += ("," + intr);
        }
    }


    public double getAvg() {
        return avg;
    }

    public void setAvgAgain(double avg){
        this.avg=avg;
    }

    public void setAvg(double avg) {
        double temp=getAvg();
        temp=temp*getNumR();
        temp=temp+avg;
        setNumR(getNumR()+1);
        this.avg=temp/getNumR();
    }


    public int getNumR() {
        return numR;
    }

    public void setNumR(int numR) {
        this.numR = numR;
    }

    public String getRev() {
        return rev;
    }

    public void addRev(String auth,String rev) {

        try{
            if(this.rev.equals("") || this.rev==null){
                this.rev="@"+auth+":"+rev;
            }
            else{
                this.rev += "@"+auth+":"+rev;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getRat() {
        return rat;
    }

    public void addRat(String auth,double rat) {

        try{
            if(this.rat.equals("") || this.rat==null){
                this.rat="@"+auth+":"+rat;
            }
            else{
                this.rat += "@"+auth+":"+rat;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getBlock() {
        return block;
    }

    public void addBlock(String block) {
        if(this.block.equals("") || this.block==null){
            System.out.println("CAME HERE");
            this.block=block;
        }
        else {
            this.block+=(","+block);
        }
    }

}