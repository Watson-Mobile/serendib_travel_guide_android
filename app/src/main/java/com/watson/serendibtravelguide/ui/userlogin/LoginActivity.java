package com.watson.serendibtravelguide.ui.userlogin;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.data.model.UserResponse;
import com.watson.serendibtravelguide.rest.UserApiService;
import com.watson.serendibtravelguide.ui.home.HomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_DISTANCE;
import static com.watson.serendibtravelguide.config.Config.LOCATION_REFRESH_TIME;


public class LoginActivity extends AppCompatActivity {


    private LoginViewModel loginViewModel;
    public static final String BASE_URL_AWS = "http://ec2-34-238-82-190.compute-1.amazonaws.com/api/";
    private static Retrofit retrofit = null;
    private LoginActivity loginActivity = this;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public static User loggedUser;
    private LocationManager mLocationManager;
    private Point currentLocation;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
//            //your code here
//            Toast.makeText(getContext(), "OnLocationChanged", Toast.LENGTH_SHORT).show();
//            String longitude = "Longitude: " + location.getLongitude();
//            String latitude = "Latitude: " + location.getLatitude();
//            String s = longitude + "\n" + latitude;
//            Log.d(TAG, "location String : "+s);

            currentLocation = Point.fromLngLat(location.getLongitude(),location.getLatitude());
            Log.d(TAG, "Current Point : "+currentLocation);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        setContentView(R.layout.activity_login2);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

//        mLocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
//
//        if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//        }
//
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
//                LOCATION_REFRESH_DISTANCE, mLocationListener);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        loginViewModel.login(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                try {
                    User userLoggedIn = loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    if (userLoggedIn != null) {

                        editor.putBoolean("userLogged", true);
                        editor.putString("firstName", userLoggedIn.getFirstname());
                        editor.putString("lastName", userLoggedIn.getLastname());
                        editor.putString("email", userLoggedIn.getEmail());
                        editor.putString("username", userLoggedIn.getUsername());
                        editor.putString("userId", userLoggedIn.getUserId());
                        editor.putString("userType", userLoggedIn.getUserType());
                        if (userLoggedIn.getUserType() == "Local_Assistent") {
                            editor.putString("telephoneNumber", userLoggedIn.getTelephone_number().toString());
                            editor.putString("nicNumber", userLoggedIn.getNic_num());
                            editor.putFloat("logitude", 79.899963F);
                            editor.putFloat("langitude", 6.797072F);
                        }
                        editor.commit();

                        loggedUser = userLoggedIn;
                        Intent intent = new Intent(loginActivity, HomeActivity.class);
                        intent.putExtra("location_point",currentLocation);
                        //killing all other activities
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void updateUiWithUser(LoggedUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void connectAndGetLoginData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_AWS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        UserApiService userApiService = retrofit.create(UserApiService.class);

        List<User> placesOut = new ArrayList<>();

        Call<UserResponse> call = userApiService.getMockLoginSuccess();
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                User userData = response.body().getData();

                /* remove this part after API correctly added*/
                if (userData != null) {
                    //assume login is success
                    //change activity (LoginActivity => HomeActivity)
                    Intent intent = new Intent(loginActivity, HomeActivity.class);
                    //killing all other activities
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }


//                Log.d(TAG, "Login Response: " + userData.size());
//                Log.d("message","Incoming:" + response.body().getMessage());


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());

                //assume login is success
                //change activity (LoginActivity => HomeActivity)
                Intent intent = new Intent(loginActivity, HomeActivity.class);
                //killing all other activities
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });
    }
}