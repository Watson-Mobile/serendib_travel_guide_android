package com.watson.serendibtravelguide.rest;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
//import retrofit2.http.Path;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.models.PlaceAddResponse;
import com.watson.serendibtravelguide.models.PlaceResponse;

public interface PlaceApiService {

    //SAMPLE ENDPOINT https://api.themoviedb.org/3/movie/top_rated
    @GET("place")
    Call<PlaceResponse> getByLocation(@Query("longitude") String longitude, @Query("latitude") String latitude);

    @GET("all_place")
    Call<PlaceResponse> getAllPlaces();

    @GET("search_place")
    Call<PlaceResponse> searchPlaces(@Query("query") String query);

    @POST("place")
    @FormUrlEncoded
    Call<PlaceResponse> savePlace(@Body Place place);

    @POST("place")
    @FormUrlEncoded
    @Multipart
    Call<PlaceAddResponse> savePlaceWithImage(@Part MultipartBody.Part file , @Body Place place);

    @GET("not_verified_place")
    Call<PlaceResponse> getNotVerifiedPlaces(@Query("longitude") String longitude, @Query("latitude") String latitude);



}
