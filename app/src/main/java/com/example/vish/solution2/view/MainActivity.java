package com.example.vish.solution2.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vish.solution2.R;
import com.example.vish.solution2.model.Data;
import com.example.vish.solution2.model.FromCentral;
import com.example.vish.solution2.model.Location;
import com.example.vish.solution2.model.Response;
import com.example.vish.solution2.util.NetworkUtil;
import com.example.vish.solution2.viewmodel.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    Spinner spinner;
    TextView car,train;
    SupportMapFragment mapFragment;
    MainViewModel mainViewModel;
    List<String> list;
    Double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        CoordinatorLayout coordinatorLayout = findViewById(R.id.cordinatorLayout);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FromCentral fc = mainViewModel.getFomCentralfromDb(list.get(position));
                if(fc.car==null || fc.train == null) {
                    fc.car = "00"+"mins";
                    fc.train ="00"+"mins";
                }
                    car.setText(fc.car.toString());
                    train.setText(fc.train.toString());

                    Location loc = mainViewModel.getLocationfromDb(list.get(position));
                    lat = Double.parseDouble(loc.latitude);
                    lng = Double.parseDouble(loc.longitude);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        Button navigate = findViewById(R.id.button);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapFragment.getView().setVisibility(View.VISIBLE);
                mapFragment.getMapAsync(MainActivity.this);
            }
        });

        //map stuff
        mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getView().setVisibility(View.INVISIBLE);



        car = findViewById(R.id.car_tv);
        train = findViewById(R.id.train_tv);

        //check if the network is connected
        if(NetworkUtil.isNetworkAvailable(MainActivity.this)) {
        //Initialize ViewModel in the layoutr
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


            //get data from view model
            mainViewModel.getDataList();
        }else{
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"No Network!",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        //add observer for changes in viewmodel
        mainViewModel.getDataList().observe(this, new Observer<List<Response>>() {
            //update ui when data is changed
            @Override
            public void onChanged(@Nullable List<Response> responses) {
                //set spinner
                UpdateUI(responses);
            }
        });



    }


    //retrieve data and save
    private void UpdateUI(List<Response> responses) {
        list = new ArrayList<>();
        Data data ;
        for(Response i : responses) {
            list.add(i.name);
            data = new Data();
            data.name = i.name;
            data.id = i.id;
            data.latitude = i.location.latitude;
            data.car = i.fromcentral.car;
            data.train = i.fromcentral.train;
            data.longitude = i.location.longitude;

            mainViewModel.insertInToDb(data);
        }
        setSpinner(list);
    }

    //set the adapter to spinner
    private void setSpinner(List<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(arrayAdapter);
    }

    //google map call back
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),8));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lng)));

    }
}
