package com.example.studentinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements MainCallbacks {
    FragmentTransaction ft;
    ListFragment listFragment;
    DetailFragment detailFragment;
    People[] peoples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ft = getSupportFragmentManager().beginTransaction();
        listFragment = ListFragment.newInstance("list");
        ft.replace(R.id.list_fragment, listFragment);
        ft.commit();

        ft = getSupportFragmentManager().beginTransaction();
        detailFragment = DetailFragment.newInstance("detail");
        ft.replace(R.id.detail_fragment, detailFragment);
        ft.commit();

        peoples = listFragment.get_peoples();
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
}