package com.example.corentin.justrun.v1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.corentin.justrun.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.Console;

/**
 * Created by Corentin on 01/11/2016.
 */

public class RunFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    static GoogleMap googleMap;
    private MapView mapView;
    static final String AVERAGE_SPEED = "Average speed";
    static final String DIST = "Distance";
    static Context context;
    static double averageSpeed;
    static double dist;

    static Handler handler = new Handler(){

        //Handle messages which come from the location service

        @Override
        public void handleMessage(Message msg) {
            averageSpeed = msg.getData().getDouble(AVERAGE_SPEED);
            dist = msg.getData().getDouble(DIST);
            updateUI();
        }
    };



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.run_view, container, false);

        initUI(savedInstanceState);
        context = this.getContext();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void initMap(Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(this);
    }

    public void initUI(Bundle savedInstanceState){
        initMap(savedInstanceState);  //function for initialize the map


    }
    static void updateUI() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            googleMap.setMyLocationEnabled(true);
        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            googleMap.setMyLocationEnabled(true);  // Set the position of the user at the beginning
        }
    }
}
