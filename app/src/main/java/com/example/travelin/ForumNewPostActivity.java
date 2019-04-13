package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ForumNewPostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private BottomNavigationView mMainNav;
    private Button createPost;
    private TextView postDesc;
    private ImageView postImg;
    private TextView postTitle;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ConstraintLayout parentLayout;
    private boolean isImageFitToScreen;
    private DatabaseReference newPostRef;
    private byte[] bArray;
    private FirebaseStorage storage;
    private String mainUri;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_new_post);

        storage = FirebaseStorage.getInstance();

        parentLayout = findViewById(R.id.constraintLayout);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    hideKeyboard(v);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

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

        postDesc = findViewById(R.id.new_post_desc);
        postImg = findViewById(R.id.new_post_image);
        postTitle = findViewById(R.id.new_post_title);

        postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        createPost = findViewById(R.id.post_btn);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("Posts");
                if(newPostRef == null) {
                    newPostRef = databaseReference.push();
                }

                Date currentDate = Calendar.getInstance().getTime();
                Post creatingPost = new Post(mAuth.getCurrentUser().getEmail(), currentDate, postDesc.getText().toString(), newPostRef.getKey() ,postTitle.getText().toString());
                creatingPost.setUsername(getAuthorName());
                creatingPost.setImageURLs(mainUri);

                newPostRef.setValue(creatingPost);

                Intent intent = new Intent(ForumNewPostActivity.this, ForumMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });







    }

    private String getAuthorName() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Users");
        final StringBuffer authorName = new StringBuffer("");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);

                    if (mAuth.getCurrentUser().getUid().equals(user.getEmail())) {
                        authorName.append(user.getUsername());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return authorName.toString();

    }

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Realm realm=Realm.getDefaultInstance();
        final Uri targetUri = data.getData();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Posts");
        newPostRef = myRef.push();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Post post = postSnapshot.getValue(Post.class);

                    String postid = newPostRef.getKey();

                    //if (postid.equals(post.getPostID())) {

                        //final Post postneeded = post;

                        try {
                            Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                            bArray = stream.toByteArray();

                            String path = "postphotos/" + UUID.randomUUID() + ".png";
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
                                        mainUri = downloadUri.toString();
                                        //postneeded.setImageURLs(downloadUri.toString());
                                        //final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Posts")
                                                //.child(postneeded.getPostID());

                                        //updateData.child("imageURLS").setValue(postneeded.getImageURLs());

                                    } else {

                                    }

                                    StorageReference fuckthis = storage.getReferenceFromUrl(downloadUri.toString());
                                    final long OM = 5000 * 500000000;
                                    fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            postImg.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
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
                    }
                    //////////////////////////////////////////////////////////////////////
                //}

            //}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (resultCode == RESULT_OK) {
            //Uri targetUri = data.getData();

        }

    }

}
