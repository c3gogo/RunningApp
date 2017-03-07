package com.example.corentin.justrun.v1;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;

import static com.example.corentin.justrun.v1.RunFragment.handler;

/**
 * Created by Corentin on 11/11/2016.
 */

public class MyLocationListener implements LocationListener ,Runnable{

    private Location currentLocation;
    private Location oldLocation;
    private double oldLatitude;
    private double oldLongitude;
    private double newLatitude;
    private double newLongitude;
    private double dist;
    private float actualSpeed;
    private float averageSpeed;
    private int count;

    private final String AVERAGE_SPEED = "Average speed";
    private final String DIST = "Distance";




    public MyLocationListener() {
        actualSpeed = 0;
        averageSpeed = 0;
        count = 0;
        dist = 0;

    }

    @Override
    public void onLocationChanged(final Location location) {

        count++;

        if(oldLocation==null){
            oldLocation = location;
        }

        currentLocation=location;
        //  Location request callback
        Thread t = new Thread(new Runnable() { // Thread for location operations
            @Override
            public void run() {

                // Thread for operations on location

                Bundle messageBundle = new Bundle();
                Message myMessage;


                actualSpeed = currentLocation.getSpeed(); // speed in m/s
                actualSpeed = (1/actualSpeed)*(60/1000); //convert to min/km

                averageSpeed += currentLocation.getSpeed();
                averageSpeed /= count; //average speed in m/s
                averageSpeed = (1/averageSpeed)*(60/1000); //convert to min/km

                dist += currentLocation.distanceTo(oldLocation)/1000; // distance in km

                myMessage=handler.obtainMessage();
                messageBundle.putDouble(AVERAGE_SPEED,averageSpeed);
                messageBundle.putDouble(DIST,dist);
                handler.sendMessage(myMessage);


            }
        });

        t.start();


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void run() {

    }
}
