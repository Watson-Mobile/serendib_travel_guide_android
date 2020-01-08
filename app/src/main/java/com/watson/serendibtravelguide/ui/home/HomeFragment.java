package com.watson.serendibtravelguide.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.BuildConfig;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.models.MovieItem;
import com.watson.serendibtravelguide.models.MovieResponse;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.MovieApiService;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.ui.Utils.LocationHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_DISTANCE;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_TIME;
import static com.watson.serendibtravelguide.ui.Utils.LocationHandler.currentLocation;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;


    private List<CardViewModel> placeList;
    private static List<Place> placeList1;


    private LocationManager mLocationManager;
//    private Point currentLocation;

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(final Location location) {
//            //your code here
////            String longitude = "Longitude: " + location.getLongitude();
////            String latitude = "Latitude: " + location.getLatitude();
////            String s = longitude + "\n" + latitude;
////            Log.d(TAG, "location String : " + s);
//
//            currentLocation = Point.fromLngLat(location.getLatitude(), location.getLongitude());
//            Log.d(TAG, "Current Point : " + currentLocation);
//            connectAndGetApiDataAWS(false, currentLocation.latitude(), currentLocation.longitude());
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String s) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String s) {
//
//        }
//    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        currentLocation = Point.fromLngLat(79.899963, 6.797072);
//        currentLocation = HomeActivity.getLocationFromIntent();
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.item_chip_distance);
//        currentLocation = LocationHandler.getCurrentLocation();
        Log.d(TAG,"Centralized Location"+ currentLocation.toJson());

        placeList = new ArrayList<>();
        placeList1 = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerView_home);
        recyclerViewAdapter = new RecyclerViewAdapter(placeList, this.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        connectAndGetApiDataAWS(true, currentLocation.latitude(), currentLocation.longitude());


//        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//
//        if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this.getActivity(),
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//        if (this.getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this.getActivity(),
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//        }
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE, mLocationListener);

        return root;
    }


    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<MovieItem> movies = response.body().getResults();
//                placeList = response.body().getResults();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiDataAWS(boolean isFirstTime, double latitude, double longitude) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        List<Place> placesOut = new ArrayList<>();
        Call<PlaceResponse> call;

        if (isFirstTime) {
            call = placeApiService.getAllPlaces();

        } else
            placeList.clear();
        call = placeApiService.getByLocation(Double.toString(longitude), Double.toString(latitude));


        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                if (response.body() != null) {
                    List<Place> places = response.body().getData();
//                places = response.body().getData();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                    Log.d(TAG, "Number of movies received: " + places.size());
                    Log.d("message", "Incoming:" + response.body().getMessage());

                    for (Place place : places) {
                        double distanceFromCurrentPlace = calculateDistance(currentLocation, place.getLocation());
                        String distanceString = "";
                        if(distanceFromCurrentPlace<1000){
                            distanceString=Math.round(distanceFromCurrentPlace * 100) / 100+"m";
                        }else{
                            distanceString=Math.round(distanceFromCurrentPlace / 10) / 100+"km";
                        }
                        Log.d(TAG, distanceFromCurrentPlace + "km <-----distance");
                        placeList.add(new CardViewModel(place.getName(), place.getImagePaths().get(0), distanceString,
                                place.getType().get(0)));
                    }

                    for (Place place : places) {
                        placeList1.add(place);
                    }

                    recyclerViewAdapter.notifyDataSetChanged();

                }else{
                    Log.d(TAG, "Empty Response: " + response);
                }

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


    public static List<Place> getPlaceList1() {
        return placeList1;
    }

    public static double calculateDistance(Point currentLocation, Point placeGPSLocation) {
        double latitude1 = currentLocation.latitude();
        double longitude1 = currentLocation.longitude();
        double latitude2 = placeGPSLocation.latitude();
        double longitude2 = placeGPSLocation.longitude();

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        double distanceToPlace = Math.sqrt(distance);
//        return Math.round(distanceToPlace * 100) / 100;
        return distance;
    }
}