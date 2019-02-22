package com.example.travelin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
    private EditText usernameText;
    private EditText confirmPasswordText;
    private View loginText;
    private View progressView;
    private View signUpFormView;
    //private Realm realm = null;
    private static SyncConfiguration config;
    private SyncUser user;
    private SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //realm = Realm.getDefaultInstance();

        //Go back to login screen by clicking "Login here"
        loginText = findViewById(R.id.textView2);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    SignUpActivity.this.finish();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });//End of going back to login screen

        //Set up the sign up form
        progressView = findViewById(R.id.signUp_progress);
        signUpFormView = findViewById(R.id.signUp_form);
        final Button signUpButton = findViewById(R.id.email_sign_up_button);
        signUpButton.setEnabled(true);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        confirmPasswordText = findViewById(R.id.confirmpassword);
        usernameText = findViewById(R.id.username);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(validateEmailPass()) {
                        attemptSignUp();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    } //End of onCreate

    private void attemptSignUp() throws InvalidKeySpecException, NoSuchAlgorithmException {

        final String signUpEmail = emailText.getText().toString();
        final String signUpPass = passwordText.getText().toString();
        final String hashPass = generateHash(signUpPass);
        final String signUpUsername = usernameText.getText().toString();

        //showProgress(true);

        final SyncCredentials credentials = SyncCredentials.usernamePassword(signUpEmail,signUpPass,true);

        SyncUser.logInAsync(credentials, AUTH_URL, new SyncUser.Callback<SyncUser>() {
            @Override
            public void onSuccess(SyncUser result) {
                //SyncConfiguration configuration = result.getDefaultConfiguration();
                //realm = Realm.getInstance(configuration);
                //Realm.setDefaultConfiguration(configuration);
                SyncSingleton.getInstance().setEmail(signUpEmail);
                //spref = getSharedPreferences("com.example.tavelin",MODE_PRIVATE);
                //spref.edit().putString("UserEmail",loginEmail).apply();
                //PreferenceUtils.saveEmail(loginEmail,getApplicationContext());

                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                User u = realm.createObject(User.class,signUpUsername);
                //u.setIdent(result.getIdentity());
                //u.addRating();
                u.setEmail(signUpEmail);
                u.setPassword(signUpPass);
                realm.commitTransaction();

                goToHomePage();
            }

            @Override
            public void onError(ObjectServerError error) {
                emailText.setError("Retry SignUp");
                emailText.requestFocus();
                Log.e("Signup Error", error.toString());
            }
        });
    }

    private void goToHomePage(){
        Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        SignUpActivity.this.finish();
    }

    private boolean validateEmailPass() {

        emailText.setError(null);
        passwordText.setError(null);
        confirmPasswordText.setError(null);
        usernameText.setError(null);

        boolean validate = false;

        String validateUsername = usernameText.getText().toString();
        //username can't be empty
        if(validateUsername.isEmpty()){
            usernameText.setError("Username cannot be empty");
            usernameText.requestFocus();
            return false;
        }

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





    private static String generateHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return (iterations + ":" + toHex(salt) + ":" + toHex(hash));
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static boolean validate(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private void getPK() {

        //return (int) realm.where(User.class).count() + 1;

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
