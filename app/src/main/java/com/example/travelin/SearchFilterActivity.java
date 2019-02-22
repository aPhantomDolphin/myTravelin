package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;

public class SearchFilterActivity extends AppCompatActivity {

    private View searchUser;
    private Button backButton;
    private Button searchButton;
    private String genderPreferance="Both";
    private RatingBar star;
    private Float rating;
    private CheckBox check;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        searchUser = findViewById(R.id.user_name);
        //
        // backButton= findViewById(R.id.);
        star = findViewById(R.id.ratingBar);
        rating = star.getRating();
        home = findViewById(R.id.home_button);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchFilterActivity.this, HomeActivity.class);
                try{
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(SearchFilterActivity.this, SearchPageActivity.class);
                    intent.putExtra("gender",genderPreferance);
                    intent.putExtra("rating",String.valueOf(rating));
                    try{
                        startActivity(intent);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox_male:
                if (!checked)
                    genderPreferance ="Female";
                break;
            case R.id.checkBox_female:
                if (!checked)
                    genderPreferance="Male";
                break;
            default:
                genderPreferance="Both";
                break;
        }
    }
}
