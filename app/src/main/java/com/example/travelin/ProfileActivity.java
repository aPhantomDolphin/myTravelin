package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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

    //private Realm realm;
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
    private TextView reviews;
    byte[] bArray=new byte[0];
    User usert;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private BottomNavigationView mMainNav;



    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_search:
                        intent = new Intent(ProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });
        mAuth = FirebaseAuth.getInstance();


        nameView = findViewById(R.id.name_profile);

        logoutButton = findViewById(R.id.Logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SyncUser.current().logOut();
                mAuth.signOut();
                Intent intent = new Intent( ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });

        editProfile = findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ProfileActivity.this.finish();
            }
        });


        imageView = findViewById(R.id.profilepic);
        bioView = findViewById(R.id.bio_profile);
        emailView = findViewById(R.id.email_profile1);
        ratingView = findViewById(R.id.rating_profile);
        dpView = findViewById(R.id.profilepic);

        interests=findViewById(R.id.interest_profile1);
        reviews=findViewById(R.id.reviews_profile);

        addImages = findViewById(R.id.add_pictures_profile);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        User u = null;

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ArrayList<User> userlist = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = (User) postSnapshot.getValue(User.class);
                    //userlist.add(user);
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        nameView.setText(user.getName());
                        bioView.setText(user.getBio());
                        emailView.setText(user.getEmail());
                        ratingView.setText(String.valueOf(user.getAvgRating()));

                        StorageReference fuckthis = storage.getReferenceFromUrl(user.getProfURL());
                        final long OM = 5000 * 50000000;
                        fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
                        try {

                            interests.setText(user.getInterestsNew());
                            //reviews.setText(re);
                            String re1=user.getRev();
                            String[] arrOfStr = re1.split("@");
                            String res="";
                            for(String a : arrOfStr){
                                String temp=a;
                                System.out.println("FIRST SPLIT RES::"+temp);
                                String[] arrT = temp.split(":");
                                try {
                                    String re2 = arrT[1];
                                    System.out.println("SECOND SPLIT RES::"+re2);
                                    res+=re2+", ";
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                            reviews.setText(res);
                        }
                        catch(Exception e){
                            e.printStackTrace();
                            interests.setText("NoInterests");
                            System.out.println("REACHED NO INTERESTS");
                            reviews.setText("NOOL");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Realm realm=Realm.getDefaultInstance();
        final Uri targetUri = data.getData();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Users");
        User u = null;

        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    final User user = (User) postSnapshot.getValue(User.class);
                    //userlist.add(user);
                    if(firebaseUser.getUid().equals(postSnapshot.getKey())){
                        //////////////////////////////////////////////////////////////////////

                        try {
                            Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                            //dpView.setImageBitmap(bmp);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                            bArray = stream.toByteArray();
                            //realm.beginTransaction();
                            //usert.addProfileImage(bArray);
                            //realm.commitTransaction();

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
                                        user.addPics(downloadUri.toString());
                                        final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                                .child(mAuth.getCurrentUser().getUid());

                                        updateData.child("pics").setValue(user.getPics());

                                    } else {

                                    }

                                    StorageReference fuckthis = storage.getReferenceFromUrl(downloadUri.toString());
                                    final long OM = 5000*500000000;
                                    fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            //imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
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
        } );


        if (resultCode == RESULT_OK) {
            //Uri targetUri = data.getData();

        }
    }



}
