package com.example.travelin;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncUser;

public class ProfileActivity extends AppCompatActivity {
    private Realm realm = Realm.getDefaultInstance();
    private Button logoutButton;
    private TextView nameView;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SyncUser syncUser = SyncUser.current();
        realm = Realm.getInstance(syncUser.getDefaultConfiguration());


        //username = getIntent().getStringExtra("USERNAME");

        logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncUser.logOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*RealmQuery<User> query = realm.where(User.class).equalTo("username",username);
        RealmResults<User> results = query.findAll();
        String name = results.get(0).getName();
        nameView.setText(name);*/

        //System.out.println("hii"+username);

    }




}