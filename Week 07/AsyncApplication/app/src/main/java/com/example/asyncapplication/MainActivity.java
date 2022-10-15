package com.example.asyncapplication;

import static java.lang.Math.*;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final int progressStep = 100;
    EditText edtBox;
    TextView txtMsg;
    ProgressBar progressBar;
    Button btnDoItAgain;
    private int nWork;
    private int accumulator = 0;
    private int percentage = 0;
    final private int MAX_WORK = 10000;

    private Handler handler = new Handler();
    private Runnable foreground = new Runnable() {
        @Override
        public void run() {
            try {
                progressBar.incrementProgressBy(progressStep);
                accumulator += progressStep;
                percentage = (100 * accumulator) / nWork;
                txtMsg.setText(percentage + "%");

                btnDoItAgain.setEnabled(accumulator >= progressBar.getMax());
            } catch (Exception e) {
                Log.e("Foreground:", e.getMessage());
            }
        }
    };
    private Runnable background = new Runnable() {
        @Override
        public void run() {
            try {
                for (int i = 0; i < nWork / progressStep; i++) {
                    SystemClock.sleep(1);
                    handler.post(foreground);
                }
            } catch (Exception e) {
                Log.e("Background:", e.getMessage());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtBox = (EditText) findViewById(R.id.edtBox);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        progressBar = (ProgressBar) findViewById(R.id.myBarHor);
        btnDoItAgain = (Button) findViewById(R.id.btnDoItAgain);

        btnDoItAgain.setOnClickListener(view -> {
            if (edtBox.getText().toString().length() > 0) onStart();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (edtBox.getText().toString().length() > 0) {
            int _nWork = 0;
            try {
                _nWork = Integer.parseInt(edtBox.getText().toString());
                nWork = 100 * min(_nWork, MAX_WORK);
                if(_nWork > MAX_WORK) {
                    Toast.makeText(getApplicationContext(), "Not accept number bigger than " + MAX_WORK, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                nWork = 100 * MAX_WORK;
                Toast.makeText(getApplicationContext(), "Not accept number bigger than " + MAX_WORK, Toast.LENGTH_LONG).show();
            }
        }
        accumulator = 0;
        percentage = 0;
        txtMsg.setText("0%");
        progressBar.setProgress(0);
        progressBar.setMax(nWork);
        progressBar.setVisibility(View.VISIBLE);

        Thread thread = new Thread(background, "backgroundThread");
        thread.start();
    }
}