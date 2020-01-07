package com.watson.serendibtravelguide.data;

import android.os.StrictMode;
import android.util.Log;

import com.watson.serendibtravelguide.BuildConfig;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceAddResponse;
import com.watson.serendibtravelguide.models.PlaceResponse;
import com.watson.serendibtravelguide.rest.PlaceApiService;
import com.watson.serendibtravelguide.rest.UserApiService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PlaceDataSource {

    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;
    public Place addedPlace;

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;


    public Result<Place> addPlace(Place newPlace) throws IOException {
        connectAndGetApiDataAWS(newPlace);
        Log.i(TAG, "post submitted to API."+addedPlace.getDescription());

        /*
         * completable future implementation
         * */

        if(addedPlace==null){
            return new Result.Error(new Exception("Error in login"));
        }else{
            return new Result.Success<> (addedPlace);
        }

    }


    public void connectAndGetApiDataAWS(Place place) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        PlaceApiService placeApiService = retrofit.create(PlaceApiService.class);

        Call<PlaceAddResponse> call = placeApiService.savePlace(place);

        addedPlace = call.execute().body().getData();



    }


}
