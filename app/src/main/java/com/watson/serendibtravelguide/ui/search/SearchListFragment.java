package com.watson.serendibtravelguide.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.config.Config;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchListFragment extends Fragment {
    private static final String TAG = "SearchListFragment";
    private static Retrofit retrofit = null;
    private String query;
    public static List<Place> searchPlaceList = new ArrayList<>();
    private List<SearchViewModel> searchViewList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecycleViewAdapterSearch searchAdapter;
    private TextView searchResultsTextView;


    public SearchListFragment(String query) {
        this.query = query;
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "From search fragment, query received: " + query);
        this.queryPlaces(query);


        View root = inflater.inflate(R.layout.fragment_search_list, container, false);
        searchResultsTextView = root.findViewById(R.id.search_results_title);
        recyclerView = root.findViewById(R.id.search_result_list);
        searchAdapter = new RecycleViewAdapterSearch(searchViewList, this.getContext());
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
