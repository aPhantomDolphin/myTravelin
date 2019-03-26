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
import com.google.firebase.auth.FirebaseUser;
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
        //System.out.println("HELLOW FROM RATE:"+mail);

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

                mAuth = FirebaseAuth.getInstance();
                //System.out.println("HELLOW FROM RATE:"+mail);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User auth=new User();
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                                auth=(User) postSnapshot.getValue(User.class);
                            }
                        }

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = (User) postSnapshot.getValue(User.class);
                            if (user.getEmail().equals(mail)) {
                                nameView.setText(user.getName());
                                avgRating.setText(String.valueOf(user.getAvg()));

                                DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());

                                if(!giveRating.getText().toString().isEmpty()){
                                    double x=Double.parseDouble(giveRating.getText().toString());
                                    String auth1 = auth.getName();
                                    try {
                                        String mast = user.getRat();
                                        String[] arrOfStr = mast.split("@");
                                        String res = "";
                                        int flag = 0;

                                        double val = user.getAvg();
                                        val = val * user.getNumR();

                                        for (String a : arrOfStr) {
                                            String temp = a;
                                            String[] arrT = temp.split(":");
                                            try {
                                                String re2 = arrT[0];
                                                if (re2.equals(auth1)) {
                                                    double minus = Double.parseDouble(arrT[1]);
                                                    temp = auth1 + ":" + x;
                                                    val -= minus;
                                                    flag = 1;
                                                }
                                                if(!temp.equals("")){
                                                    res += "@" + temp;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (flag == 1) {
                                            val += x;
                                            val = val / user.getNumR();
                                            user.setAvgAgain(val);
                                            updateData.child("avg").setValue(val);
                                            updateData.child("rat").setValue(res);
                                        } else {
                                            user.setAvg(x);
                                            user.addRat(auth1, x);
                                            updateData.child("avg").setValue(user.getAvg());
                                            updateData.child("numR").setValue(user.getNumR());
                                            updateData.child("rat").setValue(user.getRat());
                                        }
                                    }
                                    catch(Exception e){
                                        user.setAvg(x);
                                        user.addRat(auth1, x);
                                        updateData.child("avg").setValue(user.getAvg());
                                        updateData.child("numR").setValue(user.getNumR());
                                        e.printStackTrace();
                                    }
                                }

                                if(!giveReview.getText().toString().isEmpty()){
                                    String rev1 = giveReview.getText().toString();
                                    String auth1 = auth.getName();
                                    try {
                                        String mast = user.getRev();
                                        String[] arrOfStr = mast.split("@");
                                        String res = "";
                                        int flag = 0;
                                        for (String a : arrOfStr) {
                                            String temp = a;
                                            String[] arrT = temp.split(":");
                                            try {
                                                String re2 = arrT[0];
                                                if (re2.equals(auth1)) {
                                                    temp = auth1 + ":" + rev1;
                                                    flag = 1;
                                                }
                                                //System.out.println("REVIEWNOAT:"+temp);
                                                if(!temp.equals("")){
                                                    res += "@" + temp;
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (flag == 1) {
                                            updateData.child("rev").setValue(res);
                                        } else {
                                            user.addRev(auth1, rev1);
                                            updateData.child("rev").setValue(user.getRev());
                                        }
                                    }
                                    catch(Exception e){
                                        e.printStackTrace();
                                        user.addRev(auth1, rev1);
                                        updateData.child("rev").setValue(user.getRev());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
