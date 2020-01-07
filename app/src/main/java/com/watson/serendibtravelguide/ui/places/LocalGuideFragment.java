package com.watson.serendibtravelguide.ui.places;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.config.Config;
import com.watson.serendibtravelguide.data.model.GuideResponse;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.dummy.DummyContent;
import com.watson.serendibtravelguide.dummy.DummyContent.DummyItem;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.rest.UserApiService;
import com.watson.serendibtravelguide.ui.home.CardViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class LocalGuideFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static Retrofit retrofit = null;
    private MyLocalGuideRecyclerViewAdapter recyclerViewAdapter;


    List<User> placesOut;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocalGuideFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static LocalGuideFragment newInstance(int columnCount) {
        LocalGuideFragment fragment = new LocalGuideFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localguide_list, container, false);

        placesOut = new ArrayList<>();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerViewAdapter = new MyLocalGuideRecyclerViewAdapter(placesOut, mListener);
            recyclerView.setAdapter(recyclerViewAdapter);
        }

        connectAndGetApiDataAWS();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Object item);
    }

    public void connectAndGetApiDataAWS() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.serverIp)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);



        Call<GuideResponse> call = userApiService.getGuidesFromCoordinates(
                "79.84778","6.93194"
//                String.valueOf(DetailedDestination.located_point.longitude()) ,
//                String.valueOf(DetailedDestination.located_point.latitude())
        );
        call.enqueue(new Callback<GuideResponse>() {
            @Override
            public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                List<User> guides = response.body().getData();
//                places = response.body().getData();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                Log.d(TAG, "Number of Guides received: " + guides.size());
                Log.d("message", "Incoming:" + response.body().getMessage());


                for (User user : guides) {
                    placesOut.add(user);
                }

                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GuideResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }


}
