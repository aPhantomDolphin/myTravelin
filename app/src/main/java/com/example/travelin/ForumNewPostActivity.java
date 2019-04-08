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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.UUID;

public class ForumNewPostActivity extends AppCompatActivity {


    private BottomNavigationView mMainNav;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Button addPic;
    private byte[] bArray = new byte[0];
    private Uri imgUri = null;
    private ImageView dpView;
    private Button submitPost;
    private EditText postBody;
    private EditText postTitle;
    private boolean isImageFitToScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_new_post);
        mAuth = FirebaseAuth.getInstance();
        final String imageUrl;

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_profile:
                        intent = new Intent(ForumNewPostActivity.this, ProfileActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;


                    case R.id.navigation_search:
                        intent = new Intent(ForumNewPostActivity.this, SearchFilterActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        return true;

                    case R.id.navigation_forum:
                        ForumNewPostActivity.this.finish();
                        return true;

                    default:
                        return false;

                }

            }
        });

        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference ref = firebaseDatabase.getReference();
                final DatabaseReference postRef = ref.child("Posts");
                DatabaseReference userRef = ref.child("Users");

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            if(mAuth.getCurrentUser().getUid().equals(snap.getKey())) {
                                final User user = snap.getValue(User.class);
                                final Post post = new Post(user, postBody.toString(), UUID.randomUUID().toString(), Calendar.getInstance().getTime());


                                try {

                                    String path = "postImages/" + UUID.randomUUID() + ".png";
                                    final StorageReference storageReference = storage.getReference(path);
                                    UploadTask uploadTask = storageReference.putBytes(bArray);



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
                                                post.setPostImageUrl(downloadUri.toString());
                                                postRef.child(post.getPostId()).setValue(post);
                                            }

                                            StorageReference picReference = storage.getReferenceFromUrl(downloadUri.toString());
                                            final long OM = 5000 * 500000000;
                                            picReference.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                                @Override
                                                public void onSuccess(byte[] bytes) {
                                                    dpView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                        }
                                    });
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

            }
        });

        dpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    dpView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    dpView.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    dpView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    dpView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });






        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            imgUri = targetUri;
            try {
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                dpView.setImageBitmap(bmp);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                bArray = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
