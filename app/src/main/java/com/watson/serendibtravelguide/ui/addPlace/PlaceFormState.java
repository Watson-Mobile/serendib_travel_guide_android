package com.watson.serendibtravelguide.ui.addPlace;

import androidx.annotation.Nullable;


public class PlaceFormState {
    @Nullable
    private Integer placenameError;
    @Nullable
    private Integer placeTypeError;

    @Nullable
    private Integer descriptionError;

    @Nullable
    private Integer locationError;
    private boolean isDataValid;

    PlaceFormState(@Nullable Integer placenameError, @Nullable Integer placeTypeError, @Nullable Integer descriptionError,@Nullable Integer locationError ) {
        this.placenameError = placenameError;
        this.placeTypeError = placeTypeError;
        this.locationError = locationError;
        this.descriptionError = descriptionError;
        this.isDataValid = false;
    }

    PlaceFormState(boolean isDataValid) {
        this.placenameError = null;
        this.locationError = null;
        this.placeTypeError = null;
        this.descriptionError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getPlacenameError() {
        return placenameError;
    }

    @Nullable
    Integer getPlaceTypeError() {
        return placeTypeError;
    }

    @Nullable
    Integer getLocationError(){
        return  locationError;
    }
    @Nullable
    Integer getDescriptionError() {
        return descriptionError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}