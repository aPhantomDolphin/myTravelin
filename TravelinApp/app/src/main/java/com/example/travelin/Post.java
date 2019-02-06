package com.example.travelin;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Post extends RealmObject {

    @Required
    private String type;


    private User author;

    @Required
    private Date datePosted;

    @Required
    private String body;

    private int rateUp;

    private int rateDown;

    private RealmList<Post> replies;

    private RealmList<String> imageURLs;
}
