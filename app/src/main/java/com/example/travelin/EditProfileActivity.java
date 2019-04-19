package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

    private EditText bioEdit;
    private EditText nameEdit;
    private String genderEdit="Male";
    private Button saveProfile;
    private Button changePassword;
    private EditText interests;
    private ImageView dpView;
    private ImageView addImg;
    RadioGroup radioGroup;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    byte[] bArray=new byte[0];
    Button deleteAccount;
    private Uri sickUri = null;
    private BottomNavigationView mMainNav;
    private User thisUser;

    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mauth.getCurrentUser();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    //private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-learn-f2515.appspot.com");
    private String mainID = firebaseUser.getUid();
    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);

        mMainNav = findViewById(R.id.bottom_navigation);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;

                    case R.id.navigation_profile:
                        intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return true;

                    case R.id.navigation_search:
                        intent = new Intent(EditProfileActivity.this, SearchFilterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });

        deleteAccount = findViewById(R.id.deleteprofilebutton);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditProfileActivity.this, ConfirmDeleteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        changePassword = findViewById(R.id.changepasswordbutton);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ForgotPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dpView = findViewById(R.id.profile_image);
        addImg = findViewById(R.id.addpictures);
        nameEdit = findViewById(R.id.editName);
        bioEdit = findViewById(R.id.editBio);
        interests = findViewById(R.id.editInterests);
        radioGroup = findViewById(R.id.radio_group);
        radioButtonMale = findViewById(R.id.radio_male);
        radioButtonFemale = findViewById(R.id.radio_female);
        saveProfile = findViewById(R.id.saveprofilebutton);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    thisUser = postSnapshot.getValue(User.class);

                    if (firebaseUser.getUid().equals(postSnapshot.getKey())) {
                        if (!thisUser.getUsername().isEmpty()) {
                            nameEdit.setText(thisUser.getUsername());
                        }
                        if (!thisUser.getBio().isEmpty()) {
                            bioEdit.setText(thisUser.getBio());
                        }
                        if (!thisUser.getInterestsNew().isEmpty()) {
                            interests.setHint(thisUser.getInterestsNew());
                        }
                        if (!thisUser.getGender().isEmpty()) {
                            if (thisUser.getGender().equals("Male")) {
                                genderEdit = "Male";
                                radioButtonMale.setChecked(true);
                            } else if (thisUser.getGender().equals("Female")) {
                                genderEdit = "Female";
                                radioButtonFemale.setChecked(true);
                            }
                        }
                        if (thisUser.getProfURL() != null && !thisUser.getProfURL().equals("")) {
                            StorageReference storageRef = storage.getReferenceFromUrl(thisUser.getProfURL());

                            final long OM = 5000 * 50000000;
                            storageRef.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String getName = nameEdit.getText().toString();
                final String getBio = bioEdit.getText().toString();
                final String getInterests = interests.getText().toString();

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final DatabaseReference updateData = userRef.child(mainID);
                        if(!getName.isEmpty()) updateData.child("name").setValue(nameEdit.getText().toString());
                        if(!getBio.isEmpty()) updateData.child("bio").setValue(bioEdit.getText().toString());
                        if(!genderEdit.isEmpty()) updateData.child("gender").setValue(genderEdit);
                        if(!getInterests.isEmpty()){
                            thisUser.addInterestsNew(getInterests);
                            updateData.child("interestsNew").setValue(thisUser.getInterestsNew());
                        }
                        if(sickUri != null && !sickUri.equals("")){
                            try {
                                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(sickUri));
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
                                            thisUser.setProfURL(downloadUri.toString());
                                            updateData.child("profURL").setValue(downloadUri.toString());
                                        }
                                    }
                                });

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                goToProfile();

            }
        });


        /*

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
                        DatabaseReference userRef = ref.child("Users");
                        ArrayList<User> userList = new ArrayList<>();
                        User u = null;
                        DataSnapshot needed = null;
                        userList.clear();
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
                                    user.addInterest(temp);

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
                                                user.setProfURL(downloadUri.toString());
                                                updateData.child("profURL").setValue(downloadUri.toString());
                                            } else {

                                            }

                                            StorageReference fuckthis = storage.getReferenceFromUrl(downloadUri.toString());
                                            final long OM = 5000*500000000;
                                            fuckthis.getBytes(OM).addOnSuccessListener(new OnSuccessListener<byte[]>() {
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
                        // TODO: figure out storage for images
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
    */

    }

    public void goToProfile(){
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        goToProfile();
    }
}