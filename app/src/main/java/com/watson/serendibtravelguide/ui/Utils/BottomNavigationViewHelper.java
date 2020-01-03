package com.watson.serendibtravelguide.ui.Utils;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.home.HomeActivity;
import com.watson.serendibtravelguide.ui.search.SearchActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BottomNavigationViewHelper {
 //   private static final String TAG = "BottomNavigationViewHelper";


    public static void setupBottomNavigationView(final Context context, BottomNavigationView bottomNavigationView) {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
//        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                //launch home activity
//                                Log.d(TAG, "Case Home");
//                                Intent intent1 = new Intent(context, HomeActivity.class);

//                                context.startActivity(intent1);
                                break;
                            case R.id.nav_search:
                                    // search activity
//                                Log.d(TAG, "Case Search Isuruuu");
//                                Intent intent = new Intent(context, SearchActivity.class);

//                                context.startActivity(intent);
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
