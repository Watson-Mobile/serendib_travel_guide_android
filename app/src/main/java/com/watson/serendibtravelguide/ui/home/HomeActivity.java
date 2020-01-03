package com.watson.serendibtravelguide.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNavigationView(mContext, bottomNavigationViewEx);
    }
}
