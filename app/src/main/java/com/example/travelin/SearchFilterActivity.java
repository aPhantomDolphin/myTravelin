package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
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
    private TextInputEditText searchedUN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        searchUser = findViewById(R.id.user_name);
        //
        // backButton= findViewById(R.id.);
        star = findViewById(R.id.ratingBar);
        rating = star.getRating();

        searchedUN=findViewById(R.id.search_text);

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

/** final FirebaseDatabase database = FirebaseDatabase.getInstance();
 DataBaseReference ref = database.getReference("insert_our_URL_here");
 List<User> users = new ArrayList<User>;
 String tag = tagWeWant;

 this will hypothetically work. If we have to deserialize
 the tag list as well, we may have to do some altering
 of class structure

 this query deserializes all of the user objects that we have
	stored in the database and adds them to a list

ref.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnap) {
        for (DataSnapshot userSnap : dataSnap.getChildren()) {
        User user = userSnap.getValue(User.class);
        users.add(user);
        }
        }

@Override
public void onCancelled(FirebaseError fbErr) {
        //do nothing
        }
        });


 Go through each user. If they have the
	tag that we are searching for, recommend them

        List<User> recUsers = new ArrayList<User>;
for (User u : users) {
        List<Tag> tagList = u.getTags();
        for (Tag t : tagList) {
        if (t.equals(tagWeWant)) {
        recUsers.add(u);
        }
        }
        }



         query to return all users of a certain gender
        Query query = reference.child("User").orderByChild("gender").equalTo(genderString);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
        // dataSnapshot is the "issue" node with all children with id 0
        for (DataSnapshot issue : dataSnapshot.getChildren()) {
        // add to user list
        }
        }
        }

@Override
public void onCancelled(DatabaseError databaseError) {

        }
        });
**/



