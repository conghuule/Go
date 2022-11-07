package com.example.studentinformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends FragmentActivity implements MainCallbacks {
    FragmentTransaction ft;
    SQLiteDatabase database;
    String myDatabasePath;
    DetailFragment detailFragment;
    ListFragment listFragment;
    People[] peoples;
    Class[] classes;

    public void setPeoples(People[] _peoples) {
        peoples = _peoples;
    }

    public void setClasses(Class[] _classes) {
        classes = _classes;
    }

    private void buildFragments() {
        ft = getSupportFragmentManager().beginTransaction();
        listFragment = ListFragment.newInstance("Mobile CQ2022-2");
        ft.replace(R.id.list_fragment, listFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        detailFragment = DetailFragment.newInstance("Mobile CQ2022-2");
        ft.replace(R.id.detail_fragment, detailFragment);
        ft.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File storagePath = getApplication().getFilesDir();
        myDatabasePath = storagePath + "/" + "myDatabase";

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }


    @Override
    public void onDestroy() {
        // Database will stay opened all the time, only close it on app destroy
        if (database.isOpen()) {
            database.close();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createDatabase();
                buildFragments();
            } else {
                Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMsgFromFragToMain(String sender, int position) {
        if(sender.equals("DETAIL")) {
            try {
                detailFragment.onMsgFromMainToFragment(position);
                listFragment.onMsgFromMainToFragment(position);
            }
            catch (Exception e) {
                Log.e("ERROR", "onStringFromFragToMain" + e.getMessage());
            }
        }
        if (sender.equals("LIST")) {
            try {
                detailFragment.onMsgFromMainToFragment(position);
            }
            catch (Exception e) {
                Log.e("ERROR", "onStringFromFragToMain" + e.getMessage());
            }
        }
    }

    private void createDatabase() {

        try {
            database = SQLiteDatabase.openDatabase(myDatabasePath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            createTables();
            insertTables();
            queryDatabase();
            Toast.makeText(MainActivity.this, "Database created successfully", Toast.LENGTH_SHORT).show();
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        };
    }

    private void createTables() {
        try {
            database.execSQL("DROP TABLE IF EXISTS HOCSINH;");
            database.execSQL("DROP TABLE IF EXISTS LOPHOC;");
            database.execSQL("CREATE TABLE HOCSINH ( "
                    + "STUDENTID TEXT PRIMARY KEY, "
                    + "NAME TEXT, "
                    + "CLASSID INTEGER, "
                    + "AVG FLOAT);");
            database.execSQL("CREATE TABLE LOPHOC ( "
                    + "CLASSID INTEGER PRIMARY KEY, "
                    + "CLASSNAME TEXT);");
            Toast.makeText(this, "Create Tables Completed", Toast.LENGTH_SHORT).show();

        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    private void insertTables() {
        try {
            database.execSQL("INSERT INTO LOPHOC VALUES (0, '20_2')");
            database.execSQL("INSERT INTO HOCSINH VALUES ('20120219','Nguyen Minh Tri',0,10)");
            database.execSQL("INSERT INTO HOCSINH VALUES ('20120294','Le Cong Huu',0,10)");
            database.execSQL("INSERT INTO HOCSINH VALUES ('20120312','Le Tan Kiet',0,10)");
            database.execSQL("INSERT INTO HOCSINH VALUES ('20120325','Ngo Thanh Luc',0,10)");
            database.execSQL("INSERT INTO HOCSINH VALUES ('20120405','Nguyen Long Vu',0,10)");
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
    private void queryDatabase() {
        Cursor cursor = database.rawQuery("select count(*) as 'SLSV' from HOCSINH", null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            Toast.makeText(this, count + " HS", Toast.LENGTH_SHORT).show();
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}