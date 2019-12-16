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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity  extends AppCompatActivity {
    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nAuth = FirebaseAuth.getInstance();
        nAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser()==null) {
                    Intent loginIntent = new Intent(MainActivity.this,
                            RegisterActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginIntent);
                }
            }
        };
    }
    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(nAuthListener);
    }
}
