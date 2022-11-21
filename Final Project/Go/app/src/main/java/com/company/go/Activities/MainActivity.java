package com.company.go.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.company.go.Fragments.Account;
import com.company.go.Fragments.Home;
import com.company.go.Fragments.Manage;
import com.company.go.Fragments.NearMe;
import com.company.go.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public Integer HOME = R.id.home;
    public Integer NEAR_ME = R.id.nearme;
    public Integer ACCOUNT = R.id.account;
    public Integer MANAGE = R.id.manage;
    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth auth;
    FragmentManager fm;
    FragmentTransaction ft;

    //unfocused input when click outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        switchFragment(new Home());

        auth = FirebaseAuth.getInstance();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

//        bottomNavigationView.setSelectedItemId(MANAGE);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(new Home());
                        break;
                    case R.id.nearme:
                        switchFragment(new NearMe());
                        break;
                    case R.id.account:
                        switchFragment(new Account());
                        break;
                    case R.id.manage:
                        switchFragment(new Manage());
                        break;
                    default:
                        return false;
                }

                return true;
            }
        });
    }

    public void switchFragment(Fragment newFragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.content, newFragment);
//        ft.addToBackStack(null);
        ft.commit();
//
//        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                // pop all the fragment and remove the listener
//                fm.popBackStack(FRAGMENT_OTHER, 1);
//                fm.removeOnBackStackChangedListener(this);
//                // set the home button selected
//                navigation.getMenu().getItem(0).setChecked(true);
//            }
//        });
    }
}