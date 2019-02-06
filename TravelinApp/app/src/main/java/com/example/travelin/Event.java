package com.example.travelin;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Event extends RealmObject {

    private EventLocation location;

    @Required
    private Date date;

    @Required
    private String name;

    private RealmList<Tag> tags;

    private RealmList<User> rsvp;

}
