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
    private float latitude;
    private float longitude;
    private SharedPreferences sharedPref;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPref = getContext().getSharedPreferences("serandib_travel_guide_user",Context.MODE_PRIVATE);
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
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
        final TextView textView = root.findViewById(R.id.text_notifications);

        longitude = sharedPref.getFloat("latitude",79);
        latitude = sharedPref.getFloat("longitude",6);

        Log.d("Nofification","latitude: "+latitude);
        Log.d("Notification","longitude: "+longitude);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
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
                if (searchViewList.isEmpty()){
                    searchResultsTextView.setText("No results found for \'"+query+"\'");
                    Log.d(TAG,"No results");
                }else{
                    Log.d(TAG,"Search results found");
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