package com.example.travelin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class OtherProfilePicturesActivity extends AppCompatActivity {

    private Button goToProfile;
    private String un;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other_profile_pictures);
        goToProfile = findViewById(R.id.profile_button);
        layout=findViewById(R.id.imgLinear);

        un=getIntent().getExtras().getString("username");


        Realm realm= Realm.getDefaultInstance();
        realm.beginTransaction();

        RealmResults<User> res=realm.where(User.class).equalTo("username",un).findAll();
        User user=res.get(0);
        realm.commitTransaction();
        RealmList<byte[]> list=user.getProfileImages();

        System.out.println("NUMBER OF IMAGES:"+list.size());
        for(int i=0;i<list.size();i++){
            Bitmap bmp = BitmapFactory.decodeByteArray(list.get(i), 0, list.get(i).length);
            ImageView imgV=new ImageView(getApplicationContext());
            imgV.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            imgV.setImageBitmap(bmp);
            layout.addView(imgV);
        }



        goToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(OtherProfilePicturesActivity.this,OtherProfileActivity.class);
                //startActivity(intent);


                OtherProfilePicturesActivity.this.finish();
            }
        });

    }

}
