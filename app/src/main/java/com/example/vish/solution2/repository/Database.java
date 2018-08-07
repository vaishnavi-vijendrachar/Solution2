package com.example.vish.solution2.repository;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.vish.solution2.model.Data;

@android.arch.persistence.room.Database(
        entities = {Data.class},
        version = 1,
        exportSchema = false
)


public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, "scenario_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract TravelDao getDao();
}