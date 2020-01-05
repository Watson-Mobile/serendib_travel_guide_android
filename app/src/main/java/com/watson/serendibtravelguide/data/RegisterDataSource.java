package com.watson.serendibtravelguide.data;

import android.util.Log;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.rest.UserApiService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterDataSource {

    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;


    public Result<User> register(String firstname, String lastname, String username, String email,String userType, ArrayList<String> telephone_number, String nic_num, ArrayList<String> guide_locations,String password) {
        User regiseter_user = new User();
        regiseter_user.setFirstname(firstname);
        regiseter_user.setLastname(lastname);
        regiseter_user.setUsername(username);
        regiseter_user.setEmail(email);
        regiseter_user.setUserType(userType);
        regiseter_user.setNic_num(nic_num);
        regiseter_user.setTelephone_number(telephone_number);
        regiseter_user.setGuide_locations(guide_locations);
        regiseter_user.setPassword(password);

        try {

            connectAndGetApiDataAWS(regiseter_user);
            return null;
//            // TODO: handle loggedInUser authentication
//            ArrayList<String> guide_locationss = new ArrayList<>();
//            guide_locations.add("Matara");
//            guide_locations.add("Dondra");
//            User fakeUser =
//                    new User(
//                            java.util.UUID.randomUUID().toString(),
//                            "Piyumi","Sudusinghe","piyumisudusinghe","piyumi@gmail.com","admin",Long.parseLong("0717872513"),"956731267v",guide_locationss,"12345678");
//            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error register in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }


    public User connectAndGetApiDataAWS(User user) {
        final User[] loggedUser = new User[1];
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

        Call<UserResponse> call = userApiService.saveUser(user);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {



                if(response.isSuccessful()) {
                    loggedUser[0] = response.body().getData();
                    Log.i(TAG, "post submitted to API." + loggedUser[0].getUsername() + loggedUser[0].getLastname());
                    Log.i(TAG, "post submitted to API.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                loggedUser[0] =  null;
            }
        });

        return loggedUser[0];
    }
}
