package com.watson.serendibtravelguide.data;

import android.os.StrictMode;
import android.util.Log;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserRegisterResponse;
import com.watson.serendibtravelguide.data.model.UserSubmit;
import com.watson.serendibtravelguide.rest.UserApiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterDataSource {

    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;

    public String registeringUserName ;

    public Result<UserSubmit> register(String[] telephone_number, String firstname, String lastname, String username, String email, String userType, String password, String[] guide_location, String nic_num) {
        UserSubmit regiseter_user = new UserSubmit();
        regiseter_user.setFirstname(firstname);
        regiseter_user.setLastname(lastname);
        regiseter_user.setUsername(username);
        regiseter_user.setEmail(email);
        regiseter_user.setUserType(userType);
        regiseter_user.setNic_num(nic_num);
        regiseter_user.setTelephone_number(telephone_number);
        regiseter_user.setGuide_location_submit(guide_location);
        regiseter_user.setPassword(password);


            try {

                connectAndGetApiDataAWS(telephone_number, firstname, lastname, username, email, userType, password, guide_location,nic_num);
                if (registeringUserName == null) {
                    Log.d(TAG, "i am in the datasource error");
                    return new Result.Error(new Exception("Error in register"));
                } else {
                    Log.d(TAG, "i am in the datasource success");
                    return new Result.Success<>(regiseter_user);
                }
            } catch (Exception e) {
                Log.d(TAG, "i am in the exception" + e);
                return new Result.Error(new Exception("Error in register"));
            }


        }

        public void logout () {
            // TODO: revoke authentication
        }


        public void connectAndGetApiDataAWS (String[]telephone_number, String firstname, String
        lastname, String username, String email, String userType, String password, String[]
        guide_location,String nic_num) throws IOException {

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL_AWS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            UserApiService userApiService = retrofit.create(UserApiService.class);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);


            Call<UserRegisterResponse> call = userApiService.saveUser(telephone_number, firstname, lastname, username, email, userType, password, guide_location,nic_num);

            //Call<UserResponse> call = userApiService.saveUser();
            registeringUserName = call.execute().body().getData();
            // Log.i(TAG,"registered"+call.execute().body().getMessage());


        }






}
