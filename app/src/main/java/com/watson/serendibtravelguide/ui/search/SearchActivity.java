package com.watson.serendibtravelguide.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.watson.serendibtravelguide.R;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchPlaces(query);
        }

        onSearchRequested();

    }

    private void searchPlaces(String query) {
        Log.d(TAG, "Querying.....");
        Log.d(TAG,query);
    }


    @Override
    public boolean onSearchRequested() {
        Log.d(TAG, "onSearchRequested.....");
        return super.onSearchRequested();
    }






}
