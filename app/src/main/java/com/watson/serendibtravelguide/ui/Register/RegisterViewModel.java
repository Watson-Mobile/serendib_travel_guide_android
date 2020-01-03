package com.watson.serendibtravelguide.ui.Register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.data.RegisterRepository;
import com.watson.serendibtravelguide.data.Result;
import com.watson.serendibtravelguide.data.model.User;


import java.util.ArrayList;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String firstname, String lastname, String username, String email, String userType, long telephone_number, String nic_num, ArrayList<String> guide_locations, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = registerRepository.register(firstname,lastname, username, email,userType,telephone_number,nic_num,guide_locations,password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            registerResult.setValue(new RegisterResult(new RegisteredUserView(data.getUsername())));
        } else {
            registerResult.setValue(new RegisterResult(R.string.login_failed));
        }
    }

    public void registerDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            if(Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                //call the database and check all user names
                return true;
            }else{
               return false;
            }

        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    // validate firstname
    private boolean isFirstnameValid(String firstname) {
        if (firstname == null) {
            return false;
        }
        return !firstname.trim().isEmpty();
    }

    // validate lastname
    private boolean isLastnameValid(String lastname) {
        if (lastname == null) {
            return false;
        }
        return !lastname.trim().isEmpty();
    }


}
