package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class ForumMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private ForumHomeFragment forumHomeFragment;
    private Button upvote;
    private Button downvote;

    User auth=new User();
    Post post1 = new Post();
    DatabaseReference updateData1;


    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);
        upvote = findViewById(R.id.fab);
        downvote = findViewById(R.id.fab1);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        forumHomeFragment = new ForumHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, forumHomeFragment);
        fragmentTransaction.commit();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        DatabaseReference myRef1 = firebaseDatabase.getReference().child("Posts");

        String blah = myRef1.push().getKey();
        post1 = new Post (firebaseUser.getEmail(),"YOOHOOO",blah);
        //post.setPostID(blah);
        myRef1.child(blah).setValue(post1);


        upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change this to the position post id!!////////
                final String postID = post1.getPostID();
                mAuth = FirebaseAuth.getInstance();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int flag=0;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                                auth = (User) postSnapshot.getValue(User.class);
                                flag=1;
                            }

                            if(flag==1) {
                                updateData1 = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());
                                break;
                            }
                        }

                        FirebaseDatabase db1 = FirebaseDatabase.getInstance();
                        DatabaseReference ref1 = db1.getReference();
                        DatabaseReference postRef = ref1.child("Posts");
                        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                //User auth=auth1;
                                for (DataSnapshot postSnapshot : dataSnapshot1.getChildren()) {
                                    Post post = (Post) postSnapshot.getValue(Post.class);
                                    if (post.getPostID().equals(postID)) {
                                        DatabaseReference updateData2 = FirebaseDatabase.getInstance().getReference("Posts")
                                                .child(postSnapshot.getKey());
                                        int gFlag=0;
                                        try {
                                            String master1 = auth.getUpvoted();
                                            String master2 = auth.getDownvoted();
                                            String[] arrT1 = master1.split("@");
                                            String[] arrT2 = master2.split("@");
                                            int flag1 = 0;
                                            int flag2 = 0;
                                            String res1 = "";
                                            String res2 = "";
                                            for (String a : arrT1) {
                                                int toadd1 = 0;
                                                String temp = a;
                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag1 = 1;
                                                    toadd1 = 1;
                                                }

                                                if (!temp.equals("") && toadd1 == 0) {
                                                    res1 += "@" + temp;
                                                }
                                            }

                                            for (String a : arrT2) {
                                                int toadd2 = 0;
                                                String temp = a;
                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag2 = 1;
                                                    toadd2 = 1;
                                                }

                                                if (!temp.equals("") && toadd2 == 0) {
                                                    res2 += "@" + temp;
                                                }
                                            }

                                            if(flag1==1 && flag2==0){
                                                updateData1.child("upvoted").setValue(res1);
                                                post.undoUpvote();
                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                            }
                                            else if(flag1==0 && flag2==0){
                                                auth.addUpvoted(postID);
                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                post.upvote();
                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                            }
                                            else if(flag2==1 && flag1==0){
                                                auth.addUpvoted(postID);
                                                post.undoDownVote();
                                                post.upvote();
                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                updateData1.child("downvoted").setValue(res2);
                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                                updateData2.child("rateDown").setValue(post.getRateDown());
                                            }
                                            else{
                                                System.out.println("ERROR ERROR ERROR UPVOTE ERROR");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get position post id!
                final String postID = post1.getPostID();
                mAuth = FirebaseAuth.getInstance();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int flag=0;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                                auth = (User) postSnapshot.getValue(User.class);
                                flag=1;
                            }

                            if(flag==1) {
                                updateData1 = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());
                                break;
                            }
                        }

                        FirebaseDatabase db1 = FirebaseDatabase.getInstance();
                        DatabaseReference ref1 = db1.getReference();
                        DatabaseReference postRef = ref1.child("Posts");
                        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                //User auth=auth1;
                                for (DataSnapshot postSnapshot : dataSnapshot1.getChildren()) {
                                    Post post = (Post) postSnapshot.getValue(Post.class);
                                    if (post.getPostID().equals(postID)) {
                                        DatabaseReference updateData2 = FirebaseDatabase.getInstance().getReference("Posts")
                                                .child(postSnapshot.getKey());
                                        int gFlag=0;
                                        try {
                                            String master1 = auth.getUpvoted();
                                            String master2 = auth.getDownvoted();
                                            String[] arrT1 = master1.split("@");
                                            String[] arrT2 = master2.split("@");
                                            int flag1 = 0;
                                            int flag2 = 0;
                                            String res1 = "";
                                            String res2 = "";
                                            for (String a : arrT1) {
                                                int toadd1 = 0;
                                                String temp = a;
                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag1 = 1;
                                                    toadd1 = 1;
                                                }

                                                if (!temp.equals("") && toadd1 == 0) {
                                                    res1 += "@" + temp;
                                                }
                                            }

                                            for (String a : arrT2) {
                                                int toadd2 = 0;
                                                String temp = a;
                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag2 = 1;
                                                    toadd2 = 1;
                                                }

                                                if (!temp.equals("") && toadd2 == 0) {
                                                    res2 += "@" + temp;
                                                }
                                            }

                                            if(flag2==1 && flag1==0){
                                                updateData1.child("downvoted").setValue(res2);
                                                post.undoDownVote();
                                                updateData2.child("rateDown").setValue(post.getRateDown());
                                            }
                                            else if(flag1==0 && flag2==0){
                                                auth.addDownvoted(postID);
                                                updateData1.child("downvoted").setValue(auth.getDownvoted());
                                                post.downvote();
                                                updateData2.child("rateDown").setValue(post.getRateDown());
                                            }
                                            else if(flag1==1 && flag2==0){
                                                auth.addDownvoted(postID);
                                                post.undoUpvote();
                                                post.downvote();
                                                updateData1.child("upvoted").setValue(res1);
                                                updateData1.child("downvoted").setValue(auth.getDownvoted());
                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                                updateData2.child("rateDown").setValue(post.getRateDown());
                                            }
                                            else{
                                                System.out.println("ERROR ERROR ERROR DOWNVOTE ERROR");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String currentID = firebaseUser.getUid();

    }

}
