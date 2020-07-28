package com.exercise.android.distancetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    List list;
    TextView textView1;
   SensorManager mSensorManager;
   Sensor mSensor;
   private boolean isSensorPresent=false;
   private double difference=0;
   private double previous=0;
  int stepcount=0;




    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView) findViewById(R.id.textview);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isSensorPresent = true;
        } else {

            textView1.setText("Sensor not available");
            isSensorPresent = false;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(isSensorPresent)
        {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensorPresent)
        {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x_acceleration=event.values[0];
        float y_acceleration=event.values[1];
        float z_acceleration=event.values[2];
        double Magnitude = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration + z_acceleration*z_acceleration);
        difference=Magnitude-previous;
        previous=Magnitude;
        if(difference>6){
            stepcount=stepcount+1;
        }
        textView1.setText(String.valueOf(Magnitude));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
