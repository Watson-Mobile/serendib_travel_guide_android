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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.dummy.DummyContent;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;

import static com.watson.serendibtravelguide.config.Config.BASE_URL_IMG;

public class DetailedDestination extends AppCompatActivity implements
        LocalGuideFragment.OnListFragmentInteractionListener, OnMapReadyCallback {

    private static final int ACTIVITY_NUM = 1;
    private static final String TAG = "DetailedPlaceActivity";
    private Context mContext = DetailedDestination.this;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private Point located_point;
    private String title;

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
        located_point = (Point) getIntent().getSerializableExtra("location_point");
        title = getIntent().getStringExtra("title");
        Glide.with(mContext).load(BASE_URL_IMG+getIntent().getStringExtra("imagePath"))
                .apply(new RequestOptions().override(200, 300))
                .into(detailImage);


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
        initGoogleMap(savedInstanceState);

    }

    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mMapView = findViewById(R.id.mapView4);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions().position(
                new LatLng(0, 0)).title("Marker"));
        map.setMyLocationEnabled(true);
        map.addMarker(new MarkerOptions().position(
                new LatLng(located_point.longitude(), located_point.latitude())).title(title));
        map.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(located_point.longitude(), located_point.latitude())));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
