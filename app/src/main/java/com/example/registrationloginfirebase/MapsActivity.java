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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.firebase.database.DatabaseError;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;

import android.nfc.Tag;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;




public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private static final LatLng Prague = new LatLng(50.0755, 14.4378);

    private FirebaseAuth nAuth;
    String trainingId;

    String markerID;


    private HashMap<String, Marker> markers = new HashMap<String, Marker>();


    public static final String TAG = MapsActivity.class.getSimpleName();


    //
    private FirebaseDatabase nFirebaseDatabase;
    private DatabaseReference RefMarkerInfo;

    private DatabaseReference RefClickCounter;

    private DatabaseReference RefTagData;

    private FirebaseAuth.AuthStateListener nAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toast.makeText(getApplicationContext(),"Click on Map to set a location for your Training", Toast.LENGTH_LONG).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
//        mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);


        nFirebaseDatabase = FirebaseDatabase.getInstance();
        RefMarkerInfo = nFirebaseDatabase.getReference("Markers");


        RefMarkerInfo.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                LatLng myLatLon = dataSnapshot.getValue(LatLngWrapper.class).toLatLng();

                // stash the key in the title, for recall later

                Marker myMarker = mMap.addMarker(new MarkerOptions()
                        .position(myLatLon).draggable(false)
//                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.yougagirl1))
                        .title(dataSnapshot.getKey()));
                myMarker.setTag(0);
                // cache the marker locally
                 markers.put(dataSnapshot.getKey(), myMarker);
            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                LatLng myLatLon = dataSnapshot.getValue(LatLngWrapper.class).toLatLng();
                // Move markers on the map if changed on Firebase
                Marker changedMarker = markers.get(dataSnapshot.getKey());
                changedMarker.setPosition(myLatLon);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // When markers are removed from
                Marker deadMarker = markers.get(dataSnapshot.getKey());
                deadMarker.remove();
                markers.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.v(TAG, "moved !" + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.v(TAG, "canceled!" + databaseError.getMessage());
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map) {


        mMap = map;
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(30.0f);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Prague, 17.0f));

        CameraUpdateFactory.scrollBy(0, 5);







        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker myMarker) {

                Integer clickCount = (Integer) myMarker.getTag();
                    clickCount++;



                if (clickCount == 0) {
                    Toast.makeText(getApplicationContext(), "No clicks", Toast.LENGTH_LONG).show();
                    //addd intent code

                } else if (clickCount == 1) {
//                    Toast.makeText(getApplicationContext(), "Go to Create ", Toast.LENGTH_LONG).show();

                    Intent goToCreate = new Intent(MapsActivity.this,CreateTraining.class);
                    startActivity(goToCreate);

                } else if (clickCount > 1 && clickCount <= 10) {
//                    Toast.makeText(getApplicationContext(), "Go to Book ", Toast.LENGTH_LONG).show();


                } else if (clickCount > 10) {
                    Toast.makeText(getApplicationContext(), "Sorry, this class is already full!", Toast.LENGTH_LONG).show();

                }
                    return true;
                }

        });



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                RefMarkerInfo.push().setValue(new LatLngWrapper(latLng));

            }
        });


    }


    //handling menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); //"menu_main" is the XML-File in res
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.addIcon:
                //Intent to Create TRaining Activity
                startActivity(new Intent(MapsActivity.this, BookTraining.class));
                return true;
            case R.id.signOut:
                //Sign out
                nAuth = FirebaseAuth.getInstance();
                nAuth.signOut();
                startActivity(new Intent(MapsActivity.this, MainActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

