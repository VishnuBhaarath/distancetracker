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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);

        } else {


        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case 1000: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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
