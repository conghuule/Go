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

    String[] ids = {"20120219", "20120294", "20120312", "20120325", "20120405"};
    String[] names = {"Nguyễn Minh Trí", "Lê Công Hửu", "Lê Tấn Kiệt", "Ngô Thanh Lực", "Nguyễn Long Vũ"};
    String[] class_names = {"20CTT2", "20CTT2", "20CTT2", "20CTT2", "20CTT3"};
    Float[] avg_points = {10F, 10F, 10F, 10F, 9.5F};

    Integer[] avatars = {R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5, R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5};
    People[] peoples = {
            new People(avatars[0], ids[0], names[0], class_names[0], avg_points[0]),
            new People(avatars[1], ids[1], names[1], class_names[1], avg_points[2]),
            new People(avatars[2], ids[2], names[2], class_names[2], avg_points[2]),
            new People(avatars[3], ids[3], names[3], class_names[3], avg_points[3]),
            new People(avatars[4], ids[4], names[4], class_names[4], avg_points[4]),
    };

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
}