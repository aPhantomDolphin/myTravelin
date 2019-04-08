package com.example.travelin;

import android.content.Intent;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private FloatingActionButton newPost;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Posts");

        final List<HashMap<String, String>> stringList = new ArrayList<>();
        final ArrayList<Post> postList= new ArrayList<Post>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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

                ListView androidListView = findViewById(R.id.listView);

                androidListView.setAdapter(simpleAdapter);

                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Post newPost = (Post) parent.getItemAtPosition(position);
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

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String currentID = firebaseUser.getUid();


    }

}
