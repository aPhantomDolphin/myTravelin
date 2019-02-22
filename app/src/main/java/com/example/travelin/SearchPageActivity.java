package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;

public class SearchPageActivity extends AppCompatActivity {

    private View searchUser;
    private Button backButton;
    private Button searchButton;
    private String genderPreferance="Both";
    private RatingBar star;
    Float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        searchUser = findViewById(R.id.user_name);
        backButton= findViewById(R.id.back_button);
        star = (RatingBar) findViewById(R.id.ratingBar);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(SearchPageActivity.this, LoginActivity.class); //make this ProfileActivity
                    startActivity(intent);
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }); //goes back to profile page

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    search();
                    Intent intent = new Intent(SearchPageActivity.this, SearchResultActivity.class);
                    startActivity(intent);
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
                if (checked)
                    genderPreferance ="Male";
                break;
            case R.id.checkBox_female:
                if (checked)
                    genderPreferance="Female";
                break;
            default:
                genderPreferance="Both";
                break;
        }
    }

    public void search(){
        rating = star.getRating();
    }

}
