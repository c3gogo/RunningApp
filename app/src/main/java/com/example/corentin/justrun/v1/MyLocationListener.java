package com.example.corentin.justrun.v1;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Corentin on 11/11/2016.
 */

public class MyLocationListener implements LocationListener ,Runnable{
    private Location currentLocation;
    private Location oldLocation;
    private double oldLattitude;
    private double oldLongitude;
    private double newLattitude;
    private double newLongitude;
    private double dist;
    private double speed;
    @Override
    public void onLocationChanged(final Location location) {
        if(oldLocation==null){
            oldLocation = location;
        }
        currentLocation=location;
        //  Location request callback
        Thread t = new Thread(new Runnable() { // Thread for location operations
            @Override
            public void run() {
                speed = currentLocation.getSpeed();
                dist += currentLocation.distanceTo(oldLocation);


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
