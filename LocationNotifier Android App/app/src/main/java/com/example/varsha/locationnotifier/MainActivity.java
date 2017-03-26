package com.example.varsha.locationnotifier;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener,Runnable  {

    LocationManager locationManager;
    Location location;
    LocationListener listener;
    TextView view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view=(TextView) findViewById(R.id.textView);
          locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.getApplicationContext(),"Permission not provided",Toast.LENGTH_LONG).show();

            return  ;
        }
        while(true) {
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this,null);
            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null) {
               Toast.makeText(this.getApplicationContext(), "Latitude=" + location.getLatitude() + " Longitude=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                break;
            }
            //locationManager.removeUpdates(listener);

        }

        try {
            getAddress();

        } catch (IOException e) {
            e.printStackTrace();
        }
            run();

//        ServiceConnection conn=new ServiceConnection() {
//            @Override
//            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//
//            }
//
//            @Override
//            public void onServiceDisconnected(ComponentName componentName) {
//
//            }
//        };
//        android.os.Debug.waitForDebugger();
       // Intent intent = new Intent(getApplicationContext(), GPSTracker.class);
     //   startService(intent);
       //  run();


    }


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this.getApplicationContext(),"changed Location Latitude::"+location.getLatitude()+" Longitude::"+location.getLongitude(),Toast.LENGTH_SHORT).show();
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

        for(int i=0;i<50;i++)
        {
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"Permission not provided",Toast.LENGTH_LONG).show();
                return ;
            }

            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, MainActivity.this,null);
            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                getAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(location!=null) {

                Toast.makeText(MainActivity.this, "from Run Latitude=" + location.getLatitude() + " Longitude=" + location.getLongitude(), Toast.LENGTH_SHORT).show();
            }
            if(listener!=null)
            locationManager.removeUpdates(listener);
            try {
                Thread.sleep(300);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }

    private void getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        address=address+" " +addresses.get(0).getLocality();
        address=address+" " + addresses.get(0).getAdminArea();
        address=address+" " + addresses.get(0).getCountryName();
        address=address+" " + addresses.get(0).getPostalCode();
        Toast.makeText(this.getApplicationContext(),address,Toast.LENGTH_SHORT).show();
        view.setText(address);
        return;

    }

}
