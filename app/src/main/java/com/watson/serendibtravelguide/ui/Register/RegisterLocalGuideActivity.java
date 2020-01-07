package com.watson.serendibtravelguide.ui.Register;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
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
import com.watson.serendibtravelguide.ui.Utils.LocationHandler;
import com.watson.serendibtravelguide.ui.userlogin.LoggedUserView;
import com.watson.serendibtravelguide.ui.userlogin.LoginActivity;
import com.watson.serendibtravelguide.ui.userlogin.LoginFormState;
import com.watson.serendibtravelguide.ui.userlogin.LoginResult;
import com.watson.serendibtravelguide.ui.userlogin.LoginViewModel;
import com.watson.serendibtravelguide.ui.userlogin.LoginViewModelFactory;

import java.util.ArrayList;

public class RegisterLocalGuideActivity extends AppCompatActivity {

    Button btn_nextPage;

    Bundle extras;

    public User loggedUser;
    private Point currentLocation;

    RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_local_guide);
        currentLocation = LocationHandler.defaultLocation;

        final EditText firstnameEditText = findViewById(R.id.firstname);
        final EditText lastnameEditText = findViewById(R.id.lastname);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText nic_numberEditText = findViewById(R.id.nic_no);
        final EditText telephone_numberEditText = findViewById(R.id.tel_no);
        final EditText  emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.register_pwd);
        final EditText confirm_passwordEditText = findViewById(R.id.register_con_pwd);
        final Button nextButton = findViewById(R.id.Next);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        extras = new Bundle();
        btn_nextPage = (Button)findViewById(R.id.Next);
        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extras.putString("firstname",firstnameEditText.getText().toString());
                extras.putString("lastname",lastnameEditText.getText().toString());
                extras.putString("username",usernameEditText.getText().toString());
                extras.putString("email",emailEditText.getText().toString());
                extras.putString("NICNumber",nic_numberEditText.getText().toString());
                ArrayList<String> tel_nums = new ArrayList<>();
                tel_nums.add(telephone_numberEditText.getText().toString());
                extras.putStringArrayList("telNumber",tel_nums);
                extras.putString("password",passwordEditText.getText().toString());
//                Intent intent = new Intent(RegisterLocalGuideActivity.this, MapsActivity.class);
//                intent.putExtras(extras);
//                startActivity(intent);

                currentLocation = LocationHandler.currentLocation;

                User newGuide = new User();
                newGuide.setFirstname(firstnameEditText.getText().toString());
                newGuide.setLastname(lastnameEditText.getText().toString());
                newGuide.setUsername(usernameEditText.getText().toString());
                newGuide.setEmail(emailEditText.getText().toString());
                newGuide.setPassword(passwordEditText.getText().toString());
                newGuide.setNic_num(nic_numberEditText.getText().toString());
                newGuide.setUserType("Local_Assistent");

                String[] telephone_number = new String[1];
                if (telephone_numberEditText.getText() != null) {
                    telephone_number[0]=(telephone_numberEditText.getText().toString());
                    newGuide.setTelephone_number(telephone_number);
                } else {
                    newGuide.setTelephone_number(null);
                }
                newGuide.setGuide_locations(currentLocation);
                String[] location = new String[2];
                location[0] = String.valueOf(newGuide.getGuide_locations().longitude());
                location[1] = String.valueOf(newGuide.getGuide_locations().latitude());
                if (registerViewModel.register(telephone_number, firstnameEditText.getText().toString(), lastnameEditText.getText().toString(), usernameEditText.getText().toString(),
                        emailEditText.getText().toString(), "Local_Assistent",
                        passwordEditText.getText().toString(), location, nic_numberEditText.getText().toString())) {
                    Log.d("RegiterGuide","success");
                    Intent intentLogin = new Intent(RegisterLocalGuideActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                }else{
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    Intent intentMainRegister = new Intent(RegisterLocalGuideActivity.this, MainRegisterActivity.class);
                    startActivity(intentMainRegister);
                }
            }
        });

        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);





        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                btn_nextPage.setEnabled(true);
                if (registerFormState.getFirstnameError() != null) {
                    firstnameEditText.setError(getString(registerFormState.getFirstnameError()));
                }
                if (registerFormState.getLastnameError() != null) {
                    lastnameEditText.setError(getString(registerFormState.getLastnameError()));
                }
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getNICNumError() != null) {
                    nic_numberEditText.setError(getString(registerFormState.getNICNumError()));
                }
                if (registerFormState.getTelNumError() != null) {
                    telephone_numberEditText.setError(getString(registerFormState.getTelNumError()));
                }
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getConfirmPasswordError() != null) {
                    confirm_passwordEditText.setError(getString(registerFormState.getConfirmPasswordError()));
                }

            }
        });

//        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
//            @Override
//            public void onChanged(@Nullable RegisterResult registerResult) {
//                if (registerResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (registerResult.getError() != null) {
//                    showRegisterFailed(registerResult.getError());
//                }
//                if (registerResult.getSuccess() != null) {
////                    updateUiWithUser(registerResult.getSuccess());
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });

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
                registerViewModel.registerDataChanged(firstnameEditText.getText().toString(),lastnameEditText.getText().toString(),usernameEditText.getText().toString(),emailEditText.getText().toString(),nic_numberEditText.getText().toString(), telephone_numberEditText.getText().toString(),
                        passwordEditText.getText().toString(),confirm_passwordEditText.getText().toString());
            }
        };

        firstnameEditText.addTextChangedListener(afterTextChangedListener);
        lastnameEditText.addTextChangedListener(afterTextChangedListener);
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        nic_numberEditText.addTextChangedListener(afterTextChangedListener);
        telephone_numberEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirm_passwordEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
//                }
//                return false;
//            }
//        });

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//            }
//        });
    }

//    private void updateUiWithUser(LoggedUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}
