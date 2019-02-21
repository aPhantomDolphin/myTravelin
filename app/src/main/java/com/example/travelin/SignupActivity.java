package com.example.travelin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class SignupActivity extends AppCompatActivity {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Realm realm = null;
    private static SyncConfiguration config;
    private SyncUser user;

    // TODO: add code to show signup page
    // TODO: link functions to button presses

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder() //
                .name("travelin.realm") //
                .build();
        Realm.setDefaultConfiguration(config);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_log_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }




    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(password)) {
            mPasswordView.setError("Login failed: invalid email or password");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (!isEmailValid(email)) {
            mEmailView.setError("Login failed: invalid email or password");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //showProgress(true);

            //this is the URL for our main server
            final String authURL = "https://unbranded-metal-bacon.us1a.cloud.realm.io";

            //credentials stores the username, email, and a createUser variable
            //if that value is true, it will create the user if it doesn't exist
            //which is used to create account
            //if it is false, it can only be used to login
            final SyncCredentials credentials = SyncCredentials.usernamePassword(email, password, false);

            /**
             * this creates a separate thread that allows the server to login while
             * other things go on with the UI
             * Doing this asynchronously straight up didn't work
             */
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    user = SyncUser.logIn(credentials, authURL);
                    String url = "realms://unbranded-metal-bacon.us1a.cloud.realm.io/travelin";

                    //this is supposed to create the realm for this user at our specific URL
                    config = user.createConfiguration(url).build();
                    realm = Realm.getInstance(config);

                    realm.beginTransaction();
                    User user = realm.createObject(User.class, 42);
                    user.setEmail("whoa");
                    user.setPassword("lel");
                    realm.commitTransaction();
                }
            });

            thread.start();
        }


    }


    private boolean isEmailValid(String email) {
        return email.matches(".*@purdue\\.edu");
    }


    /**
     * password requires a number, special character,
     * upper case letter, lower case letter,
     * must be longer than 4 characters and shorter than 32 characters
     */
    private boolean isPasswordValid(String password) {
        boolean containsNum;
        boolean correctLength = false;
        boolean containsSpecial = true;
        boolean containsUpper;
        boolean containsLower;

        containsNum = password.matches(".*[0-9].*");
        if ((password.length() > 4) && (password.length() < 32)) {
            correctLength = true;
        }
        if (password.matches("[a-zA-Z0-9]*")) {
            containsSpecial = false;
        }
        containsUpper = password.matches(".*[A-Z].*");
        containsLower = password.matches(".*[a-z].*");

        if ((containsNum == true) && (correctLength == true) && (containsSpecial == true)
                && (containsUpper == true) && (containsLower == true)) {
            return true;
        }
        return false;
    }
}
