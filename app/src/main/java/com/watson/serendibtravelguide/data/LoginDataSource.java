package com.watson.serendibtravelguide.data;

import android.util.Log;
import android.widget.Toast;

import com.watson.serendibtravelguide.BuildConfig;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.rest.UserApiService;
import com.watson.serendibtravelguide.ui.userlogin.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import org.riversun.promise.Promise;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginDataSource {


    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;

    public static User loggedUser = new User();

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;
//    public Result<User> login(String email, String password){
//       User user = connectAndGetApiDataAWS(email, password);
//       if(user==null){
//           return new Result.Error(new Exception("Error register in"));
//       }else{
//           return new Result.Success<> (loggedUser);
//       }

//    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void connectAndGetApiDataAWS(String email, String password) {
        final User[] retrievedUser = new User[1];
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

//        Call<UserResponse> call = userApiService.getUserByEmailAndPassword(email, password);
//
//        call.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//
//                if(response.isSuccessful()) {
//                    retrievedUser[0] = response.body().getData();
//                    Log.i(TAG, "post submitted to API." + retrievedUser[0].getUsername() + "----"+retrievedUser[0].getLastname()+"----"+retrievedUser[0].getEmail());
//                    Log.i(TAG, "post submitted to API.");
//
//                }
//
//                loggedUser.setFirstname(retrievedUser[0].getFirstname());
//                loggedUser.setLastname(retrievedUser[0].getLastname());
//                loggedUser.setLastname(retrievedUser[0].getUsername());
//                loggedUser.setEmail(retrievedUser[0].getEmail());
//                loggedUser.setNic_num(retrievedUser[0].getNic_num());
//                loggedUser.setTelephone_number(retrievedUser[0].getTelephone_number());
//                loggedUser.setGuide_locations(retrievedUser[0].getGuide_locations());
//                loggedUser.setUserType(retrievedUser[0].getUserType());
//                loggedUser.setPassword(retrievedUser[0].getPassword());
//
//
//            }
//
//
//
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable throwable) {
//                Log.e(TAG, throwable.toString());
//
//            }
//        });
//
//        return retrievedUser[0];
//
    }



}
