package com.example.travelin;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncUser;
import io.realm.SyncUserInfo;

import static com.example.travelin.Constants.REALM_URL;

public class ProfileActivity extends AppCompatActivity {
    //private Realm realm;
    private Button logoutButton;
    private TextView nameView;
    private String username;
    private Button editProfile;
    private Button homeButton;
    private ImageView imageView;
    private Button searchButton;
    private TextView bioView;
    private TextView emailView;
    private TextView ratingView;
    private ImageView dpView;
    private Button addImages;
    private TextView interests;
    private TextView reviews;
    byte[] bArray=new byte[0];
    User usert;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Realm realm = Realm.getDefaultInstance();

        nameView = findViewById(R.id.name_profile);

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
               ProfileActivity.this.finish();
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

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(ProfileActivity.this, SearchFilterActivity.class);
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

        imageView = findViewById(R.id.profilepic);
        bioView = findViewById(R.id.bio_profile);
        emailView = findViewById(R.id.email_profile1);
        ratingView = findViewById(R.id.rating_profile);
        dpView = findViewById(R.id.profilepic);

        interests=findViewById(R.id.interest_profile1);
        reviews=findViewById(R.id.reviews_profile);

        addImages = findViewById(R.id.add_pictures_profile);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        System.out.println(SyncSingleton.getInstance().getEmail());
        RealmQuery<User> realmQuery = realm.where(User.class);

        realmQuery.equalTo("email",SyncSingleton.getInstance().getEmail());
        RealmResults<User> results = realmQuery.findAll();

        User u = results.get(0);
        usert = u;
        nameView.setText(u.getUsername());
        bioView.setText(u.getBio());
        emailView.setText(u.getEmail());
        ratingView.setText(String.valueOf(u.getAvgRating()));
        if (u.getImg() != null) {
            byte[] bArray2 = u.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(bArray2, 0, bArray2.length);
            dpView.setImageBitmap(bmp);
        }
        String in="";
        for(int i=0;i<u.getInterests().size();i++){
            in+=u.getInterests().get(i).getTagName();
            in+=",";
        }
        String re="";
        for(int i=0;i<u.getReviews().size();i++){
            re+=u.getReviews().get(i).getBody();
            re+=",";
        }
        interests.setText(in);
        reviews.setText(re);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Realm realm=Realm.getDefaultInstance();


        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            try {
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                //dpView.setImageBitmap(bmp);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                bArray = stream.toByteArray();
                realm.beginTransaction();
                usert.addProfileImage(bArray);
                realm.commitTransaction();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
