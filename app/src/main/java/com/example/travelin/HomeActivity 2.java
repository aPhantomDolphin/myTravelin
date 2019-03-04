package com.example.testrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textView = findViewById(R.id.home_text);

        textView.setText(user.getEmail());


    }

}
