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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    TextView textview;
    FusedLocationProviderClient fusedLocationClient;
    private int locationRequestCode = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = (TextView) findViewById(R.id.textview1);


            fusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            textview.setText("VB");
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener <Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location!=null){
                        Toast.makeText(MainActivity.this," "+location.getLatitude(),Toast.LENGTH_SHORT).show();


                    }
                    else{
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
            });








    }
}
