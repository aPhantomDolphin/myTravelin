package com.example.travelin;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class RateUserActivity extends AppCompatActivity {

    private TextInputEditText giveRating;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_user);

        giveRating = findViewById(R.id.user_rating);
        submit = findViewById(R.id.submit_rating);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();



            }
        });

    }
}
