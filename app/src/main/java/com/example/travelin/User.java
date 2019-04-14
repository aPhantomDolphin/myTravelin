package com.example.travelin;


public class User {

    private String password;

    private String email;

    private int age;

    private String gender;

    private int reportCount = 0;

    private String bio;

    private String interestsNew;

    private String name;
    private String username;

    private double avgRating=0.0;

    private byte[] img;

    private double avg;
    private int numR;
    private String profURL;
    private String rev;
    private String rat;
    private String block;

    private String upvoted;
    private String downvoted;
    private String postIDs;

    private String pics;
    private String rooms;
    private String roomInvites;

    public User(){}

    public User(String email, /*String password,*/ String name /*,int age*/) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.avgRating=0.0;
        /////////////////////////////////////////////////////////////////////////////////
        this.gender="";
        this.reportCount = 0;
        this.bio="";
        this.username="";
        this.interestsNew="";
        this.numR=0;
        this.rev="";
        this.rat="";
        this.block="";
        this.upvoted="";
        this.downvoted="";
        this.postIDs="";
        this.pics="";
        this.rooms="";
        this.roomInvites="";

        /////////////////////////////////////////////////////////////////////////////////

    }


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


    public String getUsername() {
        return username;
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

    public String getUpvoted() {
        return upvoted;
    }



    public void addUpvoted(String upvote) {
        try{
            if(this.upvoted.equals("") || this.upvoted==null){
                this.upvoted="@"+upvote;
            }
            else{
                this.upvoted += "@"+upvote;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getDownvoted(){
        return downvoted;
    }

    public void addDownvoted(String downvote){
        try{
            if(this.downvoted.equals("") || this.downvoted==null){
                this.downvoted="@"+downvote;
            }
            else{
                this.downvoted += "@"+downvote;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getPostIDs() {
        return postIDs;
    }



    public void addPostIDs(String postID) {
        try{
            if(this.postIDs.equals("") || this.postIDs==null){
                this.postIDs=postID;
            }
            else{
                this.postIDs += "@"+postID;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getRoomInvites() {
        return roomInvites;
    }

    public void setRoomInvites(String roomInvites) {
        this.roomInvites = roomInvites;
    }

    public void addPics(String pic) {
        if (pics.equals("")) {
            pics = pics+pic;
        } else {
            pics = pics + "|" + pic;
        }
    }

    public void addRoom(String room) {
        if (rooms.equals("")) {
            rooms = rooms + room;
        } else {
            rooms = rooms + "|" + room;
        }
    }

    public void clearBlock() {block = ""; return;}

    public void addInvite(String room) {
        if (roomInvites.equals("")) {
            roomInvites = roomInvites = room;
        } else {
            roomInvites = roomInvites + "|" + room;
        }
    }

    public void clearInvites() {
        roomInvites = "";
    }

}