package com.watson.serendibtravelguide.rest;

import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.data.model.GuideResponse;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserRegisterResponse;
import com.watson.serendibtravelguide.data.model.UserResponse;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    @POST("user/login")
    @FormUrlEncoded
    Call<UserResponse> getUserByEmailAndPassword(@Field("email") String email, @Field("password") String password);


    @GET("all_user")
    Call<UserResponse> getAllUsers();

    @POST("user")
    @FormUrlEncoded
    Call<UserRegisterResponse> saveUser(
            @Field("telephone_number") String[] telephone_number,
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("username") String username,
            @Field("email") String email,
            @Field("userType") String userType,
            @Field("password") String password,
            @Field("guide_location")String[] guide_location,
            @Field("nic_num")String nic_num);


//    @POST("user")
//    @Headers("Content-type: application/json")
//    Call<UserResponse> saveUser();
    @GET("all_place")
    Call<UserResponse> getMockLoginSuccess();

    @GET("user/local_guides")
    Call<GuideResponse> getGuidesFromCoordinates(@Query("longitude") String longitude, @Query("latitude") String latitude);



}
