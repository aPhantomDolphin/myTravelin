package com.example.testrealm;

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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText nameView;
    private EditText emailView;
    private EditText passwordView;
    private Button registerButton;
    private TextView gobacktoLogin;
    private LinearLayout parentLayout;

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

                                    User user = new User(nameView.getText().toString(),firebaseUser.getEmail());

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

    protected void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
