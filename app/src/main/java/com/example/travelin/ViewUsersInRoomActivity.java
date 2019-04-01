package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class ViewUsersInRoomActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView userList;
    private String roomname;
    private String selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_in_room);
        final ArrayList<String> res= new ArrayList<String>();
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();


        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Rooms");
        User u = null;

        //final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Room room = snap.getValue(Room.class);
                    if (room.getRoomname().equals(roomname)) {
                        String[] inRoom = room.getUsers().split("\\|");
                        for (int i = 0; i < inRoom.length; i++) {
                            // TODO: POPULATE VIEW WITH ALL USERS
                            res.add(inRoom[i]);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // TODO : SET VARIABLE FOR USER THEY CLICKED ON
        // TODO : SET EXTRA, GO TO OTHER PROFILE IF CLICKED

        for (String str : res) {
            HashMap<String, String> hm = new HashMap<>();

            hm.put("listview_title", str);
            aList.add(hm);
        }

        String[] from = {"listview_title"};
        int[] to = {R.id.listview_item_title};

        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ViewUsersInRoomActivity.this, aList, R.layout.activity_listview, from, to);
        ListView androidListView = (ListView) findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mail=res.get(position);
                System.out.println("SEARCH USERS: "+mail);
                Intent intent = new Intent(ViewUsersInRoomActivity.this, OtherProfileActivity.class);

                intent.putExtra("mail",mail);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }

}
