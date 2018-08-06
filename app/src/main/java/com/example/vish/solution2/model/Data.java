package com.example.vish.solution2.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Data {
    public int id;
    @PrimaryKey
    @NonNull
    public String name;
    public String latitude;
    public String longitude;
    public String car;
    public String train;

}
