package com.example.vish.solution2.model;

import android.arch.persistence.room.Entity;

import java.security.PublicKey;

@Entity
public class Location {
    public String latitude;
    public String longitude;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
