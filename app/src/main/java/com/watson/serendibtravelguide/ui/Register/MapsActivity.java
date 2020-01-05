package com.watson.serendibtravelguide.ui.Register;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.watson.serendibtravelguide.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteFragment;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutocompleteFragment placeAutoComplete;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String NICNumber;
    private ArrayList<String> telNumber;
    private String password;
    private double lat;
    private double longi;
    private String place_name;
    private SupportMapFragment mapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Intent currentIntent = getIntent();
        firstname = currentIntent.getStringExtra("firstname");
        lastname = currentIntent.getStringExtra("lastname");
        username = currentIntent.getStringExtra("username");
        email = currentIntent.getStringExtra("email");
        NICNumber = currentIntent.getStringExtra("NICNumber");
        telNumber = currentIntent.getStringArrayListExtra("telNumber");
        password = currentIntent.getStringExtra("password");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDflkvLylyshUs_Qs-7U_9gvWbqgWReijE");
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete);

        autocompleteFragment.setPlaceFields(Arrays.asList( Place.Field.LAT_LNG, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getLatLng());
                if(place != null){
                    lat = Objects.requireNonNull(place.getLatLng()).latitude;
                    longi = place.getLatLng().latitude;
                    place_name = place.getName();
              }


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });




    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng new_place = new LatLng(lat,longi);
        mMap.addMarker(new MarkerOptions().position(new_place).title(place_name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new_place));


    }
}
