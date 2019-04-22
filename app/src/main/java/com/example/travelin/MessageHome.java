package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageHome extends AppCompatActivity {

    // TODO : ACTUALLY CREATE A ROOMNAME TEXTBOX
    // TODO: POPULATE LISTVIEW WITH ROOM NAMES

    private Button addGroupButton;
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-learn-f2515.appspot.com");
    private FirebaseAuth mAuth;
    private EditText roomName;
    private FloatingActionButton viewInvites;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_home);
        mAuth = FirebaseAuth.getInstance();
        addGroupButton = findViewById(R.id.new_group_button);

        mMainNav = findViewById(R.id.bottom_navigation);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(MessageHome.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_profile:
                        intent = new Intent(MessageHome.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(MessageHome.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(MessageHome.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference ref=database.getReference();
        final DatabaseReference usesRef=ref.child("Users");

        viewInvites = findViewById(R.id.viewInvites);
        viewInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageHome.this, InviteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        usesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User use = new User();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    System.out.println(mAuth.getCurrentUser().getUid());

                    if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                     /**   final User u = snap.getValue(User.class);
                        String roomString = u.getRooms();
                        final String[] rooms = roomString.split("\\|");
                        ArrayList<String> shortened = new ArrayList<>();
                        for (int j = 0; j < rooms.length; j++) {
                            String[] cutoffs = rooms[j].split("-");
                            if (cutoffs.length == 2) {
                                shortened.add(cutoffs[1]);
                            } else {
                                if (cutoffs[2].equals(u.getEmail())) {
                                    shortened.add(cutoffs[1]);
                                } else {
                                    shortened.add(cutoffs[2]);
                                }
                            }
                            //shortened.add(cutoffs[1]);
                        }
                        for (int j = 0; j < shortened.size(); j++) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("room_name", shortened.get(j));
                            aList.add(hm);
                        }**/

                        final User u = snap.getValue(User.class);
                        String roomString = u.getRooms();
                        final String[] rooms = roomString.split("\\|");
                        for (int i = 0; i < rooms.length; i++) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("room_name", rooms[i]);
                            aList.add(hm);

                        }

                        // TODO: POPULATE LISTVIEW WITH ROOM NAMES
                        /**
                         * Stephen's attempt at populating a listview of roomnames using a crude
                         * implementation of the searchpageactivity listview.
                         */
                        String[] from = {"room_name"};
                        int[] to = {R.id.listview_item_title};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/MessageHome.this, aList, R.layout.activity_listview, from, to);
                        ListView androidListView = (ListView) findViewById(R.id.list_view_message);
                        androidListView.setAdapter(simpleAdapter);
                        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String roomNeeded = rooms[position];
                                System.out.println("ROOM "+roomNeeded);
                                //Intent intent = new Intent(MessageHome.this, ViewUsersInRoomActivity.class);
                                Intent intent = new Intent(MessageHome.this, MessageActivity.class);
                                intent.putExtra("name", u.getName());
                                intent.putExtra("room",roomNeeded);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MessageHome.this);
                alertDialog.setTitle("New Room Name");
                alertDialog.setMessage("Enter new room name");

                final EditText editText = new EditText(getApplicationContext());
                alertDialog.setView(editText);

                alertDialog.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String checkEmpty = editText.getText().toString();
                        if (checkEmpty.equals("")) {
                            // TODO : MAKE TOAST
                        } else {
                            final String roomName = checkEmpty;
                            usesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User u = new User();
                                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                        if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                                            u = snap.getValue(User.class);
                                            u.addRoom("observable-" + roomName);

                                            DatabaseReference updateData = null;
                                            updateData = FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(snap.getKey());

                                            updateData.child("rooms").setValue(u.getRooms());

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
                });

                alertDialog.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO : MAKE TOAST
                    }
                });

                alertDialog.show();
            }
        });

    }
}
