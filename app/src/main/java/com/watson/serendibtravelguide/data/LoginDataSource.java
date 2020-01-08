package com.watson.serendibtravelguide.data;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.watson.serendibtravelguide.BuildConfig;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.rest.UserApiService;
import com.watson.serendibtravelguide.ui.userlogin.LoginActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

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

    public static User loggedUser ;

    private final static String API_KEY = BuildConfig.CONSUMER_KEY;


    public Result<User> login(String email, String password) throws IOException {
       connectAndGetApiDataAWS(email, password);


       if(LoginDataSource.loggedUser==null){

           return new Result.Error(new Exception("Error in login"));
       }else{
           Log.i(TAG, "post submitted to API."+loggedUser.getFirstname());
           return new Result.Success<> (loggedUser);

       }

    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void connectAndGetApiDataAWS(String email, String password) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

        Call<UserResponse> call = userApiService.getUserByEmailAndPassword(email, password);
        try {
            LoginDataSource.loggedUser = call.execute().body().getData();
        }catch (NullPointerException e){
            Log.e(TAG, "Login Failed"+e);
        }



    }



}
