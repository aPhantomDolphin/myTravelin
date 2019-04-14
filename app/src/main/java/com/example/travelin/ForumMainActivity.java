package com.example.travelin;

import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private DatabaseReference postRef;
    private FloatingActionButton newPost;
    private BottomNavigationView mMainNav;

    private User auth=new User();
    private Post post1 = new Post();
    private DatabaseReference updateData1;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

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
                        return false;

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

                for (int i = 0; i < postList.size(); i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("blog_user_name", postList.get(i).getUsername());
                    System.out.println("IN HM: "+postList.get(i).getUsername());
                    //hm.put("blog_date", postList.get(i).getDatePosted().toString());
                    hm.put("blog_title", postList.get(i).getPostTitle());
                    //hm.put("blog_image", postList.get(i).getImageURLs());
                    hm.put("blog_dec", postList.get(i).getBody());
                    hm.put("blog_like_count", postList.get(i).getScore());
                    stringList.add(hm);

                }

                String[] from = {"blog_user_name"/*,"blog_date"*/, "blog_title"/*, "blog_image"*/, "blog_desc", "blog_like_count"};
                int[] to = {R.id.blog_user_name/*,R.id.blog_date*/, R.id.blog_title/*, R.id.blog_image*/, R.id.blog_desc, R.id.blog_like_count};

                SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ForumMainActivity.this, stringList, R.layout.forum1_list_item, from, to);

                final ListView androidListView = findViewById(R.id.listView);

                androidListView.setAdapter(simpleAdapter);


                /*ImageView imageView = findViewById(R.id.more);
                registerForContextMenu(imageView);*/

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
                                doUpVote(parent, view, position, id, postList);
                            }
                        });
                        view.findViewById(R.id.blog_down_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doDownVote(parent, view, position, id, postList);
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

    public void doUpVote(AdapterView<?> parent, View view, final int position, long id, final ArrayList<Post>  postList){

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        auth = postSnapshot.getValue(User.class);
                        updateData1 = userRef.child(postSnapshot.getKey());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post1 = postList.get(position);
                String id = post1.getPostID();
                DatabaseReference updateData2 = postRef.child(id);
                int gFlag = 0;
                try{
                    String masterUp = auth.getUpvoted();
                    String[] arrT = masterUp.split("@");
                    int flag = 0;
                    String res = "";
                    for(String a : arrT){
                        int toAdd = 0;
                        if(id.equals(a)){
                            //I have already upvoted this person
                            //Remove from auth.upvoted, decrease upvotes of post
                            flag = 1;
                            toAdd = 1;
                            //post1.upvote();
                        }
                        if(!a.equals("") && toAdd == 0){
                            res += "@";
                            res += a;
                        }
                    }

                    if(flag == 1){
                        updateData1.child("upvoted").setValue(res);
                        updateData2.child("upvotes").setValue(post1.getRateUp());
                        updateData2.child("downvotes").setValue(post1.getRateDown());
                    }
                    else{
                        gFlag++;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    String master = auth.getDownvoted();
                    String[] arrT = master.split("@");
                    int flag = 0;
                    String res = "";
                    for (String a : arrT) {
                        int toAdd = 0;

                        if (id.equals(a)) {
                            //I have already upvoted this person
                            //Remove from auth.upvoted, decrease upvotes of post
                            flag = 1;
                            toAdd = 1;
                            //post1.downvote();
                            post1.upvote();
                            //post1.upvote();
                        }

                        if (!a.equals("") && toAdd == 0) {
                            res += "@";
                            res += a;
                        }
                    }

                    if (flag == 1) {
                        updateData1.child("upvoted").setValue(res);
                        updateData2.child("upvotes").setValue(post1.getRateUp());
                        updateData2.child("downvotes").setValue(post1.getRateDown());
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
                    auth.addUpvoted(id);
                    post1.upvote();
                    updateData1.child("upvoted").setValue(auth.getUpvoted());
                    updateData2.child("upvotes").setValue(post1.getRateUp());
                    updateData2.child("downvotes").setValue(post1.getRateDown());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void doDownVote(AdapterView<?> parent, View view, final int position, long id, final ArrayList<Post> postList){

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        auth = postSnapshot.getValue(User.class);
                        updateData1 = userRef.child(postSnapshot.getKey());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                post1 = postList.get(position);
                String id = post1.getPostID();
                DatabaseReference updateData2 = postRef.child(id);
                int gFlag = 0;
                try{
                    String masterUp = auth.getUpvoted();
                    String[] arrT = masterUp.split("@");
                    int flag = 0;
                    String res = "";
                    for(String a : arrT){
                        int toAdd = 0;
                        if(id.equals(a)){
                            //I have already upvoted this person
                            //Remove from auth.upvoted, decrease upvotes of post
                            flag = 1;
                            toAdd = 1;
                            post1.downvote();
                        }
                        if(!a.equals("") && toAdd == 0){
                            res += "@";
                            res += a;
                        }
                    }

                    if(flag == 1){
                        updateData1.child("upvoted").setValue(res);
                        updateData2.child("upvotes").setValue(post1.getRateUp());
                        updateData2.child("downvotes").setValue(post1.getRateDown());
                    }
                    else{
                        gFlag++;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    String master = auth.getDownvoted();
                    String[] arrT = master.split("@");
                    int flag = 0;
                    String res = "";
                    for (String a : arrT) {
                        int toAdd = 0;

                        if (id.equals(a)) {
                            //I have already upvoted this person
                            //Remove from auth.upvoted, decrease upvotes of post
                            flag = 1;
                            toAdd = 1;

                        }

                        if (!a.equals("") && toAdd == 0) {
                            res += "@";
                            res += a;
                        }
                    }

                    if (flag == 1) {
                        updateData1.child("upvoted").setValue(res);
                        updateData2.child("upvotes").setValue(post1.getRateUp());
                        updateData2.child("downvotes").setValue(post1.getRateDown());
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
                    auth.addDownvoted(id);
                    post1.downvote();
                    updateData1.child("downvoted").setValue(auth.getDownvoted());
                    updateData2.child("upvotes").setValue(post1.getRateUp());
                    updateData2.child("downvotes").setValue(post1.getRateDown());
                }
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
