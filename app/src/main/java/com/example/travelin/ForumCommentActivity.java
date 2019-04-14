package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ForumCommentActivity extends AppCompatActivity {

    private EditText editText;
    private Button sendButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button reportButton;
    private Button commentButton;
    private ListView lview;
    private String commentpost;

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_comments);
        Bundle extras = getIntent().getExtras();
        commentpost = extras.getString("commentid");
        System.out.println("this is post id " + extras.getString("commentid"));
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Posts");

        editText = findViewById(R.id.new_post_comment);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int KeyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (KeyCode == KeyEvent.KEYCODE_ENTER)) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Post post = null;
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                if (commentpost.equals(postSnapshot.getKey())) {
                                    post = postSnapshot.getValue(Post.class);
                                    post.setNumComments(post.getNumComments() + 1);
                                    post.addComment(editText.getText().toString());
                                    System.out.println("added comment");
                                    break;
                                }
                            }

                            DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Posts")
                                    .child(commentpost);

                            updateData.child("comments").setValue(post.getComments());
                            Intent intent = new Intent(ForumCommentActivity.this, ForumMainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }
}
