package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class ForumNewPostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;
    private Button createPost;
    private TextView postDesc;
    private ImageView postImg;
    private TextView postTitle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ConstraintLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_new_post);

        parentLayout = findViewById(R.id.constraintLayout);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    hideKeyboard(v);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_profile:
                        intent = new Intent(ForumNewPostActivity.this, ProfileActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;


                    case R.id.navigation_search:
                        intent = new Intent(ForumNewPostActivity.this, SearchFilterActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        ForumNewPostActivity.this.finish();
                        return true;

                    default:
                        return false;

                }

            }
        });

        postDesc = findViewById(R.id.new_post_desc);
        postImg = findViewById(R.id.new_post_image);
        postTitle = findViewById(R.id.new_post_title);

        createPost = findViewById(R.id.post_btn);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("Posts");
                DatabaseReference newPostRef = databaseReference.push();

                Date currentDate = Calendar.getInstance().getTime();
                Post creatingPost = new Post(mAuth.getCurrentUser().getEmail(), currentDate, postDesc.getText().toString(), newPostRef.getKey() ,postTitle.getText().toString());
                creatingPost.setUsername(getAuthorName());

                newPostRef.setValue(creatingPost);

            }
        });







    }

    private String getAuthorName() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Users");
        final StringBuffer authorName = new StringBuffer("");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);

                    if (mAuth.getCurrentUser().getUid().equals(user.getEmail())) {
                        authorName.append(user.getUsername());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return authorName.toString();

    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
