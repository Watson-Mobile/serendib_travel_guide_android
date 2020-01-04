package com.watson.serendibtravelguide.ui.Utils;


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
import com.watson.serendibtravelguide.ui.places.LocalGuideFragment;
import com.watson.serendibtravelguide.ui.search.SearchActivity;


public class BottomNavigationViewHelper {
    private static final String TAG = "NavViewHelper";
    private static FragmentTransaction transaction;


    private static FragmentTransaction getTransaction(Activity activity){
        return getFragmentManager(activity).beginTransaction();
    }

    private static FragmentManager getFragmentManager(Activity activity){
        return ((AppCompatActivity)activity).getSupportFragmentManager();
    }

    /**
     * Add Fragment to the given ID
     * @param activity
     * @param fragment
     * @param id
     * @param add_to_backstack
     */
    static void addFragment(Activity activity, Fragment fragment, int id, boolean add_to_backstack){
        transaction = getTransaction(activity);
        transaction.add(id,fragment,fragment.getClass().getName());
        if (add_to_backstack)
            transaction.addToBackStack(fragment.getClass().getName());
        transaction.commit();
    }

    static void replaceFragment(Activity activity, Fragment fragment, int id, boolean add_to_backstack) {
        Fragment check_Fragment = getFragmentManager(activity).findFragmentByTag(fragment.getClass().getName());
        if (check_Fragment == null) {
            transaction = getTransaction(activity)
                    .replace(id,fragment,fragment.getClass().getName());
            if (add_to_backstack)
                transaction.addToBackStack(fragment.getClass().getName());
            transaction.commit();
        }
        else{
            transaction = getTransaction(activity);
            transaction.replace(id,check_Fragment,check_Fragment.getClass().getName())
                    .addToBackStack(null)
                    .commit();
        }
    }


    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView, Activity activity) {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        Log.d(TAG, "Setting up BottomNavigationView");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                //launch home activity
                                Log.d(TAG, "Case Home");
                                HomeFragment homeFragment = new HomeFragment();
                                addFragment(activity, homeFragment,R.id.relLayout2,true);
//                                Intent intent1 = new Intent(context, HomeActivity.class);

//                                context.startActivity(intent1);
                                break;
                            case R.id.nav_search:
                                    // search activity
                                Log.d(TAG, "Case Search");
                                DashboardFragment dashboardFrag = new DashboardFragment();
                                replaceFragment(activity, dashboardFrag,R.id.relLayout2,true);


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
