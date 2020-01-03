package com.watson.serendibtravelguide.ui.Utils;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.dashboard.DashboardFragment;
import com.watson.serendibtravelguide.ui.home.HomeActivity;
import com.watson.serendibtravelguide.ui.home.HomeFragment;
import com.watson.serendibtravelguide.ui.places.DetailedDestination;

public class BottomNavigationViewHelper extends AppCompatActivity {
    private static final String TAG = "BottomNavigationViewHel";


    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView) {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");
//        bottomNavigationView.setItemHorizontalTranslationEnabled(true);


//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.nav_home:
//                                //launch home activity
//                                break;
//                            case R.id.nav_search:
//                                    // search activity
//                                break;
//                            case R.id.nav_notifications:
//                                //add activity
//
//                                break;
//                        }
//                        return true;
//                    }
//
//                });
    }


    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){

                    case R.id.nav_home:
                        Log.d(TAG, "Case Home Bottom");
//                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
//                        context.startActivity(intent1);
                        fragment = new HomeFragment();
//                        loadFragment(fragment);
                        break;

                    case R.id.nav_search:
                        Log.d(TAG, "Case Search Bottom");
                        fragment = new DashboardFragment();
//                        loadFragment(fragment);
                        break;

                    case R.id.nav_notifications:
                        Log.d(TAG, "Case Notification Bottom");
//                        Intent intent3 = new Intent(context, ShareActivity.class);//ACTIVITY_NUM = 2
//                        context.startActivity(intent3);
                        break;

                    case R.id.nav_add_place:
                        Log.d(TAG, "Case Add Bottom");
//                        Intent intent4 = new Intent(context, LikesActivity.class);//ACTIVITY_NUM = 3
//                        context.startActivity(intent4);
                        break;
                }


                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.relLayout2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
