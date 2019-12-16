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

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText emailField;
    private EditText passField;
    private EditText phoneField;
    private FirebaseAuth nAuth;
    private DatabaseReference nDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nameField = (EditText) findViewById(R.id.nameField);

        passField = (EditText) findViewById(R.id.passField);

        emailField = (EditText) findViewById(R.id.emailField);

        phoneField = (EditText) findViewById(R.id.phoneField);

        nAuth = FirebaseAuth.getInstance();
        nDatabase = FirebaseDatabase.getInstance().getReference().child("Users");



    }


    public void registerButtonClicked(View view) {

        final String name = nameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String password = passField.getText().toString().trim();
        String phone = phoneField.getText().toString().trim();


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phone)) {
            nAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        //User Object sending
                        User user = new User();
                        user.setUser_id(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                        user.setName(name);
                        user.setPhone(phone);
                        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);

                        Intent mainIntent = new Intent(RegisterActivity.this,MapsActivity.class);

                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(mainIntent);

                        Toast.makeText(getApplicationContext(),"NAMASTE! Thank you for joining our yoga community", Toast.LENGTH_LONG).show();

                    }

                }
            });
        } else {
            Toast.makeText(getApplicationContext(),"Please, enter your credentials correctly", Toast.LENGTH_LONG).show();

        }
    }



    public void gotoLoginButton(View view) {

        Intent gotoLogin = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(gotoLogin);
        Toast.makeText(getApplicationContext(),"Welcome back!", Toast.LENGTH_LONG).show();

    }
}
