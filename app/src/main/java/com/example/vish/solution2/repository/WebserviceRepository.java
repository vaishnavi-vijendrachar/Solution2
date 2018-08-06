package com.example.vish.solution2.repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.vish.solution2.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebserviceRepository {

    ApiService api;


    public WebserviceRepository() {

        //create retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ApiService.class);
    }

    public MutableLiveData<List<Response>> getDataList() {
        //add observer to monitor remote data
        final MutableLiveData<List<Response>> data = new MutableLiveData<>();

        //call webservice
        Call<List<Response>> call = api.getDetails();

        //asynchronus call
        call.enqueue(new Callback<List<Response>>() {
            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                if(response.isSuccessful()) {
                    List<Response> result = response.body();
                    data.setValue(result);
                }
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
