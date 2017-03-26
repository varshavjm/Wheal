package com.example.varsha.locationnotifier;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by varsha on 3/25/2017.
 */
public class GPSTracker extends IntentService implements LocationListener {

    private Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = true;

    // flag for network status
    boolean isNetworkEnabled = true;

    // flag for GPS status
    boolean canGetLocation = true;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    LocationListener listener;

    public GPSTracker() {
        super("GPSTracker");
    }
    public void onCreate()

    {
        super.onCreate();
        mContext = getApplicationContext();
        getLocation();
    }



    public void getLocation() {

            locationManager = (LocationManager) getSystemService(mContext.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println(location.toString());
                Toast.makeText(this.getApplicationContext(), "Permission not provided", Toast.LENGTH_LONG).show();
                return;
            }
        int i=0;
            while (i<10) {
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Toast.makeText(this.getApplicationContext(), "From GPS Tracker Latitude=" + location.getLatitude() + " Longitude=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    System.out.println(location.toString());
                }
                if(listener!=null)
                locationManager.removeUpdates(listener);
                i++;
            }
            //return location;
        }


/*            if (!isGPSEnabled) {

            } else {
                if (isGPSEnabled) {
                    if (location == null) {
                        if (Build.VERSION.SDK_INT >= 23 &&
                                ContextCompat.checkSelfPermission(mContext.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(mContext.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        canGetLocation = true;
                        Log.d("GPS Enabled", " Enabled");

                        if (locationManager != null) {
                            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this,null);
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                printMessage(latitude + "," + longitude);

                            }
                        }
                    } else {
                        canGetLocation = false;
                        Log.d("gps", "GPS Disable");
                    }
                }
            }

            if (!isNetworkEnabled) {
                // no network provider is enabled
            } else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    //                    Preferences.TAG_G_L = "L";
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            printMessage(latitude + "," + longitude);
                        }
                    }
                    Log.d("network", latitude + "," + longitude);*/


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        getLocation();
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onLocationChanged(Location location) {

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
}
//    void printMessage(final String str)
//    {
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable(){
//            @Override
//            public void run(){
//                Toast.makeText(mContext,"From Serice "+str,Toast.LENGTH_SHORT).show();
//            }
//        });


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */

    /**
     * Function to get latitude
     */

    /**
     * function to get longitude
     *
     * @return double
     */

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */


