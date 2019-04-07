package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Posts");

        final List<HashMap<String, String>> stringList = new ArrayList<>();
        final ArrayList<Post> postList= new ArrayList<Post>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Post post= postSnapshot.getValue(Post.class);
                    if(!mAuth.getCurrentUser().getUid().equals(postSnapshot.getKey())) {
                        postList.add(post);
                    }
                }


                for (int i = 0; i < postList.size(); i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();

                    hm.put("blog_user_name", postList.get(i).getUsername());
                    hm.put("blog_date", postList.get(i).getDatePosted().toString());
                    hm.put("blog_title", postList.get(i).getTitle());
                    hm.put("blog_image", postList.get(i).getImageURLs());
                    hm.put("blog_dec", postList.get(i).getBody());
                    hm.put("blog_like_count", Integer.toString(postList.get(i).getRateUp() - postList.get(i).getRateDown()));
                    stringList.add(hm);
                }

                String[] from = {"blog_user_name", "blog_date", "blog_title", "blog_image", "blog_desc", "blog_like_count"};
                int[] to = {R.id.blog_user_name, R.id.blog_date, R.id.blog_title, R.id.blog_image, R.id.blog_desc, R.id.blog_like_count};

                SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ForumMainActivity.this, stringList, R.layout.activity_listview, from, to);

                ListView androidListView = (ListView) findViewById(R.id.listView);

                androidListView.setAdapter(simpleAdapter);

                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


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
