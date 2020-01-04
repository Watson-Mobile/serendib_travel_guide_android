package com.watson.serendibtravelguide.rest;

import com.watson.serendibtravelguide.data.model.UserResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("user")
    Call<UserResponse> getUserByEmail(@Query("email") String email);

    @GET("all_user")
    Call<UserResponse> getAllUsers();

}
