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
                                            String master = auth.getUpvoted();
                                            String[] arrT = master.split("@");
                                            int flag = 0;
                                            String res = "";
                                            for (String a : arrT) {
                                                int toadd = 0;
                                                String temp = a;

                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag = 1;
                                                    toadd = 1;
                                                    //post.downvote();
                                                }

                                                if (!temp.equals("") && toadd == 0) {
                                                    res += "@" + temp;
                                                }
                                            }

                                            if (flag == 1) {
                                                updateData1.child("upvoted").setValue(res);
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());
                                            } else {
                                                //I have not upvoted this post
                                                /*auth.addUpvoted(postID);
                                                post.upvote();
                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());*/
                                                gFlag++;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            String master = auth.getDownvoted();
                                            String[] arrT = master.split("@");
                                            int flag = 0;
                                            String res = "";
                                            for (String a : arrT) {
                                                int toadd = 0;
                                                String temp = a;

                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.upvoted, decrease upvotes of post
                                                    flag = 1;
                                                    toadd = 1;
                                                    //post.downvote();
                                                    post.upvote();
                                                    //post.upvote();
                                                }

                                                if (!temp.equals("") && toadd == 0) {
                                                    res += "@" + temp;
                                                }
                                            }

                                            if (flag == 1) {
                                                updateData1.child("upvoted").setValue(res);
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());
                                            } else {
                                                //I have not upvoted this post
                                                /*auth.addUpvoted(postID);
                                                post.upvote();
                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());*/
                                                gFlag++;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if(gFlag==2){
                                            auth.addUpvoted(postID);
                                            post.upvote();
                                            updateData1.child("upvoted").setValue(auth.getUpvoted());
                                            updateData2.child("upvotes").setValue(post.getRateUp());
                                            updateData2.child("downvotes").setValue(post.getRateDown());
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

                System.out.println("HELLOWORLD"+post1.getPostID());
                System.out.println("HELLOWORLD1"+postID);

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
                                            String master = auth.getUpvoted();
                                            String[] arrT = master.split("@");
                                            int flag = 0;
                                            String res = "";
                                            for (String a : arrT) {
                                                int toadd = 0;
                                                String temp = a;

                                                if (postID.equals(temp)) {
                                                    //I have already upvoted this person
                                                    //Remove from auth.downvoted, decrease upvotes of post
                                                    flag = 1;
                                                    toadd = 1;
                                                    post.downvote();
                                                    //post.downvote();
                                                }

                                                if (!temp.equals("") && toadd == 0) {
                                                    res += "@" + temp;
                                                }
                                            }

                                            if (flag == 1) {
                                                updateData1.child("upvoted").setValue(res);
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());
                                            } else {
                                                //I have not upvoted this post
                                                /*auth.addUpvoted(postID);
                                                post.upvote();
                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());*/
                                                gFlag++;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            String master = auth.getDownvoted();
                                            String[] arrT = master.split("@");
                                            int flag = 0;
                                            String res = "";
                                            for (String a : arrT) {
                                                int toadd = 0;
                                                String temp = a;

                                                if (postID.equals(temp)) {
                                                    //I have already downvoted this person
                                                    //Remove from auth.downvoted, increase upvotes of post
                                                    flag = 1;
                                                    toadd = 1;
                                                    //System.out.println("HERE12:"+post.getRateDown());
                                                    //post.undoDownVote();
                                                    //System.out.println("HERE13:"+post.getRateDown());
                                                }

                                                if (!temp.equals("") && toadd == 0) {
                                                    res += "@" + temp;
                                                }
                                            }

                                            if (flag == 1) {
                                                updateData1.child("downvoted").setValue(res);
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());
                                            } else {
                                                //I have not downvoted this post
                                                /*auth.addDownvoted(postID);
                                                post.downvote();
                                                updateData1.child("downvoted").setValue(auth.getDownvoted());
                                                updateData2.child("upvotes").setValue(post.getRateUp());
                                                updateData2.child("downvotes").setValue(post.getRateDown());*/
                                                gFlag++;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        if(gFlag==2){
                                            auth.addDownvoted(postID);
                                            post.downvote();
                                            updateData1.child("downvoted").setValue(auth.getDownvoted());
                                            updateData2.child("upvotes").setValue(post.getRateUp());
                                            updateData2.child("downvotes").setValue(post.getRateDown());
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
