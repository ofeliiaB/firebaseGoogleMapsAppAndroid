package com.example.registrationloginfirebase;

/**
 * Created By Ofeliia Bagratian
 * Prague College
 * Teesside University
 * Bachelor Project
 *
 *
 * Interactive Booking System For Yoga Trainings
 * YOGAA
 *
 * */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail;
    private EditText loginPass;
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;


    private DatabaseReference nDatabase;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPass = (EditText) findViewById(R.id.loginPass);

        nAuth = FirebaseAuth.getInstance();
        nDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        nAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (nAuth.getCurrentUser() != null) {
                    signIn();
                    Toast.makeText(getApplicationContext(),"NAMASTE! Thank you for joining our yoga community", Toast.LENGTH_LONG).show();
                }
            }

        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(nAuthListener);
    }

        public void loginButtonClicked(View view) {

        String email = loginEmail.getText().toString().trim();
        String pass = loginPass.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            nAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        checkUserExists();
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(),"Please, enter both email and password", Toast.LENGTH_LONG).show();
        }
    }

        public void checkUserExists () {
            final String user_id = Objects.requireNonNull(nAuth.getCurrentUser()).getUid();
            nDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChild(user_id)) {
                        signIn();
                    } else {
                        Toast.makeText(LoginActivity.this,"Incorrect email or password",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this,"Lost connection with database",Toast.LENGTH_LONG).show();
                }
            });
        }


    private void signIn() {
        Intent loginIntent = new Intent(LoginActivity.this,MapsActivity.class);
        startActivity(loginIntent);
    }



    }

