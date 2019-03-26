package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


   private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;
    private FloatingActionButton messageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        messageButton = findViewById(R.id.fab);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MessageActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference();


        final ArrayList<User> res= new ArrayList<User>();

                final String[] city = new String[]{
                        "Los Angeles", "Miami", "New York City", "Chicago",
                        "Orlando", "ListView Title 6", "ListView Title 7", "ListView Title 8",
                };


                int[] ImageView = new int[]{
                        R.drawable.beach, R.drawable.beach, R.drawable.beach, R.drawable.beach,
                        R.drawable.beach, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
                };

                String[] info = new String[]{
                        "More info", "More info", "More info", "More info",
                        "More info",
                };

                mMainNav = findViewById(R.id.main_nav);

                mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId()) {

                            case R.id.nav_profile:
                                intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                try{
                                    System.out.println("CLICKED PROFILE");
                                    startActivity(intent);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                                return true;


                            case R.id.nav_search:
                                intent = new Intent(HomeActivity.this, SearchFilterActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return true;

                            default:
                                return false;

                        }

                    }
                });





                List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < 5; i++) {
                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("title", city[i]);
                    hm.put("info", info[i]);
                    hm.put("image", Integer.toString(ImageView[i]));
                    aList.add(hm);
                }

                String[] from = {"image", "title", "info"};
                int[] to = {R.id.HomeImageView, R.id.city, R.id.info};

                SimpleAdapter simpleAdapter = new SimpleAdapter(HomeActivity.this, aList, R.layout.listview_home, from, to);
                ListView androidListView = (ListView) findViewById(R.id.list_view_home);
                androidListView.setAdapter(simpleAdapter);
                androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = city[position];
                        Intent intent = new Intent(HomeActivity.this, CityPageActivity.class);
                        intent.putExtra("city",name);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
//
            }

    public void cityClicked(View view){
        System.out.println("REACHED HERE INSTEAD");
        Intent intent = new Intent(HomeActivity.this, CityPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}


