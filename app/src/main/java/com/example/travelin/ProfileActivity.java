package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference().child("Users");

    private Button unblockButton;
    private Button logoutButton;
    private TextView nameView;
    private String username;
    private Button editProfile;
    private Button homeButton;
    private ImageView imageView;
    private Button searchButton;
    private TextView bioView;
    private TextView emailView;
    private TextView ratingView;
    private ImageView dpView;
    private Button addImages;
    private TextView interests;
    private Button reviewsLink;
    private TextView reviewsView;
    private TextView photosView;
    private Button photosLink;
    byte[] bArray=new byte[0];
    User usert;
    //private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-learn-f2515.appspot.com");
    private BottomNavigationView mMainNav;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);
        doAlways();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data!=null) {
            final Uri targetUri = data.getData();


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference().child("Users");
            //     User u = null;

            final FirebaseUser firebaseUser = mAuth.getCurrentUser();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        //User user = postSnapshot.getValue(User.class);
                        //userlist.add(user);
                        if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                            //////////////////////////////////////////////////////////////////////

                            try {
                                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));

                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                                bArray = stream.toByteArray();


                                String path = "herearemyphotos/" + UUID.randomUUID() + ".png";
                                final StorageReference storageReference = storage.getReference(path);
                                StorageMetadata meta = new StorageMetadata.Builder()
                                        .setCustomMetadata("yeet", "damn").build();
                                UploadTask uploadTask = storageReference.putBytes(bArray, meta);

                                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                    @Override
                                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                        if (!task.isSuccessful()) {
                                            throw task.getException();
                                        }

                                        return storageReference.getDownloadUrl();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        Uri downloadUri = null;
                                        if (task.isSuccessful()) {
                                            downloadUri = task.getResult();
                                            postSnapshot.getValue(User.class).addPics(downloadUri.toString());
                                            User u = postSnapshot.getValue(User.class);
                                            u.addPics(downloadUri.toString());
                                            DatabaseReference updateDataNew = FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(mAuth.getCurrentUser().getUid());

                                            updateDataNew.child("pics").setValue(u.getPics());
                                            doAlways();

                                        } else {

                                        }

                                    }
                                });


                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            //////////////////////////////////////////////////////////////////////
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        if (resultCode == RESULT_OK) {

        }

    }

    public void doAlways(){
        mMainNav = findViewById(R.id.bottom_navigation);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(ProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_forum:
                        intent = new Intent(ProfileActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        unblockButton = findViewById(R.id.unblock_button);
        unblockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UnblockActivity.class);
                //intent.putExtra("name", u.getName());
                //intent.putExtra("room",roomNeeded);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        nameView = findViewById(R.id.profile_name);

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent( ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });

        editProfile = findViewById(R.id.editprofilebutton);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        bioView = findViewById(R.id.bio);
        ratingView = findViewById(R.id.ratingText);
        dpView = findViewById(R.id.profile_image);
        interests=findViewById(R.id.interestsView);
        reviewsView=findViewById(R.id.reviewsText);
        reviewsLink=findViewById(R.id.reviewsLink);
        photosLink=findViewById(R.id.photos);
        photosView=findViewById(R.id.photosText);

        photosLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileViewImages.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        reviewsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ReviewActivity.class);
                intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        addImages = findViewById(R.id.addpicturesbutton);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);

                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        nameView.setText(user.getName());
                        bioView.setText(user.getBio());
                        ratingView.setText(String.valueOf(user.getAvg()));

                        if(user.getProfURL() != null && !user.getProfURL().equals("")) {
                            StorageReference storageRef = storage.getReferenceFromUrl(user.getProfURL());

                            final long OM = 5000 * 50000000;
                            storageRef.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    dpView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("");
                                }
                            });
                        }
                        try {
                            interests.setText(user.getInterestsNew());
                        } catch(Exception e){
                            e.printStackTrace();
                            interests.setText("Please add Interests");
                            System.out.println("REACHED NO INTERESTS");
                            //reviews.setText("NOOL");
                        }
                        //reviews.setText(re);
                        try {
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
                            if(!user.getRev().equals("")){
                                reviewsView.setText(String.valueOf(reviews1.size()));
                            }
                            else{
                                reviewsView.setText("0");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            reviewsView.setText("0");
                        }
                        try{
                            String getPics = user.getPics();
                            String[] arrOfStr = getPics.split("\\|");
                            if(!user.getPics().equals("")){
                                photosView.setText(String.valueOf(arrOfStr.length));
                            }
                            else{
                                photosView.setText("0");
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            photosView.setText("0");
                        }

                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isTaskRoot()){
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else{
            ProfileActivity.this.finish();
        }
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
        doAlways();
    }

}
