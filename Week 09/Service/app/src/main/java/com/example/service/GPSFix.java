package com.example.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GPSFix extends Service {
    String GPS_FILTER = "matos.action.GPSFIX";
    Thread serviceThread;
    LocationManager lm;
    GPSListener myLocationListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("<<MyGpsService-onStart>>", "I am alive-GPS!");
        Toast.makeText(this, "MyService6 Created", Toast.LENGTH_LONG).show();
        serviceThread = new Thread(new Runnable() {
            public void run() {
                getGPSFix_Version2();
            }
        });
        serviceThread.start();
    }

    public void getGPSFix_Version2() {
        try {
            Looper.prepare();
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            myLocationListener = new GPSListener();
            long minTime = 2000;
            float minDistance = 0;
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
            Looper.loop();
            Log.e(">>GPS_Service<<", "Lat:");
        } catch (Exception e) {
            Log.e(">>GPS_Service<<", String.valueOf(e));
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("<<MyGpsService-onDestroy>>", "I am dead-GPS");
        try {
            lm.removeUpdates(myLocationListener);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude(),
                    longitude = location.getLongitude();
            Intent myFilteredResponse = new Intent(GPS_FILTER);
            myFilteredResponse.putExtra("latitude", latitude);
            myFilteredResponse.putExtra("longitude", longitude);
            myFilteredResponse.putExtra("provider", location.getProvider());
            Log.e(">>GPS_Service<<", "Lat:" + latitude + " lon:" + longitude);
            sendBroadcast(myFilteredResponse);
        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}