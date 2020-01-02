package com.watson.serendibtravelguide.ui.Utils;


import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";


    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView) {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                //launch home activity
                                break;
                            case R.id.nav_search:
                                    // search activity
                                break;
                            case R.id.nav_notifications:
                                //add activity

                                break;
                        }
                        return true;
                    }

                });
    }
}
