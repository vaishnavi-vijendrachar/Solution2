package com.example.vish.solution2.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.vish.solution2.model.Data;
import com.example.vish.solution2.model.Response;

import java.util.List;


@Dao
public interface TravelDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void insert(Data data);

    @Query("SELECT * FROM data WHERE name = :name")
    public Data getDataByName(String name);

    @Query("SELECT * FROM data")
    public List<Data> getAllData();

}
