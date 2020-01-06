package com.watson.serendibtravelguide.ui.addPlace;

import android.graphics.Point;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.watson.serendibtravelguide.R;
import com.watson.serendibtravelguide.data.LoginRepository;
import com.watson.serendibtravelguide.data.PlaceRepository;
import com.watson.serendibtravelguide.data.Result;
import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.models.Place;
import com.watson.serendibtravelguide.ui.userlogin.LoggedUserView;
import com.watson.serendibtravelguide.ui.userlogin.LoginFormState;
import com.watson.serendibtravelguide.ui.userlogin.LoginResult;

import java.io.IOException;

public class AddPlaceViewModel extends ViewModel {

    private MutableLiveData<PlaceFormState> placeFormState = new MutableLiveData<>();
    private MutableLiveData<PlaceResult> placeResult = new MutableLiveData<>();
    private PlaceRepository placeRepository;

    AddPlaceViewModel(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    LiveData<PlaceFormState> getPlaceFormState() {
        return placeFormState;
    }

    LiveData<PlaceResult> getPlaceResult() {
        return placeResult;
    }

    public boolean addPlace(Place newPlace) throws IOException {
        // can be launched in a separate asynchronous job
        Result<Place> result = placeRepository.addPlace(newPlace);

        if (result instanceof Result.Success) {
            Place data = ((Result.Success<Place>) result).getData();
            return true;
            //placeResult.setValue(new PlaceResult(new LoggedUserView(data.getUsername())));

        } else {
           return false;

        }
    }

    public void placeDataChanged(String placename, String description, Point location, String placetype) {
        if (!isPlacenameValid(placename)) {
            placeFormState.setValue(new PlaceFormState(R.string.invalid_placename, null, null, null));
        } else if (!isDescriptionValid(description)) {
            placeFormState.setValue(new PlaceFormState(null, R.string.invalid_placedescription, null, null));
        } else if (!isLocationValid(location)) {
            placeFormState.setValue(new PlaceFormState(null, null, null, null));
        }else if(!isPlacetypeValid(placetype)){
            placeFormState.setValue(new PlaceFormState(null, R.string.invalid_placeType, null, null));
        }else {
            placeFormState.setValue(new PlaceFormState(true));
        }
    }

    // validate lastname
    private boolean isPlacenameValid(String placename) {
        if (placename == null) {
            return false;
        }
        return !placename.trim().isEmpty();
    }

    // validate lastname
    private boolean isDescriptionValid(String description) {
        if (description == null) {
            return false;
        }
        return !description.trim().isEmpty();
    }

    // validate lastname
    private boolean isLocationValid(Point location) {
        if (location == null) {
            return false;
        }
        return true;
    }

    // validate lastname
    private boolean isPlacetypeValid(String placetype) {
        if (placetype == null) {
            return false;
        }
        return true;
    }


}
