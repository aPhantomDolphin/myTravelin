package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import io.realm.Realm;
//import io.realm.RealmQuery;
//import io.realm.RealmResults;

public class SearchPageActivity extends AppCompatActivity {

    //private Realm realm;
    private Button filterButton;
    private String gender;
    private String rating;
    private String searchUN;
    private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;

    String[] listviewTitle = new String[]{
            "ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4",
            "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8",
    };


    int[] listviewImage = new int[]{
            R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
            R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
    };

    String[] listviewShortDescription = new String[]{
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
            "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mAuth = FirebaseAuth.getInstance();
        //realm = Realm.getDefaultInstance();
        Bundle extras = getIntent().getExtras();
        gender = extras.getString("gender");
        rating = extras.getString("rating");
        searchUN=extras.getString("searchUN");
        filterButton= findViewById(R.id.filter_button);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchPageActivity.this, SearchFilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference();
        DatabaseReference usesRef=ref.child("Users");
        final ArrayList<User> res= new ArrayList<User>();

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(SearchPageActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_profile:
                        intent = new Intent(SearchPageActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.nav_search:
                        intent = new Intent(SearchPageActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        usesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //res.clear();
                User auth=new User();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user= postSnapshot.getValue(User.class);
                    if(!mAuth.getCurrentUser().getUid().equals(postSnapshot.getKey())){
                        res.add(user);
                    }
                    else{
                        auth=user;
                    }

                }

                for(int i=0;i<res.size();i++) {
                    if (!searchUN.isEmpty()) {
                        if (res.get(i).getUsername().equals(searchUN)) {
                            res.remove(i);
                            i--;
                        }
                    }
                }
                for(int i=0;i<res.size();i++) {
                    if (!gender.equals("Both")) {
                        if (!(res.get(i).getGender().equals(gender))) {
                            res.remove(i);
                            i--;
                        }
                    }
                }

                for(int i=0;i<res.size();i++){
                    //System.out.println("WOWRATING"+res.get(i).getAvg());
                    //System.out.println("YOURSETTING"+rating);
                    if(res.get(i).getAvg()<Double.parseDouble(rating)){
                        res.remove(i);
                        i--;
                    }
                }
                try {
                    String[] arrOfStr = auth.getBlock().split(",");
                    for (String a : arrOfStr) {
                        for (int j = 0; j < res.size(); j++) {
                            if (res.get(j).getEmail().equals(a)) {
                                res.remove(j);
                            }
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                for (int i = 0; i < res.size(); i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();

                    hm.put("listview_title", res.get(i).getName());
                    hm.put("listview_discription", res.get(i).getBio());
                    hm.put("listview_image", "NOIMG");
                    aList.add(hm);
                }

                String[] from = {"listview_image", "listview_title", "listview_discription"};
                int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description};

                SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/SearchPageActivity.this, aList, R.layout.activity_listview, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list_view);
                androidListView.setAdapter(simpleAdapter);
                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String mail=res.get(position).getEmail();
                        System.out.println("SEARCH USERS: "+mail);
                        Intent intent = new Intent(SearchPageActivity.this, OtherProfileActivity.class);

                        intent.putExtra("mail",mail);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public void otherProfileClicked(View view){
        System.out.println("REACHED HERE INSTEAD");
        Intent intent = new Intent(SearchPageActivity.this, OtherProfileActivity.class);

        //intent.putExtra("username",);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}