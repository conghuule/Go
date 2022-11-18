package com.example.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class Fibonacci extends Service {
    boolean isRunning= true;
    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("MyService5Async-Handler", "Handler got from MyService5Async: "+ (String)msg.obj);
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("<<Fibonacci-Start>>", "I am alive-5Async!");
        Toast.makeText(this, "MyService5 Start", Toast.LENGTH_LONG).show();
        new ComputeFibonacciRecursivelyTask().execute(20, 50);
    }

    public Integer fibonacci(Integer n) {
        if( n==0 || n==1 ) return 1;
        else return fibonacci(n-1) + fibonacci(n-2);
    }

    @Override public void onDestroy() {
        Log.e("<<Fibonacci-Destroy>>", "I am dead-5-Async");
        isRunning= false;
        Toast.makeText(this, "MyService5 Stopped", Toast.LENGTH_LONG).show();
    }

    public class ComputeFibonacciRecursivelyTask extends AsyncTask<Integer, Integer, Integer> {
        @Override protected Integer doInBackground(Integer... params) {
            for(int i=params[0]; i < params[1]; i++){
                Integer fibn= fibonacci(i);
                if (!isRunning) break;
                publishProgress(i, fibn);
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Intent intentFilter5 = new Intent("matos.action.GOSERVICE5");
            String data = "dataItem-5-fibonacci-AsyncTask"+ values[0] + ": "+ values[1];
            intentFilter5.putExtra("MyService5DataItem", data);
            sendBroadcast(intentFilter5);
            Message msg = handler.obtainMessage(5, data);
            handler.sendMessage(msg);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}