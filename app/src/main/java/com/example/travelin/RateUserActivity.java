package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncUser;

public class RateUserActivity extends AppCompatActivity {

    private TextInputEditText giveRating;
    private TextInputEditText giveReview;
    private TextView avgRating;
    private Button submit;
    //private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user);

        giveRating = findViewById(R.id.user_rating);
        giveReview = findViewById(R.id.user_reviews);
        submit = findViewById(R.id.submit_rating);
        avgRating=findViewById(R.id.other_rating_profile);


        Realm realm = Realm.getDefaultInstance();
        String un=getIntent().getExtras().getString("username");

        realm.beginTransaction();
        RealmResults<User> res=realm.where(User.class).equalTo("username",un).findAll();
        realm.commitTransaction();
        User user=res.get(0);

        String temp=""+(Math.floor(user.getAvgRating()*100)/100);
        System.out.println("TEMP STRING: "+temp);
        avgRating.setText(temp);

        realm.close();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                String un=getIntent().getExtras().getString("username");

                realm.beginTransaction();
                RealmResults<User> res=realm.where(User.class).equalTo("username",un).findAll();
                User user1=res.get(0);
                MyRating mr=new MyRating();
                try {
                    mr.setRating(Double.parseDouble(giveRating.getText().toString()));
                }
                catch(Exception e){
                    //mr.setRating(0.0);
                    e.printStackTrace();
                }
                try {
                    mr.setBody(giveReview.getText().toString());
                }
                catch(Exception e){
                    //mr.setBody("No review");
                    e.printStackTrace();
                }//mr.setAuthor(SyncUser.current());

                user1.addRating(mr);
                user1.addReview(mr);
                realm.commitTransaction();

                Intent intent =new Intent(RateUserActivity.this,OtherProfileActivity.class);
                intent.putExtra("username",un);
                startActivity(intent);

            }
        });

    }
}
