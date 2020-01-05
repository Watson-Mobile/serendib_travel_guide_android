package com.watson.serendibtravelguide.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;
import com.watson.serendibtravelguide.ui.places.LocalGuideFragment;
import com.watson.serendibtravelguide.ui.search.SearchListFragment;

public class HomeActivity extends AppCompatActivity implements LocalGuideFragment.OnListFragmentInteractionListener {
    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;
    private Activity homeActivity = this;

    private MenuItem searchMenuItem;
    private SearchView searchView;
    private SearchListFragment searchListFragment;
    private HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeFragment homeFragment = new HomeFragment();
        BottomNavigationViewHelper.replaceFragment(this, homeFragment,R.id.relLayout2,false);
        setupBottomNavigationView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

    }

    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx, this);
    }

    @Override
    public void onListFragmentInteraction(Object dummy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view_menu, menu);

        searchMenuItem = menu.findItem(R.id.search_item);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "query submitted............");
                Log.d(TAG,query);
                searchListFragment = new SearchListFragment();
                BottomNavigationViewHelper.replaceFragment(homeActivity, searchListFragment,R.id.relLayout2,false);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
                } else {
                    Log.d(TAG, newText);
                }
                return false;
            }
        });


        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                Log.d(TAG, "Expanding menubar items.....");
                searchView.setQueryHint("Search Places");

                if(searchView.isIconified()){
                    Log.d(TAG, "Iconified...........");
                }

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                homeFragment = new HomeFragment();
                invalidateOptionsMenu();
                getSupportFragmentManager().beginTransaction().remove(searchListFragment).commit();
                BottomNavigationViewHelper.replaceFragment(homeActivity, homeFragment,R.id.relLayout2,false);
                Log.d(TAG, "Collapsing menubar......");
                return false;
            }
        });

        return true;
    }

}
