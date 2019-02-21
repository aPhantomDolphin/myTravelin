package com.example.travelin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncUser;

public class ProfileFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        String url = "realms://unbranded-metal-bacon.us1a.cloud.realm.io/travelin";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        SyncConfiguration config = SyncUser.current().createConfiguration(url).build();
        Realm realm = Realm.getInstance(config);

        String username=SyncUser.current().getIdentity();     //Get the username from intent here

        RealmQuery<User> query = realm.where(User.class);
        query.equalTo("username", username);

        RealmResults<User> results = query.findAll();
        User u = results.get(0);

        RealmList<byte[]> images = u.getImages();   //GET THE IMAGES REALMLIST FROM THE DATABASE;

        final LinearLayout linearLayout=(LinearLayout) findViewById(R.id.linearLayout);

        for(byte[] b : images){
            Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length);

            ImageView imageView=new ImageView(getApplicationContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            imageView.setImageBitmap(bitmap);

            linearLayout.addView(imageView);

        }



    }

}
