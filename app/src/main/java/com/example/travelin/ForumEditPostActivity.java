package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class ForumEditPostActivity extends AppCompatActivity {

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private String mainID;

    private TextView saveButton;
    private TextView deleteButton;
    private EditText editTitle;
    private EditText editBody;
    private TextView userName;
    private TextView dateView;
    private ImageView imgView;
    private Post thisPost;
    private DatabaseReference updateData1;

    @Override
    public void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.forum1_edit_post);

        mainID=getIntent().getExtras().getString("postid");

        final DatabaseReference postRef = databaseReference.child("Posts");
        final DatabaseReference commentRef = postRef.child(mainID).child("Comments");

        saveButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        editTitle = findViewById(R.id.edit_blog_title);
        editBody = findViewById(R.id.edit_blog_desc);
        userName = findViewById(R.id.blog_user_name);
        dateView = findViewById(R.id.blog_date);
        imgView = findViewById(R.id.blog_image);

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Displaying Post info
                Post post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post= postSnapshot.getValue(Post.class);
                    if (mainID.equals(postSnapshot.getKey())) {
                        thisPost = post;
                        break;
                    }
                }

                userName.setText(thisPost.getUsername());
                editTitle.setText(thisPost.getPostTitle());
                editBody.setText(thisPost.getBody());

                if(post.getImageURLs() != null ) {
                    StorageReference commentStorage = storage.getReferenceFromUrl(post.getImageURLs());

                    final long OM = 5000 * 500000000;
                    commentStorage.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imgView.setImageBitmap(bitmap.createScaledBitmap(bitmap, 300, 300, false));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumEditPostActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete all post info and replies?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                            if (mainID.equals(postSnapshot.getKey())) {
                                                postRef.child(mainID).removeValue();
                                            }

                                            System.out.println("Post successfully deleted");

                                            //ForumCommentActivity.this.finish();
                                            Intent intent = new Intent(ForumEditPostActivity.this, ForumMainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            try{
                                                startActivity(intent);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError){

                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Post post=postSnapshot.getValue(Post.class);
                            if(post.getPostID().equals(mainID)){
                                updateData1=postRef.child(postSnapshot.getKey());
                                if(!editTitle.getText().toString().isEmpty()){
                                    updateData1.child("postTitle").setValue(editTitle.getText().toString());
                                }
                                if(!editBody.getText().toString().isEmpty()){
                                    updateData1.child("body").setValue(editBody.getText().toString());
                                }
                                Intent intent = new Intent(ForumEditPostActivity.this, ForumCommentActivity.class);
                                intent.putExtra("postid",mainID);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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


    }
}
