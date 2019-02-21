package com.example.travelin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static android.Manifest.permission.READ_CONTACTS;
import static com.example.travelin.Constants.AUTH_URL;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Realm realm=null;
    private TextView signUpHereText;
    private AutoCompleteTextView emailText;
    private EditText passText;
    Button buttonLogin;
    private View progressView;
    private View LoginFormView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(this);
        //realm = Realm.getDefaultInstance();

        progressView = findViewById(R.id.login_progress);
        LoginFormView = findViewById(R.id.email_login_form);

        //showProgress(true);

        if( SyncUser.current() != null) {
            showProgress(true);
            goToHomePage();
        }

        signUpHereText = findViewById(R.id.textView2);
        signUpHereText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goToSignUpPage();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        buttonLogin = findViewById(R.id.email_log_in_button);
        emailText = findViewById(R.id.email);
        passText = findViewById(R.id.password);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginEmail = emailText.getText().toString();
                final String loginPass = passText.getText().toString();

                SyncCredentials credentials = SyncCredentials.usernamePassword(loginEmail,loginPass,false);
                SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
                    @Override
                    public void onSuccess(SyncUser result) {
                        SyncConfiguration configuration = result.getDefaultConfiguration();
                        realm = Realm.getInstance(configuration);
                        Realm.setDefaultConfiguration(configuration);
                        goToHomePage();
                    }

                    @Override
                    public void onError(ObjectServerError error) {
                        emailText.setError("Retry Login");
                        emailText.requestFocus();
                        Log.e("Login Error", error.toString());
                    }
                });

            }
        });


    }

    private void goToHomePage(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        showProgress(false);
        startActivity(intent);
    }

    private void goToSignUpPage(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        showProgress(false);
        startActivity(intent);
    }

    /**
     * Shows the progress UI and hides the signUp form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        LoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        LoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                LoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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



