package com.example.vish.solution2.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vish.solution2.model.Data;
import com.example.vish.solution2.model.Response;

import java.util.HashMap;
import java.util.List;

public class DatabaseTask  extends  AsyncTask<Data,Void,Void>{


    Context ctx;
    public DatabaseTask(Context context) {
        ctx = context;
    }

    @Override
    protected Void doInBackground(Data... data) {
        if(data != null) {
            TravelDao travelDao = Database.getDatabase(ctx).getDao();
            travelDao.insert(data[0]);
        }
        return null;
    }
}
