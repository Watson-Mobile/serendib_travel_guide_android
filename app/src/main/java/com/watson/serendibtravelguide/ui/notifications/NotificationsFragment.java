package com.watson.serendibtravelguide.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.config.Config;
import com.watson.serendibtravelguide.data.LoginDataSource;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceAddResponse;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.ui.Utils.LocationHandler;
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
    private static double latitude;
    private static double longitude;

    private static final String TAG = "Notification";
    private static Retrofit retrofit = null;
    private String userId;
    private static List<Place> notificationList = new ArrayList<>();
    private List<NotificationsViewModel> notificationViewList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static RecycleViewAdapterNotification notificationAdapter;
    private TextView notificationResultsTextView;

    public NotificationsFragment(String userId) {
        this.userId = userId;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        longitude = LocationHandler.currentLocation.longitude();
        latitude = LocationHandler.currentLocation.latitude();

        if (LoginDataSource.loggedUser.getGuide_locations() != null) {
            longitude = LoginDataSource.loggedUser.getGuide_locations().longitude();
            latitude = LoginDataSource.loggedUser.getGuide_locations().latitude();
            Log.d("Notification", "GPS changed");
        } else {
            Log.d("Notification", "logged user not set properly");
        }

        Log.d("Notification",longitude+" "+latitude);


        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "From Notification Fragment (" + longitude + " " + latitude + ")");

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        Log.d("Notification", LoginDataSource.loggedUser.getGuide_locations() + ",----locations");

        notificationResultsTextView = root.findViewById(R.id.notification_title);
        recyclerView = root.findViewById(R.id.notification_list);
        this.queryNotVerifiedPlaces();
        notificationAdapter = new RecycleViewAdapterNotification(notificationViewList, this.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationAdapter);

        return root;
    }

    public void queryNotVerifiedPlaces() {
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        Call<PlaceResponse> notificationCall = placeApiService.getNotVerifiedPlaces(
                Double.toString(longitude), Double.toString(latitude)
        );
        if(LoginDataSource.loggedUser.getUserType().equals("Local_Assistent")){
            notificationCall.enqueue(new Callback<PlaceResponse>() {
                @Override
                public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                    List<Place> notVerifiedPlaces = response.body().getData();
                    Log.d("notification", response.body().getMessage());

                    for (Place place : notVerifiedPlaces) {
                        notificationList.add(place);
                        String secondaryTitle = "";
                        for (String name : place.getOtherNames()) {
                            secondaryTitle = secondaryTitle.concat(name).concat(" ");
                        }

                        String notificationMessage = "User has posed a new travel destination(" + place.getName() +
                                ") in your area. Please verify if you now this place.";

                        NotificationsViewModel searchViewModel = new NotificationsViewModel(
                                notificationMessage,
                                "",
                                place.getType().get(0),
                                place.getImagePaths().get(0),
                                place.getId()
                        );
                        notificationViewList.add(searchViewModel);

                    }
                    if (notificationViewList.isEmpty()) {
                        notificationResultsTextView.setText("Notifications not found");
                        Log.d(TAG, "No results");
                    } else {
                        Log.d(TAG, "Notifications found");
                        notificationResultsTextView.setText("Notifications");
                    }

                    notificationAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<PlaceResponse> call, Throwable t) {

                }
            });
        }else{
            notificationResultsTextView.setText("Notifications not found");
        }
    }

    public static void verifyPlace(String placeId, int position) {
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);
        Call<PlaceAddResponse> notificationVerifyCall = placeApiService.verifyPlace(placeId);
        notificationVerifyCall.enqueue(new Callback<PlaceAddResponse>() {
            @Override
            public void onResponse(Call<PlaceAddResponse> call, Response<PlaceAddResponse> response) {
                Place updatedPlace = response.body().getData();
                if (updatedPlace != null) {
                    Log.d("Notification", "Place verified...");
                    int listSize = notificationList.size();
                    for (int i = 0; i <= listSize; i++) {
                       if( notificationList.get(i).getId().equals(updatedPlace.getId())){
                           notificationList.remove(i);
                           notificationAdapter.notifyDataSetChanged();
                           Log.d("Notification",notificationList.get(i).getId()+" removed");
                           break;
                       }
                    }

                } else {
                    Log.d("Notification", "Place verification place...");
                }

            }

            @Override
            public void onFailure(Call<PlaceAddResponse> call, Throwable t) {

            }

        });
    }
}