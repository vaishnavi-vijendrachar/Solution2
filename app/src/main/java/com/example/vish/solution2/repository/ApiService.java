package com.example.vish.solution2.repository;



import com.example.vish.solution2.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    String BASE_URL = "http://express-it.optusnet.com.au/";
    @GET("sample.json")
    Call<List<Response>> getDetails();
}
