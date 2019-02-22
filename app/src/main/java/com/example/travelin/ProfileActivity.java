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

import org.json.JSONObject;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncUser;

import static com.example.travelin.Constants.REALM_URL;

public class ProfileActivity extends AppCompatActivity {
    private Realm realm;
    private Button logoutButton;
    private TextView nameView;
    private String username;
    private Button editProfile;
    private Button homeButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        nameView = findViewById(R.id.name_profile);

        //RealmQuery<User> query = realm.where(User.class);
        //query.equalTo("email","a@purdue.edu");
        //User results = query.findFirst();
        /*RealmResults<User> results = realm
                .where(User.class)
                .contains("username", "a")
                .findAll();

        try{
            if(results != null){
                //User newUser = realm.copyFromRealm(results);
                User newUser = results.get(0);
                System.out.println(newUser.getEmail());
                nameView.setText(newUser.getEmail());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }*/


        logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncUser.current().logOut();
                Intent intent = new Intent( ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });

        editProfile = findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){
               Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
           }
        });

        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

}
