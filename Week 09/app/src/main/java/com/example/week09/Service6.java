package com.example.week09;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class Service6 extends Service {
    String GPS_FILTER = "matos.action.GPSFIX";
    Thread serviceThread;
    LocationManager lm;
    GPSListener myLocationListener;
    boolean isRunning = true;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e(" << MyGpsService - onStart >>", "I am alive - GPS !");
        serviceThread = new Thread(new Runnable() {
            public void run() {
                getGPSFix_Version2(); // uses GPS chip provider
            }// run
        });
        serviceThread.start();
    }

    public void getGPSFix_Version2() {
        try {
            Looper.prepare();
// try to get your GPS location using the LOCATION.SERVIVE provider
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// This listener will catch and disseminate location updates
            myLocationListener = new GPSListener();
// define update frequency for GPS readings
            long minTime = 2000; // 2 seconds
            float minDistance = 0; // 5 meter
// request GPS updates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(" << MyGpsService - onDestroy >>", "I am dead - GPS");
        try {
            lm.removeUpdates(myLocationListener);
            isRunning = false;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
// capture location data sent by current provider
            double latitude = location.getLatitude(), longitude = location.getLongitude();
// assemble data bundle to be broadcasted
            Intent myFilteredResponse = new Intent(GPS_FILTER);
            myFilteredResponse.putExtra("latitude", latitude);
            myFilteredResponse.putExtra("longitude", longitude);
            myFilteredResponse.putExtra("provider", location.getProvider());
            Log.e(" >> GPS_Service <<", "Lat:"+latitude + "lon:"+longitude);
// send the location data out
            sendBroadcast(myFilteredResponse);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    ;// GPSListener class
}