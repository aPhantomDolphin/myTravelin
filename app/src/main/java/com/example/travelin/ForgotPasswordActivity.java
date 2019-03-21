package com.example.travelin;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static java.lang.Thread.sleep;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailView;
    private TextView gobacktoLogin;
    private Button forgetButton;
    private TextView confirmText;
    private LinearLayout parentLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        parentLayout = findViewById(R.id.parent_layout);
        emailView = findViewById(R.id.forget_email);
        gobacktoLogin = findViewById(R.id.forget_backtologin);
        confirmText = findViewById(R.id.confirm_text);

        gobacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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


        forgetButton = findViewById(R.id.forget_button);
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(emailView.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            try{
                                hideKeyboard(parentLayout);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            emailView.setVisibility(View.GONE);
                            forgetButton.setVisibility(View.GONE);
                            gobacktoLogin.setVisibility(View.GONE);
                            confirmText.setText(getString(R.string.reset_email_text));
                            new CountDownTimer(1500,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                                }
                            }.start();
                        }
                        else{
                            emailView.setError("Please retry");
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
