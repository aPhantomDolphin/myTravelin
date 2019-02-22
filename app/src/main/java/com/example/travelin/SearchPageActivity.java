package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;

public class SearchPageActivity extends AppCompatActivity {

        private Realm realm;
        private Button filterButton;
        private String gender;
        private String rating;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_result);
            realm = Realm.getDefaultInstance();
            Bundle extras = getIntent().getExtras();
            gender = extras.getString("gender");
            rating = extras.getString("rating");

            filterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchPageActivity.this,SearchFilterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });

        }

}
