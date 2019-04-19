package com.example.travelin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class ConfirmDeleteActivity extends AppCompatActivity {

    private FirebaseAuth mauth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = mauth.getCurrentUser();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private String mainID = firebaseUser.getUid();
    private DatabaseReference userRef = firebaseDatabase.getReference().child("Users");

    private TextView goBack;
    private EditText emailText;
    private EditText passText;
    private Button confirmDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete);

        goBack = findViewById(R.id.go_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeleteActivity.this.finish();
            }
        });

        emailText = findViewById(R.id.confirm_email);
        passText = findViewById(R.id.confirm_password);

        confirmDelete = findViewById(R.id.confirm_account);
        confirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailText.getText().toString().isEmpty()){
                    emailText.setError("Name cannot be empty");
                    emailText.requestFocus();
                }
                if(passText.getText().toString().isEmpty()){
                    passText.setError("Password cannot be empty");
                    passText.requestFocus();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmDeleteActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete all post info and replies?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AuthCredential credential = EmailAuthProvider.getCredential(emailText.getText().toString(), passText.getText().toString());
                                firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                                                if (mainID.equals(postSnapshot.getKey())) {
                                                                    userRef.child(mainID).removeValue();
                                                                }

                                                                System.out.println("ACCOUNT DELETED");


                                                                Intent intent = new Intent(ConfirmDeleteActivity.this, LoginActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                startActivity(intent);

                                                            }
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError){

                                                        }
                                                    });

                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

}
