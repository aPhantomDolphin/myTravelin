package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    private TextInputEditText searchedUN;
    private BottomNavigationView mMainNav;
    private Button similarPeopleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        searchUser = findViewById(R.id.user_name);
        //
        // backButton= findViewById(R.id.);
        star = findViewById(R.id.ratingBar);
        //rating = star.getRating();
        System.out.println("YOURSET2"+rating);

        searchedUN=findViewById(R.id.search_text);

        home = findViewById(R.id.home_button);
        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        System.out.println("AT HOME");
                        intent = new Intent(SearchFilterActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_profile:
                        intent = new Intent(SearchFilterActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(SearchFilterActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(SearchFilterActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });


        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    rating = star.getRating();
                    Intent intent = new Intent(SearchFilterActivity.this, SearchPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("gender",genderPreferance);
                    //System.out.println("YOURSET1"+rating);
                    intent.putExtra("rating",String.valueOf(rating));
                    intent.putExtra("searchUN",searchedUN.getText().toString());
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

        similarPeopleButton = findViewById(R.id.search_similar_button);
        similarPeopleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(SearchFilterActivity.this, SearchSimilarPageActivity.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isTaskRoot()){
            Intent intent = new Intent(SearchFilterActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else{
            SearchFilterActivity.this.finish();
        }
    }

}
