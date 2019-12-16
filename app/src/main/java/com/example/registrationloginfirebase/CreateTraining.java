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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class CreateTraining extends AppCompatActivity {

    EditText editTextName;
    EditText editTextDesc;
    EditText editTextPrice;
    EditText editTextAuthor;

    String trainingId;

    String markerID;

    String myRef;
    DatabaseReference myRefMarkers;



    private FirebaseAuth nAuth;
    private FirebaseAuth.AuthStateListener nAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_training);



    }

    public void createTrainingButtonClicked(View view) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        editTextName = (EditText) findViewById(R.id.trainingName);
        String name = editTextName.getText().toString();

        editTextDesc = (EditText) findViewById(R.id.trainingDesc);
        String desc = editTextDesc.getText().toString();

        editTextPrice = (EditText) findViewById(R.id.trainingPrice);
        String price = editTextPrice.getText().toString();



        trainingId = FirebaseDatabase.getInstance().getReference().child("Trainings").push().getKey();

        markerID = FirebaseDatabase.getInstance().getReference().child("Markers").child("LverR6Jc7Ejg9tbAsN3").getKey();




        Training training = new Training();
        training.setTeacher(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
        training.setName(name);
        training.setDesc(desc);
        training.setPrice(price);
        training.setMarkerID(markerID);
        training.setTrainingId(trainingId);
        training.setStudentID1("null");

        FirebaseDatabase.getInstance().getReference().child("Trainings").child(trainingId).setValue(training);



//     Need to send marker data
//        newTraing.child("markerLat").setValue(markerLat);
//        newTraing.child("markerLon").setValue(markerLon);


        Toast.makeText(getApplicationContext(),"You have successfully created a Training! Thank you for contribution!", Toast.LENGTH_LONG).show();



    }

    public void goBackButtonClicked (View view) {
        Intent goBackToMap = new Intent(CreateTraining.this,MapsActivity.class);
        startActivity(goBackToMap);
    }

}


