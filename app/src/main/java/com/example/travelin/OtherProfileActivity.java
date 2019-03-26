package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OtherProfileActivity extends AppCompatActivity {

    private Button rateUser;
    private Button blockButton;
    private Button reportButton;
    private TextView nameView;
    private String username;
    private Button homeButton;
    private ImageView imageView;
    private Button searchButton;
    private TextView bioView;
    private TextView emailView;
    private TextView ratingView;
    private ImageView dpView;
    private Button viewImages;
    private TextView reviewsProfile;
    private BottomNavigationView mMainNav;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private TextView interest;

    byte[] bArray=new byte[0];
    //User usert;
    private String mail;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_other_profile);
        mail=getIntent().getExtras().getString("mail");
        bioView=findViewById(R.id.other_bio_profile);
        nameView = findViewById(R.id.other_name_profile);
        emailView = findViewById(R.id.other_email);
        reviewsProfile=findViewById(R.id.other_reviews_profile);
        rateUser = findViewById(R.id.rate_and_review_profile);

        interest=findViewById(R.id.other_interest_profile);

        rateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,RateUserActivity.class);
                System.out.println("OPAAAAAAAAAAAAAAA:"+mail);
                intent.putExtra("mail",mail);
                startActivity(intent);
            }
        });

        reportButton = findViewById(R.id.report_button);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                final DatabaseReference ref = db.getReference();
                final DatabaseReference userRef = ref.child("Users");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("executing report code");
                        ArrayList<User> userList = new ArrayList<>();
                        userList.clear();
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            if (user.getEmail().equals(mail)) {
                                user.addReport();
                                System.out.println("reporting user");
                                final DatabaseReference updateData = userRef.child(postSnapshot.getKey());
                                updateData.child("reportCount").setValue(user.getReportCount() + 1);
                                if (user.getReportCount() >= 3) {
                                    System.out.println("banhammer");
                                    updateData.child("username").setValue(null);
                                    updateData.child("avg").setValue(null);
                                    updateData.child("avgRating").setValue(null);
                                    updateData.child("bio").setValue(null);
                                    updateData.child("interestsNew").setValue(null);
                                    updateData.child("name").setValue("DELETED");
                                    updateData.child("pics").setValue(null);
                                    updateData.child("profURL").setValue(null);

                                    //i can't delete other users so omegalul
                                }
                            }
                        }

                        System.out.println("done with report code");
                        //update data in firebase here
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_profile:
                        intent = new Intent(OtherProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(OtherProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_search:
                        intent = new Intent(OtherProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;


                    default:
                        return false;

                }

            }
        });

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        DatabaseReference userRef = ref.child("Users");

        //userRef.addValueEventListener(new ValueEventListener() {
          userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                User u = null;
                userList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = (User) postSnapshot.getValue(User.class);
                    System.out.println("EMAILLLLLLL "+user.getEmail());
                    if (user.getEmail().equals(mail)) {
                        //u = user;
                        System.out.println("THIS NOOB " + user.getName());
                        nameView.setText(user.getName());
                        bioView.setText(user.getBio());
                        emailView.setText(user.getEmail());
                        ratingView.setText(String.valueOf(user.getAvg()));
                        interest.setText(String.valueOf(user.getInterestsNew()));

                        try {
                            String re1 = user.getRev();
                            String[] arrOfStr = re1.split("@");
                            String res = "";
                            for (String a : arrOfStr) {
                                String temp = a;
                                String[] arrT = temp.split(":");
                                try {
                                    String re2 = arrT[1];
                                    res += re2 + ", ";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            reviewsProfile.setText(res);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        blockButton = findViewById(R.id.block_button);
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");

                final FirebaseUser firebaseUser = mAuth.getCurrentUser();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User auth=new User();
                        DatabaseReference updateData=null;
                        DatabaseReference updateData2=null;
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                                auth=(User) postSnapshot.getValue(User.class);
                                updateData= FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());
                            }
                        }
                        //System.out.println("AUTHORUSER:"+auth.getEmail());

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            if (user.getEmail().equals(mail)) {
                                updateData2= FirebaseDatabase.getInstance().getReference("Users")
                                        .child(postSnapshot.getKey());
                                //System.out.println("BLOCKEDUSER:"+user.getEmail());
                                auth.addBlock(user.getEmail());
                                user.addBlock(auth.getEmail());
                                try {
                                    updateData.child("block").setValue(auth.getBlock());
                                    updateData2.child("block").setValue(user.getBlock());
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                String mail=getIntent().getExtras().getString("mail");
                Intent intent =new Intent(OtherProfileActivity.this, ProfileActivity.class);
                intent.putExtra("mail",mail);
                startActivity(intent);



            }
        });


        imageView = findViewById(R.id.other_profilepic);
        bioView = findViewById(R.id.other_bio_profile);
        //emailView = findViewById(R.id.other_name_profile);
        ratingView = findViewById(R.id.other_rating_profile);
        dpView = findViewById(R.id.other_profilepic);

        viewImages = findViewById(R.id.images_other_profile);
        viewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this,OtherProfilePicturesActivity.class);
                intent.putExtra("mail",mail);
                startActivity(intent);

            }   });

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference refg=database.getReference();
        DatabaseReference usesRef=refg.child("Users");
        final ArrayList<User> res= new ArrayList<User>();
        usesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                res.clear();
                User user = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    user= postSnapshot.getValue(User.class);
                    if (user.getEmail().equals(mail)) {
                        break;
                    }
                    //System.out.println(postSnapshot.getKey());
                }

                StorageReference fuckthis = storage.getReferenceFromUrl(user.getProfURL());
                final long OM = 5000*500000000;
                fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}