package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    User auth=new User();
    ArrayList<Destinations> dests=new ArrayList<Destinations>();
    //List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
    /*int[] ImageView = new int[]{
            R.drawable.beach, R.drawable.beach, R.drawable.beach, R.drawable.beach,
            R.drawable.beach, R.drawable.profilepic, R.drawable.profilepic, R.drawable.profilepic,
    };*/


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        messageButton = findViewById(R.id.fab);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MessageHome.class));
            }
        });
        doAlways();

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }

    public void doAlways(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_profile:
                        intent = new Intent(HomeActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;


                    case R.id.navigation_search:
                        intent = new Intent(HomeActivity.this, SearchFilterActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(HomeActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }
            }
        });

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference ref=db.getReference();

        DatabaseReference userRef = ref.child("Users");
        final DatabaseReference destRef = ref.child("Destinations");

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int flag=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                        auth = (User) postSnapshot.getValue(User.class);
                        flag=1;
                    }
                }

                String master=auth.getInterestsNew();

                System.out.println("HERE:::::"+master);

                final String[] arr=master.split(",");

                System.out.println("HERE1:::::"+arr[0]);

                destRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        //User auth=auth1;
                        dests=new ArrayList<Destinations>();
                        for (DataSnapshot postSnapshot : dataSnapshot1.getChildren()) {
                            Destinations dest = (Destinations) postSnapshot.getValue(Destinations.class);
                            String tags=dest.getTags();
                            System.out.println("HERE2:::::"+tags);
                            HashMap<Integer,String> map=new HashMap<>();
                            String[] temp=tags.split("@");
                            int count=0;
                            for(String a : temp){
                                map.put(count,a);
                                count++;
                            }

                            for(String b : arr){
                                if(map.containsValue(b)){
                                    dests.add(dest);
                                }
                            }
                        }
                        //fix this part this doesnt work
                        if(dests.size()==0){
                            for (DataSnapshot postSnapshot : dataSnapshot1.getChildren()) {
                                Destinations dest = (Destinations) postSnapshot.getValue(Destinations.class);
                                dests.add(dest);
                            }
                        }

                        final ArrayList<User> res= new ArrayList<User>();

                        final String[] city = new String[]{
                                "Los Angeles", "Miami", "New York City", "Chicago",
                                "Orlando", "ListView Title 6", "ListView Title 7", "ListView Title 8",
                        };


                        int[] ImageView = new int[]{
                                R.drawable.mia1, R.drawable.chi1, R.drawable.or2, R.drawable.nyc2,
                                R.drawable.mad, R.drawable.la1
                        };

                        String[] info = new String[]{
                                "More info", "More info", "More info", "More info",
                                "More info",
                        };


                        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

                        for (int i = 0; i < dests.size(); i++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("title", dests.get(i).getCityName());
                            hm.put("info", dests.get(i).getInfo());
                            if(dests.get(i).getCityName().equals("Miami")){
                            hm.put("image", Integer.toString(ImageView[0]));}
                            else if(dests.get(i).getCityName().equals("Chicago")){
                                hm.put("image", Integer.toString(ImageView[1]));}
                                else if(dests.get(i).getCityName().equals("Orlando")){
                                hm.put("image", Integer.toString(ImageView[2]));}
                                else if(dests.get(i).getCityName().equals("New York City")){
                                hm.put("image", Integer.toString(ImageView[3]));}
                            else if(dests.get(i).getCityName().equals("Los Angeles")){
                                hm.put("image", Integer.toString(ImageView[5]));}
                                else{
                                    hm.put("image",Integer.toString(ImageView[4]));

                            }

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
                                String name = aList.get(position).get("title");
                                System.out.println("CITYNAMECLICKED"+name);
                                Intent intent = new Intent(HomeActivity.this, CityPageActivity.class);
                                intent.putExtra("city",name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        System.out.println("HERE3:::::"+dests.size());
    }

    public void cityClicked(View view){
        System.out.println("REACHED HERE INSTEAD");
        Intent intent = new Intent(HomeActivity.this, CityPageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        doAlways();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        doAlways();
    }

}


