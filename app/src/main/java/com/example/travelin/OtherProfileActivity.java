package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncUser;

public class OtherProfileActivity extends AppCompatActivity {

    private Button rateUser;
    private Button logoutButton;
    private TextView nameView;
    private String username;
    private Button homeButton;
    private ImageView imageView;
    private Button searchButton;
    private TextView bioView;
    private TextView emailView;
    private TextView ratingView;
    private ImageView dpView;
    private Button viewImages;
    private TextView reviewsProfile;
    byte[] bArray=new byte[0];
    //User usert;
    private String UN;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        UN=getIntent().getExtras().getString("username");

        rateUser = findViewById(R.id.rate_and_review_profile);
        rateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,RateUserActivity.class);
                intent.putExtra("username",UN);
                startActivity(intent);
            }
        });

        Realm realm = Realm.getDefaultInstance();

        nameView = findViewById(R.id.other_username_profile);
        reviewsProfile=findViewById(R.id.other_reviews_profile);
        RealmResults<User> res1=realm.where(User.class).equalTo("username",UN).findAll();
        User thisone=res1.get(0);
        String rev="";
        for(int i=0;i<thisone.getReviews().size();i++){
            rev+=thisone.getReviews().get(i).getBody();
            rev+=",";
        }

        //rev+=thisone.getReviews().get(thisone.getReviews().size()-1).getBody();
        reviewsProfile.setText(rev);

        /*logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncUser.current().logOut();
                Intent intent = new Intent( ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });*/

        //this line is just here for commit purposes, will be updated with the actual functionality when database change is done

        //debugging placeholder


        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(OtherProfileActivity.this, SearchFilterActivity.class);
                    try{
                        startActivity(intent);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        imageView = findViewById(R.id.other_profilepic);
        bioView = findViewById(R.id.other_bio_profile);
        emailView = findViewById(R.id.other_name_profile);
        ratingView = findViewById(R.id.other_rating_profile);
        dpView = findViewById(R.id.other_profilepic);

        viewImages = findViewById(R.id.images_other_profile);
        viewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,OtherProfilePicturesActivity.class);
                intent.putExtra("username",UN);
                startActivity(intent);

        }   });

        System.out.println(SyncSingleton.getInstance().getEmail());
        realm.beginTransaction();
        RealmQuery<User> realmQuery = realm.where(User.class);
        System.out.println("USERNAME FOUND: "+getIntent().getExtras().getString("username"));
        realmQuery.equalTo("username",getIntent().getExtras().getString("username"));
        RealmResults<User> results = realmQuery.findAll();
        realm.commitTransaction();

        User u = results.get(0);

        //usert = u;
        System.out.println("USER FOUND: "+u.getUsername());
        nameView.setText(u.getUsername());
        bioView.setText(u.getBio());
        emailView.setText(u.getEmail());
        ratingView.setText(String.valueOf(u.getAvgRating()));
        if (u.getImg() != null) {
            byte[] bArray2 = u.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(bArray2, 0, bArray2.length);
            dpView.setImageBitmap(bmp);
        }



    }
}