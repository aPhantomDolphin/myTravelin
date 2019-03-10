package com.example.testrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailView;
    private TextView gobacktoLogin;
    private Button forgetButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        emailView = findViewById(R.id.forget_email);
        gobacktoLogin = findViewById(R.id.forget_backtologin);

        gobacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forgetButton = findViewById(R.id.forget_button);
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = mAuth.sendSignInLinkToEmail(emailView.getText().toString(), ActionCodeSettings.newBuilder().build());
                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(ForgetPassword.this,LoginActivity.class));
                }
            }
        });



    }

}
