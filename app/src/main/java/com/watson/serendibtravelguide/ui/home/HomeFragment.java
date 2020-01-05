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


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;

    private List<CardViewModel> movieList;
    private static List<Place> movieList1;


    private LocationManager mLocationManager;
    private Point currentLocation;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            Toast.makeText(getContext(), "OnLocationChanged", Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + location.getLongitude();
            String latitude = "Latitude: " + location.getLatitude();
            String s = longitude + "\n" + latitude;
            Log.d(TAG, "location String : "+s);

            currentLocation = Point.fromLngLat(location.getLatitude(),location.getLongitude());
            Log.d(TAG, "Current Point : "+currentLocation);
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.card_text_distance);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                cardView.setText(s);
//            }
//        });

        movieList = new ArrayList<>();
        movieList1 = new ArrayList<>();
        recyclerView = root.findViewById(R.id.recyclerView_home);
        recyclerViewAdapter = new RecyclerViewAdapter(movieList,this.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        connectAndGetApiDataAWS();


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
//                movieList = response.body().getResults();
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
    public void connectAndGetApiDataAWS() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        List<Place> placesOut = new ArrayList<>();

        Call<PlaceResponse> call = placeApiService.getAllPlaces();
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                List<Place> places = response.body().getData();
//                places = response.body().getData();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                Log.d(TAG, "Number of movies received: " + places.size());
                Log.d("message","Incoming:" + response.body().getMessage());

                for (Place place: places) {
                    movieList.add(new CardViewModel(place.getName(), place.getImagePaths().get(0),"12",
                            place.getType().get(0)));
                }

                for (Place place: places) {
                    movieList1.add(place);
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


    public static List<Place> getMovieList1() {
        return movieList1;
    }
}