package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth mAuth;
    private EditText roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_home);
        mAuth = FirebaseAuth.getInstance();
        addGroupButton = findViewById(R.id.new_group_button);

        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference ref=database.getReference();
        final DatabaseReference usesRef=ref.child("Users");

        usesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //User u = new User();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
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
                        ListView androidListView = (ListView) findViewById(R.id.list_view);
                        androidListView.setAdapter(simpleAdapter);
                        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String roomNeeded = rooms[position];
                                System.out.println("ROOM "+roomNeeded);
                                Intent intent = new Intent(MessageHome.this, MessageActivity.class);
                                intent.putExtra("name", u.getName());
                                intent.putExtra("room",roomNeeded);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                usesRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User u = new User();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                                u = snap.getValue(User.class);
                                u.addRoom("observable-" + roomName.toString());
                                // TODO : ACTUALLY CREATE A ROOMNAME TEXTBOX
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
