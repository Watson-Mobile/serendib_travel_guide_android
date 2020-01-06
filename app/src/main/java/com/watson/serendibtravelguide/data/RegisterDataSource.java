package com.watson.serendibtravelguide.data;

import android.os.StrictMode;
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
            if(connectAndGetApiDataAWS(regiseter_user)){
                return new Result.Success<> (regiseter_user);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error register in", e));
        }
        return new Result.Error(new IOException("Error register in"));
    }

    public void logout() {
        // TODO: revoke authentication
    }


    public boolean connectAndGetApiDataAWS(User user) throws IOException{

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        Log.d(TAG,"registering");
        Log.d(TAG,"NewUser:"+user.toString());
        Call<UserResponse> call = userApiService.saveUser(user);
        if(call.execute().isSuccessful()){
            Log.d(TAG,"register_success");
            return true;
        }
        return false;
    }
}
