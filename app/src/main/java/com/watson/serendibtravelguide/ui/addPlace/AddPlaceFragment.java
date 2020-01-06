package com.watson.serendibtravelguide.ui.addPlace;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//import com.google.android.gms.location.LocationListener;
import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;
import com.watson.serendibtravelguide.ui.home.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_DISTANCE;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_TIME;

public class AddPlaceFragment extends Fragment {

    private static final int pic_id = 123;
    private AddPlaceViewModel placeViewModel;
    private static final int TAG_CODE_PERMISSION_LOCATION = 678;
    private ImageView click_image_id;
    private LocationManager mLocationManager;
    private EditText sampleText;
    private double current_location_lat;
    private double current_location_long;



    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
//            Toast.makeText(getContext(), "OnLocationChanged", Toast.LENGTH_SHORT).show();   // Toast not showing
//            String longitude = "Longitude: " + location.getLongitude();
//            String latitude = "Latitude: " + location.getLatitude();
//            String s = longitude + "\n" + latitude;
//            Log.d(TAG, "location String : "+s);
//            sampleText.setHint(s);
            current_location_lat = location.getLatitude();
            current_location_long = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_place, container, false);

        final EditText placenameEditText = root.findViewById(R.id.PlaceName);
        final EditText descriptionEditText = root.findViewById(R.id.PlaceDescription);
        final EditText otherNamesEditText = root.findViewById(R.id.OtherName);
        final Spinner placeTypes = (Spinner) root.findViewById(R.id.placeType);
        final Button btn_add_place = (Button)root.findViewById(R.id.btn_add_place);
        final Button get_my_location = (Button)root.findViewById(R.id.btn_get_my_location);
        final EditText locationEditText_lat = root.findViewById(R.id.showLocation_lat);
        final EditText locationEditText_long = root.findViewById(R.id.showLocation_long);



        String[] items = new String[] {"Historical","Heritage" ,"Religious","Nature","Leisure","Adventure","Cultural","Wildlife","Other"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(),android.R.layout.simple_spinner_dropdown_item, items);

        placeTypes.setAdapter(adapter);

        final Button btn_take_photo = root.findViewById(R.id.btn_get_photo);
        click_image_id = (ImageView) root.findViewById(R.id.image_add_place);

        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);


        placeViewModel = ViewModelProviders.of(this, new PlaceViewModelFactory())
                .get(AddPlaceViewModel.class);

        placeViewModel.getPlaceFormState().observe(this, new Observer<PlaceFormState>() {
            @Override
            public void onChanged(@Nullable PlaceFormState placeFormState) {
                if (placeFormState == null) {
                    return;
                }
                btn_add_place.setEnabled(placeFormState.isDataValid());
                if (placeFormState.getPlacenameError() != null) {
                    placenameEditText.setError(getString(placeFormState.getPlacenameError()));
                }
                if (placeFormState.getDescriptionError() != null) {
                    descriptionEditText.setError(getString(placeFormState.getDescriptionError()));
                }
                if(placeFormState.getLocationError() != null){
                    locationEditText_lat.setError(getString(placeFormState.getLocationError()));
                }
                if(placeFormState.getPlaceTypeError() != null){

                }
            }
        });


        placeViewModel.getPlaceResult().observe(this, new Observer<PlaceResult>() {
            @Override
            public void onChanged(@Nullable PlaceResult placeResult) {
                if (placeResult == null) {
                    return;
                }

                if (placeResult.getError() != null) {
                    //showLoginFailed(loginResult.getError());
                }
                if (placeResult.getSuccess() != null) {
                   // updateUiWithUser(loginResult.getSuccess());
                }

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
//                placeViewModel.placeDataChanged(placenameEditText.getText().toString(), descriptionEditText.getText().toString(),
//                         location, String placetype);,

            }
        };

        placenameEditText.addTextChangedListener(afterTextChangedListener);
        descriptionEditText.addTextChangedListener(afterTextChangedListener);
        /*

        /*if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,
                MIN_DISTANCE, this);
         */


        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit data or take photograph
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });

        get_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationEditText_lat.setText("lat : "+String.valueOf(current_location_lat));
                locationEditText_long.setText("long : "+String.valueOf(current_location_long));
            }


        });

        btn_add_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit data or take photograph
                Place newPlace = new Place();
                newPlace.setName(placenameEditText.getText().toString());
                newPlace.setDescription(descriptionEditText.getText().toString());
                ArrayList<String> other_names = new ArrayList<>();
                if(otherNamesEditText.getText() != null){
                    other_names.add(otherNamesEditText.getText().toString());
                    newPlace.setOtherNames(other_names);
                }else{
                    newPlace.setOtherNames(null);
                }
                ArrayList<String> selectedPlaceTypes = new ArrayList<>();
                selectedPlaceTypes.add(placeTypes.getSelectedItem().toString());
                newPlace.setType(selectedPlaceTypes);
                newPlace.setLocation( Point.fromLngLat(current_location_long,current_location_lat));

                try {
                    if (placeViewModel.addPlace(newPlace)) {
                        AddPlaceFragment addPlaceFragment = new AddPlaceFragment();
                        BottomNavigationViewHelper.replaceFragment( getActivity(), addPlaceFragment,R.id.relLayout2,false);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {

            // BitMap is data structure of image file
            // which stor the image in memory
            Bitmap photo = (Bitmap)data.getExtras()
                    .get("data");

            // Set the image in imageview for display
            click_image_id.setImageBitmap(photo);
        }
    }
}
