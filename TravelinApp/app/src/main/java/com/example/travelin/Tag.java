package com.example.travelin;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Tag extends RealmObject {

    @Required
    private String tagName;


    private RealmList<Tag> relatedTags;

}
