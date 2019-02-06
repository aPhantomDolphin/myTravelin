package com.example.travelin;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class DirectMessage extends RealmObject {

    private User recipient;

    @Required
    private String body;

    private User author;

    @Required
    private Date dateSent;

}
