package com.watson.serendibtravelguide.ui.userlogin;

import androidx.annotation.Nullable;



public class LoginResult {
    @Nullable
    private LoggedUserView success;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
