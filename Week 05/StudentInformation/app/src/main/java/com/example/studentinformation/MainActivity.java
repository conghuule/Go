package com.example.studentinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

//    String[] ids = {"20120001","20120002","20120003","20120004","20120005"};
//            String[] names = {"Nguyễn Minh Trí", "Lê Công Hửu", "Lê Tấn Kiệt", "Ngô Thanh Lực", "Nguyễn Long Vũ"};
//            String[] class_names = {"20CTT2","20CTT2","20CTT2","20CTT2","20CTT3"};
//            Float[] avg_points = {Float.valueOf(10), Float.valueOf(10), Float.valueOf(10), Float.valueOf(10), Float.valueOf(9)};
//
//            Integer[] avatars = {R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5};
//            People[] peoples = {
//            new People(avatars[0],ids[0], names[0], class_names[0], avg_points[0]),
//            new People(avatars[1],ids[1], names[1], class_names[1], avg_points[2]),
//            new People(avatars[2],ids[2], names[2], class_names[2], avg_points[2]),
//            new People(avatars[3],ids[3], names[3], class_names[3], avg_points[3]),
//            new People(avatars[4],ids[4], names[4], class_names[4], avg_points[4]),
//            };


public class MainActivity extends FragmentActivity implements MainCallbacks {
    FragmentTransaction ft;
    ListFragment listFragment;
    DetailFragment detailFragment;


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
    }

    @Override
    public void onMsgFromFragToMain(String sender, People people) {
        if (sender.equals("DETAIL")){}
        if (sender.equals("LIST")) {
            detailFragment.onMsgFromMainToFragment(people);
        }
    }
}