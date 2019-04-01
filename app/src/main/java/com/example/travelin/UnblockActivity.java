package com.example.travelin;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnblockActivity extends AppCompatActivity {

    private ListView allUsers;
    private String selectedUser;
    private FirebaseAuth mAuth;
    private String unblockUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unblock);

        final String needsUnblock = "";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        User u = null;

        final ArrayList<String> res= new ArrayList<String>();
        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<User> userlist = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    //User user = (User) postSnapshot.getValue(User.class);
                    //userlist.add(user);
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        final User user = (User) postSnapshot.getValue(User.class);

                        String[] blockedUsers = user.getBlock().split(",");

                        // TODO : POPULATE LISTVIEW
                        // TODO : CHECK WHICH NAME THEY CLICK ON

                        for (String str : res) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("listview_title", str);
                            aList.add(hm);
                        }

                        String[] from = {"listview_title"};
                        int[] to = {R.id.listview_item_title};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/UnblockActivity.this, aList, R.layout.activity_listview, from, to);
                        ListView androidListView = (ListView) findViewById(R.id.list_view);
                        androidListView.setAdapter(simpleAdapter);
                        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String needsUnblock=res.get(position);
                                //System.out.println("SEARCH USERS: "+mail);

                            }
                        });

                        user.clearBlock();
                        String continueBlock = "";
                        for (int i = 0; i < blockedUsers.length; i++) {
                            if (!blockedUsers[i].equals(needsUnblock)) {
                                user.addBlock(blockedUsers[i]);
                            }
                        }

                        DatabaseReference updateData = null;
                        updateData = FirebaseDatabase.getInstance().getReference("Users")
                                .child(postSnapshot.getKey());

                        updateData.child("block").setValue(user.getBlock());


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
