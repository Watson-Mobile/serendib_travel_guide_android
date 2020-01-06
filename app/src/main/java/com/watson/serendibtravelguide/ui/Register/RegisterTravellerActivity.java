package com.watson.serendibtravelguide.ui.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.watson.serendibtravelguide.R;

import java.util.ArrayList;

public class RegisterTravellerActivity extends AppCompatActivity {

    RegisterViewModel registerViewModel;
    Button traveller_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_traveller);

        registerViewModel = ViewModelProviders.of(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        traveller_register = (Button) findViewById(R.id.Register);

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


        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                nextButton.setEnabled(registerFormState.isDataValid());
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

        traveller_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tel_num = new ArrayList<>();
                tel_num.add(telephone_numberEditText.getText().toString());
                registerViewModel.register(firstnameEditText.getText().toString(),lastnameEditText.getText().toString(),usernameEditText.getText().toString(),
                        emailEditText.getText().toString(),"traveller",tel_num,nic_numberEditText.getText().toString(),null,
                        passwordEditText.getText().toString());
            }
        });


    }
}
