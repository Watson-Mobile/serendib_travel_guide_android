package com.watson.serendibtravelguide.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.config.Config;
import com.watson.serendibtravelguide.data.LoginDataSource;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.ui.search.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private double latitude;
    private double longitude;

    private static final String TAG = "SearchListFragment";
    private static Retrofit retrofit = null;
    private String userId;
    private List<Place> searchPlaceList = new ArrayList<>();
    private List<SearchViewModel> searchViewList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecycleViewAdapterNotification searchAdapter;
    private TextView searchResultsTextView;

    public NotificationsFragment(String userId) {
        this.userId = userId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences userPrefs = this.getActivity().getSharedPreferences("userPrefs",Context.MODE_PRIVATE);
        Log.d("userpref",userPrefs.getString("name","")+"=========");
        Log.d("lllllll","pppppppppppppp");
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "From search fragment, query received: " + userId);
        this.queryPlaces(userId);


        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.notification_title);

        Log.d("Notification",LoginDataSource.getLoggedUser().getGuideLocations()+",----locations");

//        longitude = LoginDataSource.getLoggedUser().getGuideLocations().coordinates().get(0).longitude();
//        latitude = LoginDataSource.getLoggedUser().getGuideLocations().coordinates().get(0).latitude();
//
//        Log.d("Nofification", "latitude: " + latitude);
//        Log.d("Notification", "longitude: " + longitude);

//        notificationsViewModel.getText().observe(this, new Observer<String>() {
        searchResultsTextView = root.findViewById(R.id.notification_title);
        recyclerView = root.findViewById(R.id.notification_list);
        searchAdapter = new RecycleViewAdapterNotification(searchViewList, this.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);

        return root;
    }

    public void queryPlaces(String query) {
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        Call<PlaceResponse> searchCall = placeApiService.searchPlaces(query);

        searchCall.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                List<Place> searchPlaces = response.body().getData();
                Log.d(TAG, response.body().getMessage());

                for (Place place : searchPlaces) {
                    searchPlaceList.add(place);
                    String secondaryTitle = "";
                    for (String name : place.getOtherNames()) {
                        secondaryTitle = secondaryTitle.concat(name).concat(" ");
                    }

                    SearchViewModel searchViewModel = new SearchViewModel(place.getName(), place.getType().get(0),
                            secondaryTitle, place.getImagePaths().get(0));
                    searchViewList.add(searchViewModel);

                }
                if (searchViewList.isEmpty()) {
                    searchResultsTextView.setText("No results found for \'" + query + "\'");
                    Log.d(TAG, "No results");
                } else {
                    Log.d(TAG, "Search results found");
                    searchResultsTextView.setText("Search Results...");
                }

                searchAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });
    }


}