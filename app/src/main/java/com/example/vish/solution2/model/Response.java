package com.example.vish.solution2.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.vish.solution2.util.ResponseTypeConverters;

import java.util.List;

//@Entity
public class Response {
    public int id;
    public String name;
    public FromCentral fromcentral;
    public Location location;

}
