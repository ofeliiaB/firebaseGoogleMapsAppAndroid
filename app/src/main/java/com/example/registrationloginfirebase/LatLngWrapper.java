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

import com.google.android.gms.maps.model.LatLng;

public class LatLngWrapper {

    public double latitude;
    public double longitude;

    public LatLngWrapper(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;

    }

    public LatLngWrapper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLngWrapper() {
    }

    public double getLatitude() {
        return latitude;
    }



    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    LatLng toLatLng() {
        return new LatLng(this.latitude, this.longitude);
    }
}