package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

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
    private TextView photosText;
    private ImageView dpView;
    private Button viewImages;
    private TextView reviewsText;
    private BottomNavigationView mMainNav;
    private TextView photosLink;
    private TextView reviewsLink;
    private Button messageButton;
    private FirebaseAuth mAuth;
    private  FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference ref = db.getReference();
    private DatabaseReference userRef = ref.child("Users");
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    private TextView interest;

    private Button tempRating;

    byte[] bArray=new byte[0];
    //User usert;
    private String mail;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_other_profile2);

        doAlways();

     /*
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

*/
    }

    public void doAlways(){

        mMainNav = findViewById(R.id.bottom_navigation);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_profile:
                        intent = new Intent(OtherProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_home:
                        System.out.println("AT HOME");
                        intent = new Intent(OtherProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(OtherProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(OtherProfileActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        mail=getIntent().getExtras().getString("mail");
        final String laterMail = mail;
        bioView=findViewById(R.id.bio);
        nameView = findViewById(R.id.profile_name);
        interest=findViewById(R.id.interestsView);
        ratingView = findViewById(R.id.ratingText);
        reviewsLink = findViewById(R.id.reviewsLink);
        reviewsText = findViewById(R.id.reviewsText);
        photosText = findViewById(R.id.photosText);
        photosLink = findViewById(R.id.photos);

        messageButton = findViewById(R.id.sendmessagebutton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference().child("Users");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = null;
                        User user2 = null;
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            user = snap.getValue(User.class);
                            if (mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                                String roomNeeded = "observable-" + user.getEmail() + "-" + laterMail;
                                for (DataSnapshot inSnap : dataSnapshot.getChildren()) {
                                    user2 = inSnap.getValue(User.class);
                                    if (user2.getEmail().equals(laterMail)) {
                                        user2.addInvite(roomNeeded);

                                        DatabaseReference updateData = null;
                                        updateData = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(inSnap.getKey());

                                        updateData.child("roomInvites").setValue(user2.getRoomInvites());
                                    }
                                }
                                user.addRoom(roomNeeded);
                                DatabaseReference updateData = null;
                                updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(snap.getKey());

                                updateData.child("rooms").setValue(user.getRooms());
                                Intent intent = new Intent(OtherProfileActivity.this, MessageActivity.class);
                                //intent.putExtra("name", u.getName());
                                intent.putExtra("room",roomNeeded);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        photosLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this, OtherProfilePicturesActivity.class);
                intent.putExtra("mail",mail);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        reviewsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherProfileActivity.this, ReviewActivity.class);
                intent.putExtra("email",mail);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

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

                        if (!user.getName().equals("DELETED")) {
                            System.out.println("THIS NOOB " + user.getName());
                            nameView.setText(user.getName());
                            bioView.setText(user.getBio());

                            ratingView.setText(String.valueOf(user.getAvg()));


                            interest.setText(String.valueOf(user.getInterestsNew()));

                            try {
                                String re1 = user.getRev();
                                String[] arrOfStr = re1.split("\\|");
                                final ArrayList<String> reviews1=new ArrayList<>();

                                String res = "";
                                for (String a : arrOfStr) {
                                    String temp = a;
                                    String[] arrT = temp.split(":");
                                    try {
                                        String re2 = arrT[1];
                                        reviews1.add(re2);
                                        res += re2 + ", ";
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                if(!user.getRev().equals("")){
                                    reviewsText.setText(String.valueOf(reviews1.size()));
                                }
                                else{
                                    reviewsText.setText("0");
                                }
                            } catch (Exception e) {
                                reviewsText.setText("0");
                                e.printStackTrace();
                            }
                            try{
                                String getPics = user.getPics();
                                String[] arrOfStr = getPics.split("\\|");
                                if(!user.getPics().equals("")){
                                    photosText.setText(String.valueOf(arrOfStr.length));
                                }
                                else{
                                    photosText.setText("0");
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                photosText.setText("0");
                            }

                        } else {
                            Intent intent;
                            intent = new Intent(OtherProfileActivity.this, SearchPageActivity.class);
                            intent.putExtra("gender", "Both");
                            //System.out.println("YOURSET1"+rating);
                            intent.putExtra("rating", "0");
                            intent.putExtra("searchUN", "");
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        tempRating = findViewById(R.id.ratingbutton);

        tempRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Dialog(OtherProfileActivity.this, new Dialog.InputSenderDialogListener() {
                    @Override
                    public void onOK(final String rating) {
                        Log.d("rating", "The user tapped OK, rating is "+rating);
                        final String mail=getIntent().getExtras().getString("mail");

                        mAuth = FirebaseAuth.getInstance();
                        //System.out.println("HELLOW FROM RATE:"+mail);

                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference ref = db.getReference();
                        DatabaseReference userRef = ref.child("Users");

                        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User auth=new User();
                                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                                        auth=(User) postSnapshot.getValue(User.class);
                                    }
                                }

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    User user = (User) postSnapshot.getValue(User.class);
                                    if (user.getEmail().equals(mail)) {
                                        //nameView.setText(user.getName());
                                        if(!String.valueOf(user.getAvg()).isEmpty() && String.valueOf(user.getAvg()).compareTo("")==0){
                                            ratingView.setText(String.valueOf(user.getAvg()));
                                        }

                                        DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(postSnapshot.getKey());

                                        if(!rating.toString().isEmpty()){
                                            double x=Double.parseDouble(rating);
                                            String auth1 = auth.getName();
                                            try {
                                                String mast = user.getRat();
                                                String[] arrOfStr = mast.split("\\|");
                                                String res = "";
                                                int flag = 0;

                                                double val = user.getAvg();
                                                val = val * user.getNumR();

                                                for (String a : arrOfStr) {
                                                    String temp = a;
                                                    String[] arrT = temp.split(":");
                                                    try {
                                                        String re2 = arrT[0];
                                                        if (re2.equals(auth1)) {
                                                            double minus = Double.parseDouble(arrT[1]);
                                                            temp = auth1 + ":" + x;
                                                            val -= minus;
                                                            flag = 1;
                                                        }
                                                        if(!temp.equals("")){
                                                            res += "|" + temp;
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                if (flag == 1) {
                                                    val += x;
                                                    val = val / user.getNumR();
                                                    user.setAvgAgain(val);
                                                    updateData.child("avg").setValue(val);
                                                    updateData.child("rat").setValue(res);
                                                } else {
                                                    user.setAvg(x);
                                                    user.addRat(auth1, x);
                                                    updateData.child("avg").setValue(user.getAvg());
                                                    updateData.child("numR").setValue(user.getNumR());
                                                    updateData.child("rat").setValue(user.getRat());
                                                }
                                            }
                                            catch(Exception e){
                                                user.setAvg(x);
                                                user.addRat(auth1, x);
                                                updateData.child("avg").setValue(user.getAvg());
                                                updateData.child("numR").setValue(user.getNumR());
                                                e.printStackTrace();
                                            }
                                            ratingView.setText(String.valueOf(user.getAvg()));

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancel(String rating) {
                        Log.d("cancel", "The user tapped Cancel, rating is "+rating);
                    }
                }).create().show();

                doAlways();
            }
        });




        reportButton = findViewById(R.id.reportbutton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                updateData.child("reportCount").setValue(user.getReportCount());
                                if (user.getReportCount() >= 3) {
                                    System.out.println("banhammer");
                                    updateData.child("username").setValue("DELETED");
                                    updateData.child("avg").setValue(null);
                                    updateData.child("avgRating").setValue(null);
                                    updateData.child("bio").setValue(null);
                                    updateData.child("interestsNew").setValue(null);
                                    updateData.child("name").setValue("deleted");
                                    updateData.child("pics").setValue(null);
                                    updateData.child("profURL").setValue(null);
                                    Intent intent;
                                    intent = new Intent(OtherProfileActivity.this, SearchPageActivity.class);
                                    intent.putExtra("gender", "Both");
                                    //System.out.println("YOURSET1"+rating);
                                    intent.putExtra("rating", "0");
                                    intent.putExtra("searchUN", "");
                                    startActivity(intent);
                                    finish();
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

        blockButton = findViewById(R.id.blockbutton);
        blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }
}