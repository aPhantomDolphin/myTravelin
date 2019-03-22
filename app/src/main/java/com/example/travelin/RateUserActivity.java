package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import io.realm.Realm;
//import io.realm.RealmResults;

public class RateUserActivity extends AppCompatActivity {

    private TextInputEditText giveRating;
    private TextInputEditText giveReview;
    private TextView avgRating;
    private Button submit;
    private Button backToProfile;
    private TextView nameView;
    //private User user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user);

        giveRating = findViewById(R.id.user_rating);
        giveReview = findViewById(R.id.user_reviews);
        submit = findViewById(R.id.submit_rating);
        avgRating=findViewById(R.id.other_rating_profile);
        backToProfile= findViewById(R.id.backto_profile_other);
        nameView = findViewById(R.id.other_name_profile);

        mAuth = FirebaseAuth.getInstance();
        final String mail=getIntent().getExtras().getString("mail");
        System.out.println("HELLOW FROM RATE:"+mail);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference userRef = ref.child("Users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = (User) postSnapshot.getValue(User.class);
                    if (user.getEmail().equals(mail)) {
                        nameView.setText(user.getName());
                        avgRating.setText(String.valueOf(user.getAvg()));

                        /*DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                .child(postSnapshot.getKey());

                        double y=Double.parseDouble(avgRating.getText().toString());
                        System.out.println("INTENDEDAVG:"+y);

                        if(!giveRating.getText().toString().isEmpty()){
                            MyRating temp=new MyRating();
                            double x=Double.parseDouble(giveRating.getText().toString());
                            temp.setRating(x);
                            user.addRating(temp);
                            System.out.println("INTENDED:"+x);
                            System.out.println("AVGRATING:"+user.getAvgRating());
                            updateData.child("avgRating").setValue(user.getAvgRating());
                        }*/
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mail=getIntent().getExtras().getString("mail");

                //////////////////////////////////////////////////////////////

                mAuth = FirebaseAuth.getInstance();
                System.out.println("HELLOW FROM RATE:"+mail);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = (User) postSnapshot.getValue(User.class);
                            if (user.getEmail().equals(mail)) {
                                nameView.setText(user.getName());
                                avgRating.setText(String.valueOf(user.getAvg()));

                                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());

                                if(!giveRating.getText().toString().isEmpty()){
                                    //MyRating temp=new MyRating();
                                    double x=Double.parseDouble(giveRating.getText().toString());
                                    //temp.setRating(x);
                                    System.out.println("AVG1:"+user.getAvg());
                                    System.out.println("NUM1:"+user.getNumR());
                                    user.setAvg(x);
                                    System.out.println("AVG2:"+user.getAvg());
                                    System.out.println("NUM2:"+user.getNumR());
                                    System.out.println("AVGRATING:"+user.getAvg());
                                    updateData.child("avg").setValue(user.getAvg());
                                    updateData.child("numR").setValue(user.getNumR());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /////////////////////////////////////////////////////////////

                Intent intent =new Intent(RateUserActivity.this, OtherProfileActivity.class);
                intent.putExtra("mail",mail);
                startActivity(intent);

            }
        });

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=getIntent().getExtras().getString("mail");
                Intent intent =new Intent(RateUserActivity.this, OtherProfileActivity.class);
                intent.putExtra("mail",mail);
                startActivity(intent);
            }
        });

    }
}
