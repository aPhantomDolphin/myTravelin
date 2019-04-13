package com.example.travelin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ForumCommentActivity extends AppCompatActivity {

    private ImageView postImg;
    private boolean isImageFitToScreen;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private String mainID;
    private ConstraintLayout parent;
    private ConstraintLayout child;
    private View divider;
    private ListView lv;
    private EditText et;
    private ImageView newImg;
    private TextView userName;
    private TextView blogTitle;
    private TextView blogDesc;
    private TextView likeCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_comments);

        mainID=getIntent().getExtras().getString("postid");

        userName = findViewById(R.id.blog_user_name);
        blogTitle = findViewById(R.id.blog_title);
        blogDesc = findViewById(R.id.blog_desc);
        likeCount = findViewById(R.id.blog_like_count);

        postImg = findViewById(R.id.blog_image);
        parent = findViewById(R.id.constraintLayout);
        child = findViewById(R.id.post);
        divider = findViewById(R.id.divider);
        lv = findViewById(R.id.listView);
        et = findViewById(R.id.new_post_comment);
        newImg = findViewById(R.id.blog_image2);



        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference refg=database.getReference();
        DatabaseReference usesRef=refg.child("Posts");
        usesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post= postSnapshot.getValue(Post.class);
                    if (mainID.equals(postSnapshot.getKey())) {
                        break;
                    }
                }

                userName.setText(post.getUsername());

                System.out.println("USERNAME:"+post.getUsername());

                blogTitle.setText(post.getPostTitle());
                blogDesc.setText(post.getBody());
                likeCount.setText(post.getScore());

                StorageReference fuckthis = storage.getReferenceFromUrl(post.getImageURLs());

                final long OM = 5000*500000000;
                fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        postImg.setImageBitmap(bitmap.createScaledBitmap(bitmap, 300, 300, false));
                        newImg.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
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

        postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    isImageFitToScreen = true;
                    //postImg.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                    //postImg.setLayoutParams(new ConstraintLayout.LayoutParams(parent.getWidth(),parent.getHeight()));
                    //postImg.setScaleType(ImageView.ScaleType.FIT_XY);
                    child.setVisibility(View.GONE);
                    newImg.setVisibility(View.VISIBLE);
                    divider.setVisibility(View.GONE);
                    lv.setVisibility(View.GONE);
                    et.setVisibility(View.GONE);



            }
        });

        newImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImageFitToScreen = false;
                //postImg.setAdjustViewBounds(true);
                child.setVisibility(View.VISIBLE);
                newImg.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
                lv.setVisibility(View.VISIBLE);
                et.setVisibility(View.VISIBLE);
            }
        });

    }

}
