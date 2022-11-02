package com.example.studentinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements MainCallbacks {
    SQLiteDatabase db;
    FragmentTransaction ft;
    ListFragment listFragment;
    DetailFragment detailFragment;

    List<People> peoples = new ArrayList<People>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getSupportFragmentManager().beginTransaction();
        detailFragment = DetailFragment.newInstance("detail");
        ft.replace(R.id.detail_fragment, detailFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        listFragment = ListFragment.newInstance("list");
        ft.replace(R.id.list_fragment, listFragment);
        ft.commit();

        try {
            openDatabase();
//            insertSomeDbData();
            useRawQueryShowAll();
//            dropTable();
        } catch(Exception e) {
            Log.e("Error", e.getMessage());
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
                Log.e("ERROR", "onStringFromMainToFrag" + e.getMessage());
            }
        }
    }

    private void openDatabase() {
        try{
            File storagePath= getApplication().getFilesDir();
            String myDbPath= storagePath+ "/"+ "StudentManagement";
            db = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        } catch(SQLiteException e) {
            Log.e("Error", e.getMessage());
            finish();
        }
    }

    private void insertSomeDbData() {
        db.beginTransaction();
        try{
            db.execSQL("create table Class(id integer PRIMARY KEY autoincrement, name text);");
            db.execSQL("create table Student(id text PRIMARY KEY, name text, classID int, FOREIGN KEY (classID) REFERENCES Class(id));");
            db.setTransactionSuccessful();
        } catch(SQLException e1) {
            Log.e("Error", e1.getMessage());
            finish();
        } finally{
            db.endTransaction();
        }
        db.beginTransaction();
            try{
            db.execSQL("insert into Class(id, name)" + "values ('2', '20CTT2');");
            db.execSQL("insert into Class(id, name)" + "values ('3', '20CTT3');");

            db.execSQL("insert into Student(id, name, classID)" + "values ('20120219', 'Nguyễn Minh Trí', '2');");
            db.execSQL("insert into Student(id, name, classID)" + "values ('20120294', 'Lê Công Hửu', '2');");
            db.execSQL("insert into Student(id, name, classID)" + "values ('20120312', 'Lê Tấn Kiệt', '2');");
            db.execSQL("insert into Student(id, name, classID)" + "values ('20120325', 'Ngô Thanh Lực', '2');");
            db.execSQL("insert into Student(id, name, classID)" + "values ('20120405', 'Nguyễn Long Vũ', '3');");

            db.setTransactionSuccessful();
        } catch(SQLiteException e2) {
            Log.e("Error", e2.getMessage());
        } finally{
            db.endTransaction();
        }
    }

    private void useRawQueryShowAll() {
        try{
            Cursor cursor = db.rawQuery("Select s.id, s.name, c.name from Student s Join Class c on s.classID = c.id ", null);
            cursor.moveToPosition(-1);
            while(cursor.moveToNext()) {
                People p = new People(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                peoples.add(p);
            }
        } catch(Exception e) {
            Log.e("Query Error", e.getMessage());
        }
    }
    private void dropTable() {
        try{
            db.execSQL("drop table Student;");
            db.execSQL("drop table Class;");
        } catch(Exception e) {
            finish();
        }
    }

}