package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText nameView;
    private EditText emailView;
    private EditText passwordView;
    private Button registerButton;
    private TextView gobacktoLogin;
    private LinearLayout parentLayout;
    private EditText confirmPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        mAuth = FirebaseAuth.getInstance();

        nameView = findViewById(R.id.register_name);
        emailView = findViewById(R.id.register_email);
        passwordView = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);
        gobacktoLogin = findViewById(R.id.register_backtologin);
        confirmPasswordView = findViewById(R.id.confirm_password);

        gobacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });

        parentLayout = findViewById(R.id.parent_layout);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    hideKeyboard(v);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(emailView.getText().toString(), passwordView.getText().toString())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("EmailPassword", "createUserWithEmail:success");
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference().child("Users");

                                    User user = new User(firebaseUser.getEmail(),nameView.getText().toString());

                                    /*user.setGender("-");
                                    MyRating mr1=new MyRating();
                                    mr1.setRating(0.0);
                                    user.addRating(mr1);
                                    user.addReview(mr1);
                                    user.addReport();
                                    user.setBio("-");
                                    //Tag t=new Tag();
                                    //t.setTagName("-");
                                    //user.addInterest(t);
                                    user.addInterestsNew("new");*/

                                    myRef.child(firebaseUser.getUid()).setValue(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("EmailPassword", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    emailView.setError("Invalid email or password");
                                    emailView.requestFocus();
                                }
                            }
                        });
            }
        });


    }

    private boolean validateEmailPass() {

        //emailText.setError(null);
        //passwordText.setError(null);
        //confirmPassword.setError(null);

        emailView.setError(null);
        passwordView.setError(null);
        confirmPasswordView.setError(null);
        //usernameText.setError(null);

        boolean validate = false;

        /*String validateUsername = usernameText.getText().toString();
        //username can't be empty
        if(validateUsername.isEmpty()){
            usernameText.setError("Username cannot be empty");
            usernameText.requestFocus();
            return false;
        }*/
        String validateName = nameView.getText().toString();
        if(validateName.isEmpty()){
            nameView.setError("Name cannot be empty");
            nameView.requestFocus();
            return false;
        }

        //String validateEmail = emailText.getText().toString();

        String validateEmail = emailView.getText().toString();
        //email must be a purdue email
        if(!validateEmail.matches(".*@purdue\\.edu")){
            emailView.setError("SignUp failed: invalid email");
            emailView.requestFocus();
            return false;
        }

        //String validatePass = passwordText.getText().toString();
        //String confirmPass = confirmPasswordText.getText().toString();

        String validatePass = passwordView.getText().toString();
        String confirmPass = confirmPasswordView.getText().toString();

        if(!confirmPass.equals(validatePass)){
            confirmPasswordView.setError("SignUp failed: passwords don't match");
            confirmPasswordView.requestFocus();
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
                passwordView.setError("SignUp failed: invalid password");
                passwordView.requestFocus();
            }
        }
        return validate;
    }


    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    //
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

}
