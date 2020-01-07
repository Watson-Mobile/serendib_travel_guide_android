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

    private static final String TAG = "SearchListFragment";
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
        SharedPreferences userPrefs = this.getActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        Log.d("userpref", userPrefs.getString("name", "") + "=========");
        Log.d("lllllll", "pppppppppppppp");
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "From search fragment, query received: " + userId);
        this.queryNotVerifiedPlaces(userId);


        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.notification_title);

        Log.d("Notification", LoginDataSource.loggedUser.getGuide_locations() + ",----locations");

        longitude = 79.899963;
        latitude = 6.797072;
        latitude = LoginDataSource.loggedUser.getGuide_locations().latitude();

//        Log.d("Nofification", "latitude: " + latitude);
//        Log.d("Notification", "longitude: " + longitude);

//        notificationsViewModel.getText().observe(this, new Observer<String>() {
        notificationResultsTextView = root.findViewById(R.id.notification_title);
        recyclerView = root.findViewById(R.id.notification_list);
        notificationAdapter = new RecycleViewAdapterNotification(notificationViewList, this.getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationAdapter);

        return root;
    }

    public void queryNotVerifiedPlaces(String query) {
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        Call<PlaceResponse> notificationCall = placeApiService.getNotVerifiedPlaces(
//                Double.toString(longitude), Double.toString(latitude)
                "79.899","6.7969"
        );

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
                    notificationResultsTextView.setText("No results found for \'" + query + "\'");
                    Log.d(TAG, "No results");
                } else {
                    Log.d(TAG, "Search results found");
                    notificationResultsTextView.setText("Search Results...");
                }

                notificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {

            }
        });
    }

    public static void verifyPlace(String placeId,int position) {
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);
        Call<PlaceAddResponse> notificationVerifyCall = placeApiService.verifyPlace(placeId);
        notificationVerifyCall.enqueue(new Callback<PlaceAddResponse>() {
            @Override
            public void onResponse(Call<PlaceAddResponse> call, Response<PlaceAddResponse> response) {
                Place updatedPlace = response.body().getData();
                if(updatedPlace!=null){
                    Log.d("Notification","Place verified...");
                    notificationList.remove(notificationList.indexOf(updatedPlace));
//                    notificationList.remove(position);
                    notificationAdapter.notifyDataSetChanged();
                }else{
                    Log.d("Notification","Place verification place...");
                }

            }

            @Override
            public void onFailure(Call<PlaceAddResponse> call, Throwable t) {

            }

        });
    }
}