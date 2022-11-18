package com.example.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100;
    TextView txtMsg;
    Intent intentCallService4, intentCallService5, intentCallService6;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }

        txtMsg= (TextView) findViewById(R.id.txtMsg);
        findViewById(R.id.btnStart1).setOnClickListener(this);
        findViewById(R.id.btnStop1).setOnClickListener(this);
        findViewById(R.id.btnStart2).setOnClickListener(this);
        findViewById(R.id.btnStop2).setOnClickListener(this);
        findViewById(R.id.btnStart3).setOnClickListener(this);
        findViewById(R.id.btnStop3).setOnClickListener(this);
        Log.e("MAIN", "Main started");

        intentCallService4 = new Intent(this, MusicPlayer.class);
        intentCallService5 = new Intent(this, Fibonacci.class);
        intentCallService6 = new Intent(this, GPSFix.class);

        IntentFilter filter5 = new IntentFilter("matos.action.GOSERVICE5");
        IntentFilter filter6 = new IntentFilter("matos.action.GPSFIX");
        receiver = new MyEmbeddedBroadcastReceiver();
        registerReceiver(receiver, filter5);
        registerReceiver(receiver, filter6);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnStart1) {
            Log.e("MAIN", "onClick: starting service4");
            startService(intentCallService4);
        } else if(v.getId() == R.id.btnStop1) {
            Log.e("MAIN", "onClick: stopping service4");
            stopService(intentCallService4);
        }  else if(v.getId() == R.id.btnStart2) {
            Log.e("MAIN", "onClick: starting service5");
            startService(intentCallService5);
        } else if(v.getId() == R.id.btnStop2) {
            Log.e("MAIN", "onClick: stopping service5");
            stopService(intentCallService5);
        } else if(v.getId() == R.id.btnStart3) {
            Log.e("MAIN", "onClick: starting service6");
            startService(intentCallService6);
        } else if(v.getId() == R.id.btnStop3) {
            Log.e("MAIN", "onClick: stopping service6");
            stopService(intentCallService6);
        }
    }

    public class MyEmbeddedBroadcastReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            Log.e("MAIN>>", "ACTION: " + intent.getAction());
            if(intent.getAction().equals("matos.action.GOSERVICE5")) {
                String service5Data = intent.getStringExtra("MyService5DataItem");
                Log.e("MAIN>>", "Data received from Service5: "+ service5Data);
                txtMsg.append("\nService5Data: > "+ service5Data);
            } else if(intent.getAction().equals("matos.action.GPSFIX")) {
                double latitude = intent.getDoubleExtra("latitude", -1);
                double longitude = intent.getDoubleExtra("longitude", -1);
                String provider = intent.getStringExtra("provider");
                String service6Data = provider + " lat: "+ Double.toString(latitude) + " lon: "+ Double.toString(longitude);
                Log.e("MAIN>>", "Data received from Service6:"+ service6Data);
                txtMsg.append("\nService6Data: > "+ service6Data);
            }
        }
    }
}