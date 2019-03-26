package com.example.travelin;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class SimilarInterestActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        User u = null;
        mAuth = FirebaseAuth.getInstance();


        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userlist = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //User user = (User) postSnapshot.getValue(User.class);
                    //userlist.add(user);
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        final User user = (User) postSnapshot.getValue(User.class);
                        String[] interests = user.getInterestsNew().split(",");

                        for (int i = 0; i < interests.length; i++) {
                            for (DataSnapshot post : dataSnapshot.getChildren()) {
                                User tempUse = post.getValue(User.class);
                                String[] tempInterests = tempUse.getInterestsNew().split(",");
                                for (int j = 0; j < tempInterests.length; j++) {
                                    if (interests[i].equals(tempInterests[j])) {
                                        for (User k : userlist) {
                                            if (k.getName().equals(tempUse.getName())) {
                                                break;
                                            } else if (k.getName().equals(userlist.get(userlist.size()-1).getName())) {
                                                userlist.add(tempUse);
                                                break;
                                            }
                                        }

                                    }
                                }
                            }
                        }

                        // TODO: populate listview of users with all the users from the the arraylist

                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
