package com.example.travelin;

import android.graphics.Bitmap;
import android.media.Image;

import java.lang.reflect.Member;

public class Message {
    private String text;
    private Bitmap bitmap;
    private MemberData memberData;
    private boolean belongsToCurrentUser;
    private boolean image;

    public Message(String text, MemberData data, boolean belongsToCurrentUser) {
        this.text = text;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.image = false;
    }

    public Message (Bitmap bmp, MemberData data, boolean belongsToCurrentUser) {
        this.bitmap = bmp;
        this.memberData = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.image = true;
    }

    public String getText() {
        return text;
    }

    public MemberData getMemberData() {
        return memberData;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public boolean isImage() {
        return image;
    }
}
