package com.watson.serendibtravelguide.ui.addPlace;

import androidx.annotation.Nullable;

import com.watson.serendibtravelguide.ui.userlogin.LoggedUserView;

public class PlaceResult {
    @Nullable
    private LoggedUserView success;
    @Nullable
    private Integer error;

    PlaceResult(@Nullable Integer error) {
        this.error = error;
    }

    PlaceResult(@Nullable LoggedUserView success) {
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
