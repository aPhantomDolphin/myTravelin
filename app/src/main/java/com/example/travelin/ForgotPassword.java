package com.example.travelin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncUser;

import static com.example.travelin.Constants.REALM_URL;

public class ForgotPassword extends AppCompatActivity {

    private Realm realm = null;
    private EditText emailView;
    private Button forgotButton;
    private TextView loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder() //
                .name("travelin.realm") //
                .build();
        Realm.setDefaultConfiguration(config);

        loginView = findViewById(R.id.username);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });

        emailView = findViewById(R.id.email);
        if(validateEmail(emailView.getText().toString())){
            forgotButton = findViewById(R.id.password_reset_button);
            forgotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetPassword(emailView.getText().toString());
                }
            });
        }
    }

    /**
     * sends email to user for resetting password
     *
     * @param email
     */
    public void resetPassword(String email) {
        SyncUser.requestPasswordResetAsync(email, REALM_URL, new SyncUser.Callback<Void>(){
            @Override
            public void onSuccess(Void result){
                    goToLoginPage();
            }
            @Override
            public void onError(ObjectServerError error){

            }
        });
    }

    private void goToLoginPage(){
        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean validateEmail(String email) {

        emailView.setError(null);

        boolean validate = true;

        //email must be a purdue email
        if (!email.matches(".*@purdue\\.edu")) {
            emailView.setError("SignUp failed: invalid email");
            emailView.requestFocus();
            validate = false;
        }

        return validate;
    }

}