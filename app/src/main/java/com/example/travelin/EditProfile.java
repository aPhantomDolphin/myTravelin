package com.example.travelin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class EditProfile extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final Realm realm = Realm.getDefaultInstance();

        resetPass = findViewById(R.id.change_password);
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ForgotPassword.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        deleteAccount= findViewById(R.id.delete_button);
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm=Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmResults<User> res=realm.where(User.class).equalTo("email",SyncSingleton.getInstance().getEmail()).findAll();
                res.deleteAllFromRealm();
                realm.commitTransaction();
                SyncSingleton.getInstance().setEmail("");
                Intent intent = new Intent(EditProfile.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        bioEdit = findViewById(R.id.bio_edit_profile);
        /*realm.beginTransaction();
        RealmQuery<User>*/
        saveProfile = findViewById(R.id.save_profile);
        nameEdit = findViewById(R.id.email_edit_profile);


        homeButton = findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        changePassword = findViewById(R.id.change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this,ForgotPassword.class);
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


        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm=Realm.getDefaultInstance();
                interests=findViewById(R.id.intersts_edit_profile);
                String t1=interests.getText().toString();
                Tag tag=new Tag();
                tag.setTagName(t1);

                realm.beginTransaction();
                RealmQuery<User> realmQuery = realm.where(User.class);
                realmQuery.equalTo("email",SyncSingleton.getInstance().getEmail());
                RealmResults<User> results = realmQuery.findAll();

                User u = results.get(0);
                u.addInterest(tag);
                if(!nameEdit.getText().toString().isEmpty()) u.setName(nameEdit.getText().toString());
                if(!bioEdit.getText().toString().isEmpty()) u.setBio(bioEdit.getText().toString());
                if(!genderEdit.isEmpty()) u.setGender(genderEdit);
                if(bArray.length!=0) u.setImg(bArray);
                realm.commitTransaction();
                goToProfile();
            }
        });

    }

    public void goToProfile(){
        Intent intent = new Intent(EditProfile.this,ProfileActivity.class);
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
            try {
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                dpView.setImageBitmap(bmp);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 5, stream);
                bArray = stream.toByteArray();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
