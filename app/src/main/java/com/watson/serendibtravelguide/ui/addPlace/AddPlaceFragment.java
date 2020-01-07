package com.watson.serendibtravelguide.ui.addPlace;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//import com.google.android.gms.location.LocationListener;
import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.config.Config;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceAddResponse;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.ui.Utils.BottomNavigationViewHelper;
import com.watson.serendibtravelguide.ui.home.HomeActivity;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.watson.serendibtravelguide.BuildConfig.DEBUG;
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
    private static Retrofit retrofit = null;
    String currentPhotoPath;
    Uri photoURI;



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

        if (this.getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (this.getActivity().shouldShowRequestPermissionRationale(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                this.getActivity().requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        678);
            }
        }

        if (this.getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    987);



            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant

//            return;
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


        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(v.getContext(),
                                "com.watson.serendibtravelguide.provider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, pic_id);
                    }
                }


//                try {
//                    photoURI = FileProvider.getUriForFile(v.getContext(),
//                            "com.watson.serendibtravelguide.provider",
//                            createImageFile());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                //take photograph
//                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
//                startActivityForResult(camera_intent, pic_id);
            }
        });

        get_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationEditText_lat.setText(String.valueOf(current_location_lat));
                locationEditText_long.setText(String.valueOf(current_location_long));
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
                    connectAndGetApiDataAWS(newPlace);
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                try {
//                    if (placeViewModel.addPlace(newPlace)) {
//                        AddPlaceFragment addPlaceFragment = new AddPlaceFragment();
//                        BottomNavigationViewHelper.replaceFragment( getActivity(), addPlaceFragment,R.id.relLayout2,false);
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });

        return root;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Match the request 'pic id with requestCode

        if (requestCode == pic_id && resultCode == RESULT_OK) {
            //Perform any task using uri
            //For example set this URI to fill an ImageView like below
            this.click_image_id.setImageURI(photoURI);
        }

//        if (requestCode == pic_id) {
//
//            // BitMap is data structure of image file
//            // which store the image in memory
//            Bitmap photo = (Bitmap)data.getExtras().get("data");
//
//            // Set the image in imageview for display
//            click_image_id.setImageBitmap(photo);
//        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void connectAndGetApiDataAWS(Place newPlace) throws IOException {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        //Create a file object using file path
        File file1 = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                +photoURI.getPath().substring(19));

        File file = new Compressor(this.getContext())
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .compressToFile(file1);

//        File file = createImageFile();
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), newPlace.getName());



//        List<Place> placesOut = new ArrayList<>();
//        newPlace.getLocation().coordinates().toArray();

        Call<PlaceAddResponse> call = placeApiService.uploadImage(part, newPlace.getName());


//        Call<PlaceAddResponse> call = placeApiService.savePlaceSeperate(
//                newPlace.getName(),
//                new String[]{"45.55","45.25"},
//                newPlace.getDescription(),
//                new String[]{"nature"},
//                newPlace.getId(),"mockpath/storage");
        call.enqueue(new Callback<PlaceAddResponse>() {
            @Override
            public void onResponse(Call<PlaceAddResponse> call, Response<PlaceAddResponse> response) {
                try {
                    Log.d(TAG, "Place response received Manual: " + response.toString());
                    Place place = response.body().getData();

                    Log.d(TAG, "Place responce received: " + place.getName());
                    Log.d("message", "Incoming:" + response.body().getMessage());

                }catch (NullPointerException e){
                    Log.d(TAG, e.getMessage());
                }

//                for (Fragment fragment : getFragmentManager().getFragments()) {
//                    getFragmentManager().beginTransaction().remove(fragment).commit();
//                }

                AddPlaceFragment addPlaceFragment = new AddPlaceFragment();
                BottomNavigationViewHelper.replaceFragment( getActivity(), addPlaceFragment,R.id.relLayout2,false);

            }

            @Override
            public void onFailure(Call<PlaceAddResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }



}
