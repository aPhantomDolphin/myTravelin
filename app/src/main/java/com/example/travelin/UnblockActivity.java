package com.example.travelin;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unblock);

        mAuth = FirebaseAuth.getInstance();

        mMainNav = findViewById(R.id.bottom_navigation);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(UnblockActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_profile:
                        intent = new Intent(UnblockActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(UnblockActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(UnblockActivity.this, ForumMainActivity.class);
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

                        final String[] blockedUsers = user.getBlock().split(",");
                        for (String str : blockedUsers) {
                            res.add(str);
                        }

                        // TODO : POPULATE LISTVIEW
                        // TODO : CHECK WHICH NAME THEY CLICK ON

                        for (String str : res) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("unblock_name", str);
                            aList.add(hm);
                        }

                        String[] from = {"unblock_name"};
                        int[] to = {R.id.listview_item_title};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/UnblockActivity.this, aList, R.layout.activity_listview, from, to);
                        ListView androidListView = (ListView) findViewById(R.id.list_view_unblock);
                        androidListView.setAdapter(simpleAdapter);
                        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String needsUnblock=res.get(position);
                                //System.out.println("SEARCH USERS: "+mail);
                                user.clearBlock();
                                String continueBlock = "";
                                for (int i = 0; i < blockedUsers.length; i++) {
                                    if (!blockedUsers[i].equals(needsUnblock)) {
                                        user.addBlock(blockedUsers[i]);
                                    }
                                }

                                DatabaseReference updateData = null;
                                updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(firstKey);

                                updateData.child("block").setValue(user.getBlock());

                                String myEmail = user.getEmail();

                                for (DataSnapshot postSnap2 : dataSnapshot.getChildren()) {
                                    User user2 = postSnap2.getValue(User.class);
                                    if (user2.getEmail().equals(needsUnblock)) {
                                        String[] otherBlockedUsers = user2.getBlock().split(",");
                                        user2.clearBlock();
                                        for (int k = 0; k < otherBlockedUsers.length; k++) {
                                            if (!otherBlockedUsers[k].equals(myEmail)) {
                                                user2.addBlock(otherBlockedUsers[k]);
                                            }
                                        }

                                        DatabaseReference updateData2 = null;
                                        updateData2 = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(postSnap2.getKey());

                                        updateData2.child("block").setValue(user2.getBlock());

                                        break;

                                    }
                                }

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
