package com.example.testrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailView;
    private EditText passwordView;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Map<String, User> users = new HashMap<>();

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-learn-f2515.firebaseio.com/");
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("users");

        /*List<User> userList = new ArrayList<>();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                userList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    userList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

         for (int i = 0; i < 10; i++) {
            Random r = new Random();
            int low = 10;
            int high = 100;
            int result = r.nextInt(high - low) + low;
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String name = new String (array, Charset.forName("UTF-8"));
            String email = new String (array, Charset.forName("UTF-8"));
            String pass = new String (array, Charset.forName("UTF-8"));
            String gender;
            if (i % 2 == 0) {
                gender = "female";
            } else {
                gender = "male";
            }

            User user = new User(email, name, pass, result, gender);
            user.addElement("fuck" + 33);
            user.addElement("this" + 57);
            String key = "User " + i;
            users.put(key, user);
        }

        usersRef.setValue(users);

        /*usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                System.out.println(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Read failed");
            }
        });*/
        emailView = findViewById(R.id.register_email);
        passwordView = findViewById(R.id.register_password);
        registerButton = findViewById(R.id.register_button);

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
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
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
}
