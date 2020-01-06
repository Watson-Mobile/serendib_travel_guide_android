package com.watson.serendibtravelguide.ui.Register;

import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.data.RegisterRepository;
import com.watson.serendibtravelguide.data.Result;
import com.watson.serendibtravelguide.data.model.User;
import static androidx.constraintlayout.widget.Constraints.TAG;
import com.watson.serendibtravelguide.ui.userlogin.LoginFormState;


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

    public boolean register(String firstname, String lastname, String username, String email, String userType, ArrayList<String> telephone_number, String nic_num, ArrayList<String> guide_locations, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = registerRepository.register(firstname,lastname, username, email,userType,telephone_number,nic_num,guide_locations,password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            registerResult.setValue(new RegisterResult(R.string.register_success));
            return true;
        } else {
            registerResult.setValue(new RegisterResult(R.string.register_failed));
            return false;
        }
    }

    public void registerDataChanged(String firstname,String lastname, String username, String email, String NICNumber,String telephone_number,String password, String confirm_password) {
        if (!isFirstnameValid(firstname)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_firstname, null, null, null, null, null, null, null));
        } else if (!isLastnameValid(lastname)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_lastname, null, null, null, null, null, null));
        } else if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_username, null, null, null, null, null));
        } else if (!isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(null,null,null,R.string.invalid_email,null,null,null,null));
        } else if(!isTelephoneNumberValid(telephone_number)){
            registerFormState.setValue(new RegisterFormState(null,null,null,null,null,R.string.invalid_telephone_number,null,null));
        }else if(!isNICNumValid(NICNumber)){
            registerFormState.setValue(new RegisterFormState(null,null,null,null,R.string.invalid_NIC,null,null,null));
        }else if(!isPasswordValid(password)){
            registerFormState.setValue(new RegisterFormState(null,null,null,null,null,null,R.string.invalid_password,null));
        }else if(isConfirmPassowrdValid(confirm_password, password)){
            registerFormState.setValue(new RegisterFormState(null,null,null,null,null,null,null,R.string.invalid_confirm_password));
        }else{
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    //cofirm password validation
    private boolean isConfirmPassowrdValid(String confirm_password, String password){
        if(confirm_password == null){
            return false;
        }
        if(confirm_password != password){
            return false;
        }else{
            return true;
        }
    }

    // validate firstname
    private boolean isFirstnameValid(String firstname) {
        if (firstname == null) {
            return false;
        }
        return !firstname.trim().isEmpty();
    }

    // validate firstname
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    // validate lastname
    private boolean isLastnameValid(String lastname) {
        if (lastname == null) {
            return false;
        }
        return !lastname.trim().isEmpty();
    }

    private boolean isNICNumValid(String nic_num){
        if(nic_num.trim().length() == 10){
            return true;
        }else{
            return false;
        }

    }

    private boolean isTelephoneNumberValid(String telephone_number){
        if(telephone_number.isEmpty()){
            return false;
        }

        if(telephone_number.trim().length()!=10){
            return false;
        }else{
            return true;
        }
    }


}
