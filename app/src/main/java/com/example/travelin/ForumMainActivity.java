package com.example.travelin;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ForumMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private ForumHomeFragment forumHomeFragment;



    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_content_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        forumHomeFragment = new ForumHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, forumHomeFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String currentID = firebaseUser.getUid();




    }

}
