package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class CityPageActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;
    private String cityName;
    private TextView cityNameView;
    private TextView infoView;
    private ImageButton findflight;
    private ImageButton findHotels;
    private String ha;
    private TextView tag1;
    private TextView tag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mAuth = FirebaseAuth.getInstance();
        cityName = getIntent().getExtras().getString("city");
        cityNameView = findViewById(R.id.city1);
        infoView = findViewById(R.id.info1);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Destinations");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    if(cityName.equals(postSnapshot.getKey())){

                        DatabaseReference newRef = myRef.child(cityName);


                        System.out.println(newRef.toString());
                        cityNameView.setText(cityName);
                        //Map<String, String> map = dataSnapshot.getValue(Map.class);
                        //infoView.setText(map.get("Info"));
                        //infoView.setText(newRef.getKey());
                        newRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                infoView.setText(dataSnapshot.child("Info").getValue().toString());
                                tag1.setText(dataSnapshot.child("Related Tags").child("Tag1").getValue().toString());
                                tag2.setText(dataSnapshot.child("Related Tags").child("Tag2").getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        intent = new Intent(CityPageActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;


                    case R.id.nav_profile:
                        intent = new Intent(CityPageActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;


                    case R.id.nav_search:
                        intent = new Intent(CityPageActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });


        if (cityName.equals("Los Angeles")){ ha = "MAD";}
        if (cityName.equals("Chicago")){ ha = "ORD";}
        if (cityName.equals("New York City")){ ha = "JFK";}
        if (cityName.equals("Miami")){ ha = "EWR";}
        if (cityName.equals("Orlando")){ ha = "MCO";}

        findflight = findViewById(R.id.findflights);
        findflight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( CityPageActivity.this, FlightSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        findHotels = findViewById(R.id.findhotels);
        findHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( CityPageActivity.this, HotelActivity.class);
                intent.putExtra("city",ha);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }
}
