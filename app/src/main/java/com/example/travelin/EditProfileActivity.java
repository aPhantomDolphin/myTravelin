package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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

public class EditProfileActivity extends AppCompatActivity {

    private Button resetPass;
    private TextInputEditText bioEdit;
    private TextInputEditText nameEdit;
    private String genderEdit="Male";
    private Button saveProfile;
    private Button homeButton;
    private Button changePassword;
    private ImageView dpView;
    private Button setPic;
    byte[] bArray=new byte[0];
    Button deleteAccount;
    private TextInputEditText interests;
    private Uri sickUri = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private FirebaseAuth mauth;
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //final Realm realm = Realm.getDefaultInstance();
        mauth = FirebaseAuth.getInstance();



        bioEdit = findViewById(R.id.bio_edit_profile);
        /*realm.beginTransaction();
        RealmQuery<User>*/
        saveProfile = findViewById(R.id.save_profile);
        nameEdit = findViewById(R.id.email_edit_profile);





        resetPass = findViewById(R.id.change_password);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        deleteAccount= findViewById(R.id.delete_button);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser firebaseUser = mauth.getCurrentUser();
                final String uid = firebaseUser.getUid();
                System.out.println("THIS IS UID: "+uid);
                AuthCredential credential = EmailAuthProvider.getCredential("student@purdue.edu","pass1234");
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            System.out.println("IN SNAPSHOT");
                            User user = postSnapshot.getValue(User.class);
                            if (uid.equals(postSnapshot.getKey())) {
                                System.out.println("FOUND IT");
                                ref.child(uid).removeValue();
                            }

                            System.out.println("User successfully deleted");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError){

                    }
                });

                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    System.out.println("ACCOUNT DELETED");

                                }
                            }
                        });
                    }
                });

                Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        changePassword = findViewById(R.id.change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        dpView = findViewById(R.id.profilepic);
        setPic = findViewById(R.id.profilepic_edit_profile);
        setPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        mMainNav = findViewById(R.id.main_nav);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("CLICKED MENU AT PROFILE");
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        System.out.println("AT HOME");
                        intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.nav_profile:
                        intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try{
                            System.out.println("CLICKED PROFILE");
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.nav_search:
                        intent = new Intent(EditProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Realm realm=Realm.getDefaultInstance();
                interests=findViewById(R.id.intersts_edit_profile);

                final String t1=interests.getText().toString();
                System.out.println("TAG NAME FOUND TO BE:"+t1);
                //final Tag tag=new Tag();
                //tag.setTagName(t1);

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                final DatabaseReference ref = db.getReference();
                DatabaseReference userRef = ref.child("Users");
                //userRef.addValueEventListener(new ValueEventListener() {
                  userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //DatabaseReference userRef = ref.child("Users");
                        ArrayList<User> userList = new ArrayList<>();
                        User u = null;
                        DataSnapshot needed = null;
                        //userList.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //User user = postSnapshot.getValue(User.class);
                            //System.out.println("NOOB "+user.getInterests().size());
                            if(mauth.getCurrentUser().getUid().equals(postSnapshot.getKey())){
                                final User user = postSnapshot.getValue(User.class);
                                //System.out.println("HERENOW"+user.getName());
                                userList.add(user);
                                try {
                                    /*Tag temp=new Tag();
                                    temp.setTagName(t1);
                                    System.out.println("NOOBAGAIN "+user.getInterests().size());
                                    user.addInterest(temp);*/

                                    user.addInterestsNew(t1);
                                }
                                catch(Exception e){
                                    e.printStackTrace();
                                    System.out.println("NO TAG FOUND SORRY");
                                }

                                final DatabaseReference updateData = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mauth.getCurrentUser().getUid());

                                if(!nameEdit.getText().toString().isEmpty()) updateData.child("name").setValue(nameEdit.getText().toString());
                                if(!bioEdit.getText().toString().isEmpty()) updateData.child("bio").setValue(bioEdit.getText().toString());
                                if(!genderEdit.isEmpty()) updateData.child("gender").setValue(genderEdit);
                                if(!t1.isEmpty()){
                                    System.out.println("HELLO WORLD");
                                    updateData.child("interestsNew").setValue(user.getInterestsNew());
                                    break;
                                }

                                try {
                                    Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(sickUri));
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bmp.compress(Bitmap.CompressFormat.PNG, 10, stream);
                                    bArray = stream.toByteArray();

                                    String path = "profilePhotos/" + UUID.randomUUID() + ".png";
                                    final StorageReference storageReference = storage.getReference(path);
                                    StorageMetadata meta = new StorageMetadata.Builder()
                                            .setCustomMetadata("profile", "user").build();
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
                                                user.setProfURL(downloadUri.toString());
                                                updateData.child("profURL").setValue(downloadUri.toString());
                                            } else {

                                            }

                                            StorageReference profPicReference = storage.getReferenceFromUrl(downloadUri.toString());
                                            final long OM = 5000 * 500000000;
                                            profPicReference.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                            }
                        }


                        goToProfile();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    public void goToProfile(){
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onRadioButtonClick(View view){
        boolean checked =((RadioButton) view).isChecked();

        switch(view.getId()){
            case R.id.radio_male:
                if(checked)
                    genderEdit="Male";
                break;

            case R.id.radio_female:
                if(checked)
                    genderEdit="Female";
                break;
            default:
                genderEdit="Male";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            sickUri = targetUri;
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