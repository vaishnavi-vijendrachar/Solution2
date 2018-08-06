package com.example.vish.solution2.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vish.solution2.model.Data;
import com.example.vish.solution2.model.FromCentral;
import com.example.vish.solution2.model.Location;
import com.example.vish.solution2.model.Response;
//import com.example.vish.solution2.repository.DatabaseIntializer;
//import com.example.vish.solution2.repository.TravelDao;
//import com.example.vish.solution2.repository.DatabaseTask;
import com.example.vish.solution2.repository.Database;
import com.example.vish.solution2.repository.DatabaseTask;
import com.example.vish.solution2.repository.TravelDao;
import com.example.vish.solution2.repository.WebserviceRepository;
import com.example.vish.solution2.util.NetworkUtil;


import java.util.List;

/**
 * Created by admin on 25/7/2018.
 */

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Response>> resultList;
    private MutableLiveData<List<FromCentral>> fromCentralList;
    Context context;
    TravelDao travelDao;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        travelDao = Database.getDatabase(context).getDao();
    }

    public LiveData<List<Response>> getDataList() {
        if (resultList == null) {
            resultList = new MutableLiveData<>();
        }

        resultList = new WebserviceRepository().getDataList();

        return resultList;
    }

    public void insertInToDb(Data data){
        new DatabaseTask(context).execute(data);
    }

    public FromCentral getFomCentralfromDb(String name){

        Data data = travelDao.getDataByName(name);
        FromCentral fc = new FromCentral();
        fc.train = data.train;
        fc.car = data.car;
        return fc;
    }

    public Location getLocationfromDb(String name){
        Data data = travelDao.getDataByName(name);
        Location loc = new Location();
        loc.latitude = data.latitude;
        loc.longitude = data.longitude;
        return loc;

    }
}
