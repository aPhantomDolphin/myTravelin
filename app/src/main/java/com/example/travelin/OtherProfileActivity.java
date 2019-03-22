package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OtherProfileActivity extends AppCompatActivity {

    private Button rateUser;
    private Button blockButton;
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

    private TextView interest;

    byte[] bArray=new byte[0];
    //User usert;
    private String mail;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        mail=getIntent().getExtras().getString("mail");
        bioView=findViewById(R.id.other_bio_profile);
        nameView = findViewById(R.id.other_name_profile);
        emailView = findViewById(R.id.other_email);
        reviewsProfile=findViewById(R.id.other_reviews_profile);
        rateUser = findViewById(R.id.rate_and_review_profile);

        interest=findViewById(R.id.other_interest_profile);

        rateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,RateUserActivity.class);
                System.out.println("OPAAAAAAAAAAAAAAA:"+mail);
                intent.putExtra("mail",mail);
                startActivity(intent);
            }
        });

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference userRef = ref.child("Users");

        //userRef.addValueEventListener(new ValueEventListener() {
          userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                User u = null;
                userList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = (User) postSnapshot.getValue(User.class);
                    System.out.println("EMAILLLLLLL "+user.getEmail());
                    if (user.getEmail().equals(mail)) {
                        //u = user;
                        System.out.println("THIS NOOB "+user.getName());
                        nameView.setText(user.getName());
                        bioView.setText(user.getBio());
                        emailView.setText(user.getEmail());
                        ratingView.setText(String.valueOf(user.getAvg()));
                        interest.setText(String.valueOf(user.getInterestsNew()));
                        String rev="";
                        /*for(int i=0;i<u.getReviews().size();i++){
                            rev+=u.getReviews().get(i).getBody();
                            rev+=",";
                        }
                        reviewsProfile.setText(rev);*/
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*Realm realm = Realm.getDefaultInstance();

        nameView = findViewById(R.id.other_username_profile);
        reviewsProfile=findViewById(R.id.other_reviews_profile);
        RealmResults<User> res1=realm.where(User.class).equalTo("username",UN).findAll();
        User thisone=res1.get(0);
        String rev="";
        for(int i=0;i<thisone.getReviews().size();i++){
            rev+=thisone.getReviews().get(i).getBody();
            rev+=",";
        }*/

        //rev+=thisone.getReviews().get(thisone.getReviews().size()-1).getBody();
        //reviewsProfile.setText(rev);

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

        blockButton = findViewById(R.id.block_button);
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("users");
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<User> userList = new ArrayList<>();
                        User u = null;
                        userList.clear();
                        DataSnapshot needed = null;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            userList.add(user);
                            System.out.println(postSnapshot.getKey());
                            if (user.getEmail().equals(mail)) {
                                u = user;
                                needed = postSnapshot;
                                break;
                            }
                        }
                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("users")
                                .child(needed.getKey());

                        User thisUser = null;
                        for (User use : userList) {
                            if (use.getEmail().equals(mail)) {
                                thisUser = use;
                            }
                        }

                        u.addBlockedUser(thisUser);

                        updateData.child("blocked").setValue(u.getBlockedUsers());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

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
        //emailView = findViewById(R.id.other_name_profile);
        ratingView = findViewById(R.id.other_rating_profile);
        dpView = findViewById(R.id.other_profilepic);

        viewImages = findViewById(R.id.images_other_profile);
        viewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,OtherProfilePicturesActivity.class);
                intent.putExtra("mail",mail);
                startActivity(intent);

            }   });


        /*System.out.println(SyncSingleton.getInstance().getEmail());
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
        }*/



    }
}