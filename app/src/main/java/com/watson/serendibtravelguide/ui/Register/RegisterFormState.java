package com.watson.serendibtravelguide.ui.Register;

import androidx.annotation.Nullable;

public class RegisterFormState {
    @Nullable
    private  Integer firstnameError;
    @Nullable
    private  Integer lastnameError;
    @Nullable
    private  Integer NICNumError;
    @Nullable
    private  Integer telNumError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private  Integer confirmPasswordError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer firstnameError, @Nullable Integer lastnameError,@Nullable Integer usernameError,@Nullable Integer emailError, @Nullable Integer NICNumError,@Nullable Integer telNumError,@Nullable Integer passwordError,@Nullable Integer confirmpasswordError) {
        this.firstnameError = firstnameError;
        this.lastnameError = lastnameError;
        this.usernameError = usernameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmpasswordError;
        this.NICNumError = NICNumError;
        this.telNumError = telNumError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.emailError = null;
        this.usernameError = null;
        this.passwordError = null;
        this.firstnameError = null;
        this.lastnameError = null;
        this.confirmPasswordError = null;
        this.NICNumError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getFirstnameError() {
        return firstnameError;
    }

    @Nullable
    Integer getLastnameError() {
        return lastnameError;
    }

    @Nullable
    Integer getTelNumError() {
        return telNumError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getNICNumError() {
        return NICNumError;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }




    boolean isDataValid() {
        return isDataValid;
    }
}
