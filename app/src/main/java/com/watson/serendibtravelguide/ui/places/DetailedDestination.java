package com.watson.serendibtravelguide.ui.places;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.FragmentNavigator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.dummy.DummyContent;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;

public class DetailedDestination extends AppCompatActivity implements LocalGuideFragment.OnListFragmentInteractionListener {

    private static final int ACTIVITY_NUM = 1;
    private static final String TAG = "DetailedPlaceActivity";
    private Context mContext = DetailedDestination.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_destination);

        TextView placeTitle = (TextView)findViewById(R.id.title_detailed);
        TextView description = (TextView)findViewById(R.id.txt_detailed_description);
        ImageView detailImage = (ImageView)findViewById(R.id.image_detailed);
        TextView findGuides = (TextView)findViewById(R.id.txt_link_guides);

        placeTitle.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));

//        Glide.with(this).load(getIntent().getIntExtra("image_resource",0))
//                .into(detailImage);

        findGuides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent localGuidesIntent = new Intent(DetailedDestination.this, LocalGuideFragment.class);
//
//                DetailedDestination.this.startActivity(localGuidesIntent);
//
//
                LocalGuideFragment fragment = new LocalGuideFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_detailed , fragment);
                transaction.commit();

//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.add(R.id.frame_detailed,manager.findFragmentById(R.id.list_local_guide),"list_fragment");
//                transaction.addToBackStack(null);
//                transaction.commit();
            }

        });

//        setupBottomNavigationView();

    }



    @Override
    public void onListFragmentInteraction(Object dummy){

    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationViewEx = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx,this);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
