package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ForumCommentActivity extends AppCompatActivity {

    private ImageView postImg;
    private boolean isImageFitToScreen;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private DatabaseReference commentRef;
    private DatabaseReference postRef = databaseReference.child("Posts");
    private User auth=new User();
    private Post post1 = new Post();
    private DatabaseReference updateData1;
    private Comment com;
    private String newComment="";
    private String mainID;
    private ConstraintLayout parent;
    private ConstraintLayout child;
    private View divider;
    private ListView listView;
    private ImageView newImg;
    private TextView userName;
    private TextView blogTitle;
    private TextView blogDesc;
    private TextView likeCount;
    private TextView deleteButton;
    private TextView reportButton;
    private BottomNavigationView mMainNav;
    private Post thisPost;
    private EditText commentText;
    private Button sendComment;
    //private SimpleAdapter simpleAdapter;
    private TextView editButton;
    private TextView commentEdit;
    private boolean alreadyReported = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum1_comments);
        updateListView();

    }

    public void updateListView(){


        mainID=getIntent().getExtras().getString("postid");
        commentRef = postRef.child(mainID).child("Comments");

        userName = findViewById(R.id.blog_user_name);
        blogTitle = findViewById(R.id.blog_title);
        blogDesc = findViewById(R.id.blog_desc);
        likeCount = findViewById(R.id.blog_like_count);

        postImg = findViewById(R.id.blog_image);
        parent = findViewById(R.id.constraintLayout);
        child = findViewById(R.id.post);
        divider = findViewById(R.id.divider);
        newImg = findViewById(R.id.blog_image2);
        reportButton = findViewById(R.id.reportButton);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        mMainNav = findViewById(R.id.main_nav);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:

                        intent = new Intent(ForumCommentActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(ForumCommentActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(ForumCommentActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;
                    case R.id.navigation_forum:
                        intent = new Intent(ForumCommentActivity.this, ForumMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    default:
                        return false;

                }

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumCommentActivity.this, ForumEditPostActivity.class);
                intent.putExtra("postid",mainID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        commentText = findViewById(R.id.new_post_comment);
        sendComment = findViewById(R.id.send_comment);


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

                if(post.getAuthorEmail().equals(firebaseUser.getEmail())){
                    // Current user is the author of the post, so they can delete the post
                    deleteButton.setVisibility(View.VISIBLE);
                    reportButton.setVisibility(View.GONE);
                    sendComment.setVisibility(View.GONE);
                    commentText.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                }
                else{
                    // Current user can report the post

                    postRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                if(postSnapshot.getKey().equals(mainID)){
                                    Post newPost = postSnapshot.getValue(Post.class);
                                    String reported = newPost.getPeopleReported();
                                    System.out.println("REPORTED: "+reported);
                                    String[] arrayReported = reported.split("\\|");
                                    for( String a : arrayReported){
                                        if(a.equals(firebaseUser.getEmail())){
                                            System.out.println("HERE a:   "+a);
                                            reportButton.setVisibility(View.GONE);
                                            return;
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    deleteButton.setVisibility(View.GONE);
                    sendComment.setVisibility(View.VISIBLE);
                    commentText.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.GONE);
                }

                userName.setText(post.getUsername());

                System.out.println("USERNAME:"+post.getUsername());

                blogTitle.setText(post.getPostTitle());
                blogDesc.setText(post.getBody());
                likeCount.setText(post.getScore());

                if(post.getImageURLs() != null ) {
                    StorageReference commentStorage = storage.getReferenceFromUrl(post.getImageURLs());


                    final long OM = 5000 * 500000000;
                    commentStorage.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
                    });
                }


                ////////////////////////////////////////////////////////////////////////////////////
                // Now Displaying comments
                listView = findViewById(R.id.listView);
                final ArrayList<Comment> commentArrayList = new ArrayList<>();
                final ArrayList<HashMap<String, String>> stringList = new ArrayList<>();

                commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // Getting all comments of the post from database
                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            final Comment commentTemp = postSnapshot.getValue(Comment.class);
                            commentArrayList.add(commentTemp);
                        }

                        // Setting up list of hashmaps to connect each comment to UI
                        for (int i = 0; i < commentArrayList.size(); i++) {
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("comment_user_name", commentArrayList.get(i).getUsername());

                            DateFormat df = android.text.format.DateFormat.getDateFormat(ForumCommentActivity.this);
                            String date = df.format(commentArrayList.get(i).getTimeCreated());
                            hm.put("comment_date", date);

                            hm.put("comment_dec", commentArrayList.get(i).getComment());

                            stringList.add(hm);
                        }

                        // Arrays to set up adapter
                        String[] from = {"comment_user_name","comment_date", "comment_dec"};
                        int[] to = {R.id.comment_user_name, R.id.comment_date, R.id.comment_desc};

                        SimpleAdapter simpleAdapter = new SimpleAdapter(ForumCommentActivity.this, stringList, R.layout.forum1_comment_item, from, to);

                        listView.setAdapter(simpleAdapter);


                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                                view.findViewById(R.id.comment_user_name).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String mail = commentArrayList.get(position).getAuthorEmail();
                                        Intent intent = new Intent(ForumCommentActivity.this, OtherProfileActivity.class);

                                        intent.putExtra("mail",mail);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                                view.findViewById(R.id.comment_report).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String commentID = commentArrayList.get(position).getCommentId();
                                        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                                    //commentRef.child(commentID).report();




                                                    updateListView();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });
                                view.findViewById(R.id.comment_edit).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    com=postSnapshot.getValue(Comment.class);
                                                    if(com.getAuthorEmail().equals(firebaseUser.getEmail()) && com.getCommentId().equals(commentArrayList.get(position).getCommentId())){
                                                        updateData1=commentRef.child(postSnapshot.getKey());
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(ForumCommentActivity.this);
                                                        builder.setTitle("Edit Comment");

                                                        final EditText input = new EditText(ForumCommentActivity.this);
                                                        input.setInputType(InputType.TYPE_CLASS_TEXT);
                                                        input.setText(com.getComment());
                                                        builder.setView(input);

                                                        builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                System.out.println(input.getText().toString());
                                                                newComment = input.getText().toString();
                                                                if(!newComment.isEmpty()) {
                                                                    updateData1.child("comment").setValue(newComment);
                                                                }
                                                                updateListView();
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

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                });

                            }
                        });

                        sendComment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                final String getCommentText = commentText.getText().toString();
                                final Date currentDate = Calendar.getInstance().getTime();

                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference userRef = firebaseDatabase.getReference().child("Users");

                                final DatabaseReference newPostRef = commentRef.push();

                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                            User user = postSnapshot.getValue(User.class);

                                            if (firebaseUser.getEmail().equals(user.getEmail())) {

                                                Comment commentObject = new Comment(user.getEmail(), newPostRef.getKey(), currentDate, getCommentText, mainID, user.getUsername());
                                                newPostRef.setValue(commentObject);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                                commentText.getText().clear();
                                hideKeyboard(commentText);
                                updateListView();

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=thisPost.getAuthorEmail();
                Intent intent = new Intent(ForumCommentActivity.this, OtherProfileActivity.class);

                intent.putExtra("mail",mail);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumCommentActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Report");
                builder.setMessage("Are you sure you want to report this post?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                            if (mainID.equals(postSnapshot.getKey())) {
                                                thisPost = postSnapshot.getValue(Post.class);
                                                DatabaseReference newRef = postRef.child(mainID);
                                                thisPost.addReport();
                                                thisPost.addReported(firebaseUser.getEmail());
                                                newRef.child("numReports").setValue(thisPost.getNumReports());
                                                newRef.child("peopleReported").setValue(thisPost.getPeopleReported());

                                                if(thisPost.getNumReports() >= 3){
                                                    newRef.removeValue();
                                                }

                                                reportButton.setVisibility(View.GONE);
                                                System.out.println("Post successfully reported");
                                                return;
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ForumCommentActivity.this);
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
                                            Intent intent = new Intent(ForumCommentActivity.this, ForumMainActivity.class);
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


        postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isImageFitToScreen = true;
                child.setVisibility(View.GONE);
                newImg.setVisibility(View.VISIBLE);
                divider.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                commentText.setVisibility(View.GONE);
                sendComment.setVisibility(View.GONE);


            }
        });

        newImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isImageFitToScreen = false;
                child.setVisibility(View.VISIBLE);
                newImg.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
                listView.setVisibility(View.VISIBLE);
                commentText.setVisibility(View.VISIBLE);
                sendComment.setVisibility(View.GONE);
            }
        });

    }


    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ForumCommentActivity.this, ForumMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
