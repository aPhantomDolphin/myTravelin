package com.example.travelin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static com.example.travelin.Constants.AUTH_URL;
import static com.example.travelin.Constants.REALM_URL;

public class SignUpActivity extends AppCompatActivity {

    //UI references
    private AutoCompleteTextView emailText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private View loginText;
    private View progressView;
    private View signUpFormView;
    private Realm realm = null;
    private static SyncConfiguration config;
    private SyncUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        RealmConfiguration config = new RealmConfiguration.Builder() //
                .name("travelin.realm") //
                .build();
        Realm.setDefaultConfiguration(config);


        //Go back to login screen by clicking "Login here"
        loginText = findViewById(R.id.textView2);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });//End of going back to login screen

        //Set up the sign up form
        progressView = findViewById(R.id.signUp_progress);
        signUpFormView = findViewById(R.id.signUp_form);
        final Button signUpButton = findViewById(R.id.email_log_in_button);
        signUpButton.setEnabled(true);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        confirmPasswordText = findViewById(R.id.confirmpassword);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(validate()) {
                        attemptSignUp();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    } //End of onCreate

    private void attemptSignUp() {

        final String signUpEmail = emailText.getText().toString();
        final String signUpPass = passwordText.getText().toString();

        //showProgress(true);

        final SyncCredentials credentials = SyncCredentials.usernamePassword(signUpEmail,signUpPass,true);

        /*
         * this creates a separate thread that allows the server to login while
         * other things go on with the UI
         * Doing this asynchronously straight up didn't work
         */
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                user = SyncUser.logIn(credentials, AUTH_URL);
                //this is supposed to create the realm for this user at our specific URL
                config = user.createConfiguration(REALM_URL).build();
                realm = Realm.getInstance(config);

                //System.out.println("REACHED HERE");
                //System.out.println("USER IS:"+user.toString());


                realm.beginTransaction();
                int key = (int)(Math.random() * 100) + 2;
                User user = realm.createObject(User.class, key);
                user.setEmail(signUpEmail);
                user.setPassword(signUpPass);
                realm.commitTransaction();


                //System.out.println("HERE2");
                //System.out.println("USER DEETS: "+user.getEmail());

                /*realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int key = (int)(Math.random() * 100) + 2;
                        User user = realm.createObject(User.class,key);
                        user.setEmail(signUpEmail);
                        user.setPassword(signUpPass);

                    }}, new Realm.Transaction.OnSuccess(){
                        @Override
                        public void onSuccess(){
                            goToHomePage();
                        }

                    });*/

                //goToHomePage();
            }
        });

        thread.start();

    }

    private void goToHomePage(){
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private boolean validate() {

        emailText.setError(null);
        passwordText.setError(null);
        confirmPasswordText.setError(null);

        boolean validate = false;

        String validateEmail = emailText.getText().toString();
        //email must be a purdue email
        if(!validateEmail.matches(".*@purdue\\.edu")){
            emailText.setError("SignUp failed: invalid email");
            emailText.requestFocus();
            return false;
        }

        String validatePass = passwordText.getText().toString();
        String confirmPass = confirmPasswordText.getText().toString();

        if(!confirmPass.equals(validatePass)){
            confirmPasswordText.setError("SignUp failed: passwords don't match");
            confirmPasswordText.requestFocus();
        }
        else {
            /*requires a number, special character,
              upper case letter, lower case letter,
              must be longer than 4 characters and shorter than 32 characters*/
            boolean containsNum = validatePass.matches(".*[0-9].*");;
            boolean correctLength = ((validatePass.length() > 4) && (validatePass.length() < 32));
            boolean containsSpecial = !(validatePass.matches("[a-zA-Z0-9]*"));
            boolean containsUpper = validatePass.matches(".*[A-Z].*");
            boolean containsLower = validatePass.matches(".*[a-z].*");

            validate = containsNum && correctLength && containsSpecial && containsUpper && containsLower;

            if(!validate) {
                passwordText.setError("SignUp failed: invalid password");
                passwordText.requestFocus();
            }
        }
        return validate;
    }

    /**
     * Shows the progress UI and hides the signUp form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        signUpFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}
