package com.watson.serendibtravelguide.rest;

import retrofit2.Call;
import retrofit2.http.GET;
//import retrofit2.http.Path;
import retrofit2.http.Query;

import com.watson.serendibtravelguide.models.PlaceResponse;

public interface PlaceApiService {

    //SAMPLE ENDPOINT https://api.themoviedb.org/3/movie/top_rated
    @GET("place")
    Call<PlaceResponse> getByLocation(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @GET("all_place")
    Call<PlaceResponse> getAllPlaces();

}
