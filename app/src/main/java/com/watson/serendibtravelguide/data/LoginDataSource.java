package com.watson.serendibtravelguide.data;

import android.util.Log;

import com.watson.serendibtravelguide.BuildConfig;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.rest.UserApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginDataSource {


    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;
    public Result<User> login(String email, String password) {

        try {

            connectAndGetApiDataAWS(email);
            // TODO: handle loggedInUser authentication
            ArrayList<String> guide_locations = new ArrayList<>();
            guide_locations.add("Matara");
            guide_locations.add("Dondra");
            User fakeUser =
                    new User(
                            java.util.UUID.randomUUID().toString(),
                            "Piyumi","Sudusinghe","piyumisudusinghe","piyumi@gmail.com","admin",Long.parseLong("0717872513"),"956731267v",guide_locations,"12345678");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void connectAndGetApiDataAWS(String email) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

        List<User> user = new ArrayList<>();


        Call<UserResponse> call = userApiService.getUserByEmail(email);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                List<User> user = response.body().getData();
//                places = response.body().getData();
//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                Log.d(TAG, "Number of users received: " + user.size());
                Log.d("USER_REQUEST","users output" + user);

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
