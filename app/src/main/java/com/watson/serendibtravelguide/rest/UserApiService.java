package com.watson.serendibtravelguide.rest;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call<UserResponse> saveUser(@Body User user);



}
