package com.example.travelin;

import java.util.Date;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class DirectMessage extends RealmObject {

    private User recipient;

    private String body;

    private User author;

    private Date dateSent;


    //constructors
    public DirectMessage(){}

    public DirectMessage(User recipient, User author, String body, Date dateSent) {
        this.recipient = recipient;
        this.author = author;
        this.body = body;
        this.dateSent = dateSent;
    }

    //getters and setters
    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setDateSent(Date date) {
        this.dateSent = date;
    }
}
