package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncUser;

public class ProfileActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncUser syncUser = SyncUser.current();
                syncUser.logOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void changeProfileDescription(String username, String desc) {
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);
        RealmResults<User> userRatings = query.findAll();
        userRatings.get(0).setBio(desc);
    }

    public String getProfileDescription(String username) {
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);
        RealmResults<User> userRatings = query.findAll();
        return userRatings.get(0).getBio();
    }

    public void addImg(String email, Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

        byte[] byteArray=stream.toByteArray();

        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", email);

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        realm.beginTransaction();
        user.setImg(byteArray);
        realm.commitTransaction();

    }

    public void addProfileImg(String email, Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

        byte[] byteArray=stream.toByteArray();

        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", email);

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        realm.beginTransaction();
        user.addProfileImage(byteArray);
        realm.commitTransaction();
    }

    public Bitmap getImg(String email){
        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("email", email);

        RealmResults<User> results = query.findAll();
        User user=results.get(0);

        byte[] byteArray=user.getImg();
        Bitmap bitmap= BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

        return bitmap;
    }
}
