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

    @Required
    private String password;

    @Required
    private String email;

    private int age;

    @Required
    private String gender;

    private RealmList<Post> review;

    private int reportCount;

    @Required
    private String bio;

    private RealmList<Tag> interests;

    private RealmList<User> blocked;

    @Required
    private String name;

    private RealmList<DirectMessage> messages;

    @Required
    private String username;

    private String profilePictureURL;

    private RealmList<EventLocation> previousTrips;

    private RealmList<User> favorites;

    private RealmList<Post> posts;
}
