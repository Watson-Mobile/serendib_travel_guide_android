package com.watson.serendibtravelguide.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;

    private ListView searchListView;
    ArrayAdapter<String> adapter;
    String[] test_search_results = new String[]{"Sigiriya","Sigiriya","Sigiriya"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNavigationView(mContext, bottomNavigationViewEx);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater().inflate( R.menu.search_view_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.search_item);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setQueryHint("Search Places");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "query submitted............");
                Log.d(TAG,query);
                searchListView =(ListView) findViewById(R.id.search_results_list);
                adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,test_search_results);
                searchListView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
                   // searchListView.clearTextFilter();
                } else {
                    Log.d(TAG, newText);
                    //searchListView.setFilterText(newText);
                }
                return true;
            }
        });

        return true;
    }
}
