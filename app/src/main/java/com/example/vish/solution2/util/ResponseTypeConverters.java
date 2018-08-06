package com.example.vish.solution2.util;

import android.arch.persistence.room.TypeConverter;

import com.example.vish.solution2.model.Location;
import com.example.vish.solution2.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ResponseTypeConverters{

    static Gson gson = new Gson();

    @TypeConverter
    public List<Response> stringToResponseList(String data){
        if(data == null){
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Response>>(){}.getType();
        return gson.fromJson(data,listType);
    }

    public String ResponseListToString(List<Response> response){
        return gson.toJson(response);
    }

}
