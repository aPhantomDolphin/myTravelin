package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OtherProfileActivity extends AppCompatActivity {

    private Button rateUser;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        rateUser = findViewById(R.id.rate_and_review_profile);
        rateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,RateUserActivity.class);
                startActivity(intent);
            }
        });



    }
}