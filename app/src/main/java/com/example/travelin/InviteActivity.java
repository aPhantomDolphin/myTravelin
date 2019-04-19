package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
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

public class InviteActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        mAuth = FirebaseAuth.getInstance();

        mMainNav = findViewById(R.id.bottom_navigation);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(InviteActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_profile:
                        intent = new Intent(InviteActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(InviteActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(InviteActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        //User u = null;

        final ArrayList<String> res= new ArrayList<String>();
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        final User user = (User) postSnapshot.getValue(User.class);
                        final String firstKey = postSnapshot.getKey();

                        final String[] invites = user.getRoomInvites().split("\\|");
                        for (String str : invites) {
                            res.add(str);
                        }

                        // TODO : POPULATE LISTVIEW
                        // TODO : CHECK WHICH NAME THEY CLICK ON

                        for (String str : res) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("invite_room", str);
                            aList.add(hm);
                        }

                        String[] from = {"invite_room"};
                        int[] to = {R.id.listview_item_title};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/InviteActivity.this, aList, R.layout.activity_listview, from, to);
                        ListView androidListView = (ListView) findViewById(R.id.list_view_invites);
                        androidListView.setAdapter(simpleAdapter);
                        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String joinRoom = res.get(position);
                                //System.out.println("SEARCH USERS: "+mail);

                                user.addRoom(joinRoom);

                                DatabaseReference updateData = null;
                                updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(firstKey);

                                updateData.child("rooms").setValue(user.getRooms());
                                String invMinus = "";

                                for (String str : invites) {
                                    if (!joinRoom.equals(str)) {
                                        if (invMinus.equals("")) {
                                            invMinus += str;
                                        } else {
                                            invMinus += "|" + str;
                                        }
                                    }
                                }

                                user.setRoomInvites(invMinus);

                                updateData.child("roomInvites").setValue(invMinus);
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

    }
}
