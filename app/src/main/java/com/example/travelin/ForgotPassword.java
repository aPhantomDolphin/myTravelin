package com.example.travelin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncUser;

import static com.example.travelin.Constants.AUTH_URL;
import static com.example.travelin.Constants.REALM_URL;

public class ForgotPassword extends AppCompatActivity {

    //private Realm realm;
    private EditText emailText;
    private Button passwordReset;
    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();

            setContentView(R.layout.activity_resetpassword);
            backToLogin = findViewById(R.id.textView2);
            backToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ForgotPassword.this.finish();
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });



        emailText = findViewById(R.id.email);
        emailText.setError(null);
        passwordReset = findViewById(R.id.password_reset_button);

        passwordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmail(emailText.getText().toString())){
                    resetPassword(emailText.getText().toString());
                }
            }
        });

    }

    /**
     * sends email to user for resetting password
     *
     * @param email
     */
    public void resetPassword(final String email) {
        SyncUser.requestPasswordResetAsync(email, AUTH_URL, new SyncUser.Callback<Void>(){
            @Override
            public void onSuccess(Void result){

                try{
                    goToLoginPage();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ObjectServerError error){
                emailText.setError("Try again");
                emailText.requestFocus();
                Log.e("Password Reset Error", error.toString());
            }
        });
    }

    private void goToLoginPage(){
        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        ForgotPassword.this.finish();
    }

    private boolean validateEmail(String email) {

        emailText.setError(null);

        boolean validate = true;

        //email must be a purdue email
        if (!email.matches(".*@purdue\\.edu")) {
            emailText.setError("Password Reset Failed: Invalid Email");
            emailText.requestFocus();
            validate = false;
        }

        return validate;
    }

}