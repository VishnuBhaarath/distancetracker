package com.exercise.android.distanceusinggps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.location.LocationProvider;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textview;

    private int locationRequestCode = 1000;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.textview1);
        requestlocationupdates();
        callpermissions();
    }
    public void requestlocationupdates() {
        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(0);
        locationRequest.setInterval(0);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Toast.makeText(MainActivity.this,"hj",Toast.LENGTH_SHORT).show();
                double latitude=locationResult.getLastLocation().getLatitude();
                double  longitude=locationResult.getLastLocation().getLongitude();
                double prevlat=latitude;
                double prevlong=longitude;
                Log.e("Mainactivity", "lat" + locationResult.getLastLocation().getLatitude());
                Log.e("Mainactivity", "Long" + locationResult.getLastLocation().getLongitude());
               double value= distance(prevlat,prevlong,latitude,longitude);
               textview.setText(""+value);
            }
        }, getMainLooper());
    }
    public void callpermissions() {
        String[] permissions = {Manifest.
                permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                requestlocationupdates();
                // do your task.
            }

            /**
             * This method will be called if some of the requested permissions have been denied.
             *
             * @param context           The application context.
             * @param deniedPermissions The list of permissions which have been denied.
             */
            @Override
            public void onDenied(Context context, ArrayList <String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callpermissions();
            }

        });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
    
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
