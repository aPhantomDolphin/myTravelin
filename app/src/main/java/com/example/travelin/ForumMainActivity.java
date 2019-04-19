package com.example.travelin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ForumMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference postRef;
    private FloatingActionButton newPost;
    private BottomNavigationView mMainNav;

    private RadioGroup radioGroup;
    RadioButton radioButtonTime;
    RadioButton radioButtonPop;

    private User auth=new User();
    private Post post1 = new Post();
    private DatabaseReference updateData1;
    private SimpleAdapter simpleAdapter;
    private ListView androidListView;
    private String clickedID = "";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);
        doAlways();
    }

    public void doAlways(){
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        radioGroup = findViewById(R.id.radio_group);
        radioButtonTime = findViewById(R.id.time);
        radioButtonPop = findViewById(R.id.popularity);

        mMainNav = findViewById(R.id.main_nav);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(ForumMainActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(ForumMainActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(ForumMainActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    case R.id.navigation_forum:
                        doAlways();
                        return true;

                    default:
                        return false;

                }

            }
        });


        postRef = firebaseDatabase.getReference().child("Posts");
        userRef = firebaseDatabase.getReference().child("Users");

        final List<HashMap<String,String>> stringRemoved = new ArrayList<>();


        final List<HashMap<String, String>> stringList = new ArrayList<>();
        final ArrayList<Post> postList= new ArrayList<Post>();

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    final Post post= postSnapshot.getValue(Post.class);
                    postList.add(post);
                }

                if(clickedID=="TIME"){
                    Collections.sort(postList, Post.compareByTime);
                }

                if (clickedID=="SCORE"){
                    Collections.sort(postList, Post.compareByScore);
                }

                for (int i = 0; i < postList.size(); i++) {
                    final HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("blog_user_name", postList.get(i).getUsername());
                    System.out.println("IN HM USERNAME: "+postList.get(i).getUsername());

                    DateFormat df = android.text.format.DateFormat.getDateFormat(ForumMainActivity.this);

                    String date = df.format(postList.get(i).getDatePosted());
                    hm.put("blog_date", postList.get(i).getCreatedAt());
                    hm.put("blog_title", postList.get(i).getPostTitle());
                    //hm.put("blog_image", postList.get(i).getImageURLs());
                    hm.put("blog_dec", postList.get(i).getBody());
                    hm.put("blog_like_count", postList.get(i).getScore());
                    stringList.add(hm);

                }

                String[] from = {"blog_user_name", "blog_date", "blog_title"/*, "blog_image"*/, "blog_desc", "blog_like_count"};
                int[] to = {R.id.blog_user_name, R.id.blog_date, R.id.blog_title/*, R.id.blog_image*/, R.id.blog_desc, R.id.blog_like_count};

                simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ForumMainActivity.this, stringList, R.layout.forum1_list_item, from, to);

                androidListView = findViewById(R.id.listView);

                androidListView.setAdapter(simpleAdapter);

                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {

                        final Post newPost = (Post) parent.getItemAtPosition(position);
                        final ConstraintLayout cl = view.findViewById(R.id.blogConstraint);
                        final TextView unhide = view.findViewById(R.id.unhidebutton);

                        view.findViewById(R.id.blog_user_name).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String mail=postList.get(position).getAuthorEmail();
                                Intent intent = new Intent(ForumMainActivity.this, OtherProfileActivity.class);

                                intent.putExtra("mail",mail);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                        view.findViewById(R.id.blog_comment_icon).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id = postList.get(position).getPostID();
                                Intent intent = new Intent(ForumMainActivity.this, ForumCommentActivity.class);
                                intent.putExtra("postid", id);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                        view.findViewById(R.id.hidebutton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                cl.setVisibility(View.GONE);
                                unhide.setVisibility(View.VISIBLE);

                            }
                        });
                        view.findViewById(R.id.unhidebutton).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cl.setVisibility(View.VISIBLE);
                                unhide.setVisibility(View.GONE);
                            }
                        });
                        view.findViewById(R.id.blog_up_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String postID = postList.get(position).getPostID();
                                mAuth = FirebaseAuth.getInstance();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference ref = db.getReference();
                                DatabaseReference userRef = ref.child("Users");
                                final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        int flag = 0;
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                            if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                                                auth = (User) postSnapshot.getValue(User.class);
                                                flag = 1;
                                            }
                                            if (flag == 1) {
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
                                                        int gFlag = 0;
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
                                                            if (flag1 == 1 && flag2 == 0) {
                                                                updateData1.child("upvoted").setValue(res1);
                                                                post.undoUpvote();
                                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                                            } else if (flag1 == 0 && flag2 == 0) {
                                                                auth.addUpvoted(postID);
                                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                                post.upvote();
                                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                                            } else if (flag2 == 1 && flag1 == 0) {
                                                                auth.addUpvoted(postID);
                                                                post.undoDownVote();
                                                                post.upvote();
                                                                updateData1.child("upvoted").setValue(auth.getUpvoted());
                                                                updateData1.child("downvoted").setValue(res2);
                                                                updateData2.child("rateUp").setValue(post.getRateUp());
                                                                updateData2.child("rateDown").setValue(post.getRateDown());
                                                            } else {
                                                                System.out.println("ERROR ERROR ERROR UPVOTE ERROR");
                                                            }

                                                            View vi = androidListView.getChildAt(position -
                                                                    androidListView.getFirstVisiblePosition());

                                                            //view.findViewById(R.id.blog_up_btn).setBackgroundColor(Color.GREEN);
                                                            TextView score6 = (TextView) vi.findViewById(R.id.blog_like_count);
                                                            score6.setText(post.getScore());

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
                        view.findViewById(R.id.blog_down_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String postID = postList.get(position).getPostID();
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
                                                            View vi = androidListView.getChildAt( position -
                                                                    androidListView.getFirstVisiblePosition());

                                                            //view.findViewById(R.id.blog_down_btn).setBackgroundColor(Color.RED);
                                                            TextView score6 = (TextView) vi.findViewById(R.id.blog_like_count);
                                                            score6.setText(post.getScore());
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
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        newPost = findViewById(R.id.fab);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumMainActivity.this, ForumNewPostActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });



    }

    public void onRadioButtonClick2(View view) {
        clickedID = "TIME";
        doAlways();
    }


    public void onRadioButtonClick1(View view) {
        clickedID = "SCORE";
        doAlways();
    }

    public void onRadioButtonClick3(View view) {
        clickedID = "";
        doAlways();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isTaskRoot()){
            Intent intent = new Intent(ForumMainActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else{
            ForumMainActivity.this.finish();
        }
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        doAlways();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        //finish();
        doAlways();
    }

}
