package com.example.week09;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Service4 extends Service {
    public static boolean boolIsServiceCreated = false;
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service 4 Start", Toast.LENGTH_LONG).show();
        Log.e("Service4", "onCreate");
        boolIsServiceCreated = true;
        player = MediaPlayer.create(getApplicationContext(), R.raw.waiting_for_you);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service 4 Stopped", Toast.LENGTH_LONG).show();
        Log.e("Service4", "onDestroy");
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onStart(Intent intent, int startid) {
        if (player.isPlaying())
            Toast.makeText(this, "Service 4 Already Started " + startid, Toast.LENGTH_LONG).show();
        else Toast.makeText(this, "Service 4 Started " + startid, Toast.LENGTH_LONG).show();
        Log.e("Service4", "onStart");
        player.start();
    }
}
