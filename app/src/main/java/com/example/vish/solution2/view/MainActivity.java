package com.example.vish.solution2.view;

import android.arch.lifecycle.LiveData;
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
    CoordinatorLayout coordinatorLayout;
    Button navigate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initialiseViews();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mapFragment.getView().setVisibility(View.GONE);
               FromCentral fc = mainViewModel.getFomCentralfromDb(list.get(position));
                if(fc.car==null || fc.train == null) {
                    fc.car = "Nothing to display" ;
                    fc.train ="Nothing to display";
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
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mapFragment.getView().setVisibility(View.VISIBLE);
                if(mapFragment != null) {
                    mapFragment.getMapAsync(MainActivity.this);
                }
            }
        });

        if(NetworkUtil.isNetworkAvailable(this)) {
            //Initialize ViewModel in the layoutr
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            //add observer for changes in viewmodel
            mainViewModel.getDataList().observe(this, new Observer<List<Response>>() {
                //update ui when data is changed
                @Override
                public void onChanged(@Nullable List<Response> responses) {
                    if(responses != null) {
                        list = new ArrayList<>();
                        Data data;
                        for (Response i : responses) {
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
                }
            });
        }else{
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Network Not Available",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    private void initialiseViews() {
        spinner = findViewById(R.id.spinner);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        car = findViewById(R.id.car_tv);
        train = findViewById(R.id.train_tv);
        coordinatorLayout = findViewById(R.id.cordinatorLayout);
        navigate = findViewById(R.id.button);
    }

    //set the adapter to spinner
    private void setSpinner(List<String> list) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(arrayAdapter);
    }

    //google map call back
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if(lat !=null &&lng!=null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 8));
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng)));
        }

    }


}

