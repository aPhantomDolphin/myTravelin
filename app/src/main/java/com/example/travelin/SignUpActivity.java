package com.example.travelin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    private View loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //To go back to login screen
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
        });
    }
}
