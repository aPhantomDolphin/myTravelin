package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForumMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button reportButton;
    private ListView lview;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        /**
         * Depending on how we set this up, the "report Button" code may
         * need to be moved to inside the "on item click" code near
         * the bottom of the page
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);

        final Button unHideButton = null;
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Posts");
        //reportButton = findViewById("CREATE LAYOUT FOR THIS");

        final List<HashMap<String, String>> stringList = new ArrayList<>();
        final ArrayList<Post> postList= new ArrayList<Post>();
        final List<HashMap<String, String>> stringRemoved = new ArrayList<>();

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Post post = null;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            post = postSnapshot.getValue(Post.class);
                            if (post.getPostId().equals(postSnapshot.getKey())) {
                                String [] alreadyReported = post.getUsersReported().split("\\|");
                                for (String str : alreadyReported) {
                                    if (str.equals(mAuth.getCurrentUser().getUid())) {
                                        /** this user has already reported this post, do nothing **/
                                        return;
                                    }
                                }
                                post.addReport();
                                if (post.getNumReports() >= 3) {
                                    databaseReference.child(postSnapshot.getKey()).removeValue();
                                    return;
                                }
                                break;
                            }
                        }
                        post.addReported(mAuth.getCurrentUser().getUid());

                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Posts")
                                .child(post.getPostId());

                        updateData.child("usersReported").setValue(post.getUsersReported());
                        updateData.child("numReports").setValue(post.getNumReports());
                        updateData.child("author").setValue(post.getAuthor());
                        updateData.child("postText").setValue(post.getPostText());
                        updateData.child("postTitle").setValue(post.getPostTitle());
                        updateData.child("numLikes").setValue(post.getNumLikes());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post post= postSnapshot.getValue(Post.class);
                    if(post.getPostId().equals(postSnapshot.getKey())) {
                        postList.add(post);
                    }
                }


                for (int i = 0; i < postList.size(); i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();

                    hm.put("blog_user_name", postList.get(i).getAuthor());
                    hm.put("blog_date", postList.get(i).getDatePosted());
                    hm.put("blog_title", postList.get(i).getPostTitle());
                    hm.put("blog_image", postList.get(i).getPostImageUrl());
                    hm.put("blog_dec", postList.get(i).getPostText());
                    hm.put("blog_like_count", Long.toString(postList.get(i).getNumLikes()));
                    stringList.add(hm);
                }

                String[] from = {"blog_user_name", "blog_date", "blog_title", "blog_image", "blog_desc", "blog_like_count"};
                int[] to = {R.id.blog_user_name, R.id.blog_date, R.id.blog_title, R.id.blog_image, R.id.blog_desc, R.id.blog_like_count};

                final SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ForumMainActivity.this, stringList, R.layout.activity_listview, from, to);

                final ListView androidListView = (ListView) findViewById(R.id.listView);

                androidListView.setAdapter(simpleAdapter);

                unHideButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = stringRemoved.size() - 1; i > -1; i--) {
                            stringList.add(stringRemoved.get(i));
                        }
                        stringRemoved.clear();
                        androidListView.invalidateViews();
                    }
                });


                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (/* clicked on hide part */ true) {
                            stringRemoved.add(stringList.get(position));
                            stringList.remove(position);
                            androidListView.invalidateViews();
                        }

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
