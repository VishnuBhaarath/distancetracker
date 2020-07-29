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
    TextView textview,latval,longval;
    double mdistance=0;
    private int locationRequestCode = 1000;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.textview1);
        latval=(TextView)findViewById(R.id.latitude);
        longval=(TextView)findViewById(R.id.longitude);
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
                double latitude=locationResult.getLastLocation().getLatitude();
                double  longitude=locationResult.getLastLocation().getLongitude();
                double prevlat=latitude;
                double prevlong=longitude;
                Location locationA = new Location("point A");

                locationA.setLatitude(latitude);
                locationA.setLongitude(longitude);

                Location locationB = new Location("point B");

                locationB.setLatitude(prevlat);
                locationB.setLongitude(prevlong);

                float distance = locationA.distanceTo(locationB);
                Toast.makeText(MainActivity.this,""+(distance*1000),Toast.LENGTH_SHORT).show();
                mdistance=distance*1000+mdistance;
                textview.setText(" "+mdistance);
                latval.setText(" "+locationResult.getLastLocation().getLatitude());
                longval.setText("  "+locationResult.getLastLocation().getLongitude());
             

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






}
