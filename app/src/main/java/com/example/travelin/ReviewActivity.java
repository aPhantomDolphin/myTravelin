package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class ReviewActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button editReview;
    private String gOldC="";
    private String gNew="";
    private String getEmail;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        mAuth = FirebaseAuth.getInstance();
        getEmail = getIntent().getExtras().getString("email");

        doAlways();
    }

    public void doAlways(){

        mMainNav = findViewById(R.id.bottom_navigation);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_profile:
                        intent = new Intent(ReviewActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_home:
                        intent = new Intent(ReviewActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(ReviewActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(ReviewActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference().child("Users");

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        final List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        final ArrayList<String> reviews1=new ArrayList<>();

        editReview = findViewById(R.id.edit_my_review);
        if(!mAuth.getCurrentUser().getEmail().equals(getEmail)){
            editReview.setVisibility(View.VISIBLE);
        }

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(getEmail.equals(postSnapshot.getValue(User.class).getEmail())) {
                        System.out.println("REACHED EMAILL "+getEmail);

                        final User user = postSnapshot.getValue(User.class);

                        String re1 = user.getRev();

                        String[] arrOfStr = re1.split("\\|");

                        final ArrayList<String> reviews1=new ArrayList<>();

                        for(String a : arrOfStr){
                            String temp=a;
                            String[] arrT=temp.split(":");
                            try {
                                String re2 = arrT[1];
                                reviews1.add(re2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        for (int i = 0; i < reviews1.size(); i++) {
                            HashMap<String, String> hm = new HashMap<>();

                            hm.put("review", reviews1.get(i));
                            aList.add(hm);

                        }

                        /*TextView tv = findViewById(R.id.review_header);
                        tv.setText("Reviews of " + user.getEmail());*/


                        String[] from = {"review"};
                        int[] to = {R.id.listview_item_title};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(/*getBaseContext()*/ReviewActivity.this, aList, R.layout.activity_listview_review, from, to);
                        ListView androidListView = (ListView) findViewById(R.id.list_view_review);
                        androidListView.setAdapter(simpleAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        editReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                builder.setTitle("Edit Review");

                final EditText input = new EditText(ReviewActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);


                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (getEmail.equals(postSnapshot.getValue(User.class).getEmail())) {
                                final DatabaseReference updateData = userRef.child(postSnapshot.getKey());
                                final User user = postSnapshot.getValue(User.class);

                                String auth1 = user.getEmail();

                                String res="";
                                int flag=0;


                                final String[] arrOfStr = user.getRev().split("\\|");

                                for (String a : arrOfStr) {

                                    String[] arrT = a.split(":");
                                    try {

                                        String re2 = arrT[1];
                                        final String temp=a;
                                        String me=arrT[0];

                                        System.out.println("THIS IS ME: "+me);

                                        if (me.equals(firebaseUser.getEmail())) {

                                            gOldC=re2;
                                            flag=1;


                                            AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                                            builder.setTitle("Edit Review");

                                            final EditText input = new EditText(ReviewActivity.this);

                                            input.setInputType(InputType.TYPE_CLASS_TEXT);

                                            input.setText(gOldC);

                                            builder.setView(input);

                                            builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    System.out.println(input.getText().toString());

                                                    //newComment = input.getText().toString();
                                                    final String newComment = input.getText().toString();

                                                    if(!newComment.isEmpty()) {
                                                        //temp = myEmail + ":" + newComment;
                                                        gNew = firebaseUser.getEmail() + ":" + newComment;
                                                        updateData.child("rev").setValue(gNew);
                                                        doAlways();
                                                    }



                                                    //updateListView();
                                                }
                                            });
                                            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    System.out.println("CANCEL");
                                                    dialog.cancel();
                                                }
                                            });

                                            builder.show();
                                        }

                                        /*if(!temp.equals("")){
                                            res += "@" + temp;
                                        }*/
                                        //reviews1.add(re2);
                                    } catch (Exception e) {
                                             e.printStackTrace();
                                    }

                                }
                                /*if (flag == 1) {
                                    updateData.child("rat").setValue(res);
                                } else {
                                    updateData.child("rat").setValue(user.getRev());
                                }*/
                                if(flag == 0){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
                                    builder.setTitle("Add Review");

                                    final EditText input = new EditText(ReviewActivity.this);

                                    input.setInputType(InputType.TYPE_CLASS_TEXT);

                                    input.setText("");

                                    builder.setView(input);

                                    builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println(input.getText().toString());

                                            //newComment = input.getText().toString();
                                            final String newComment = input.getText().toString();

                                            if(!newComment.isEmpty()) {
                                                //temp = myEmail + ":" + newComment;
                                                gNew = firebaseUser.getEmail() + ":" + newComment;
                                                updateData.child("rev").setValue(gNew);
                                                doAlways();
                                            }



                                            //updateListView();
                                        }
                                    });
                                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.out.println("CANCEL");
                                            dialog.cancel();
                                        }
                                    });

                                    builder.show();
                                }

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

