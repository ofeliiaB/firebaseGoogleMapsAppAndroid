package com.example.registrationloginfirebase;

/**
 * Created By Ofeliia Bagratian
 * Prague College
 * Teesside University
 * Bachelor Project
 * <p>
 * <p>
 * Interactive Booking System For Yoga Trainings
 * YOGAA
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.Objects;

public class BookTraining extends AppCompatActivity {

    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;


    TextView viewLocation;



    TextView viewTitle;
    TextView viewDesc;
    TextView viewTeacher;
    TextView viewPrice;
    TextView viewStudent;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    Training training;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_book_training);

                    firebaseDatabase = FirebaseDatabase.getInstance();
                    myRef = firebaseDatabase.getReference().child("Trainings");

                    viewTitle = (TextView) findViewById(R.id.viewTitle);
                    viewDesc = (TextView) findViewById(R.id.viewDesc);
                    viewTeacher = (TextView) findViewById(R.id.viewTeacher);
                    viewPrice = (TextView) findViewById(R.id.viewPrice);
                    viewStudent = (TextView) findViewById(R.id.viewStudent);
                    myRef.child("-LvgTwAY_2xlWIvMXyAH").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            training = dataSnapshot.getValue(Training.class);

                            if(training == null){
                                Toast.makeText(BookTraining.this,
                                        "There is no training at this location yet :(", Toast.LENGTH_SHORT).show();
                            } else {
                                String title = training.getName();
                                viewTitle.setText("Name: "+title);

                                String desc = training.getDesc();
                                viewDesc.setText("Class Description: "+desc);

                                String price = training.getPrice();
                                viewPrice.setText("Price per hour: "+price+".00 CZK");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Getting Post failed, log a message
                            Log.w("loadPost:onCancelled", databaseError.toException());
                        }
                    });
                }




    public void bookTrainingButtonClicked(View view) {

        //code to get the Current User and add him to the Trainings section in the Database





        myRef.child("-LvgTwAY_2xlWIvMXyAH").child("studentID1").setValue(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());

        Intent goBackToMap = new Intent(BookTraining.this, MapsActivity.class);
        startActivity(goBackToMap);



        Toast.makeText(getApplicationContext(), "Congratulations! You have successfully booked a class!", Toast.LENGTH_LONG).show();


    }


    public void goBackButtonClicked(View view) {
        Intent goBackToMap = new Intent(BookTraining.this, MapsActivity.class);
        startActivity(goBackToMap);
    }


}
