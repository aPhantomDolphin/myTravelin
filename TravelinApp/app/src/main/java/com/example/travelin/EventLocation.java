package com.example.travelin;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class EventLocation extends RealmObject {

    private RealmList<Tag> tags;

    @Required
    private String locName;

}
