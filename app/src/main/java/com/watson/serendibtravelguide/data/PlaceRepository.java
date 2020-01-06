package com.watson.serendibtravelguide.data;

import com.watson.serendibtravelguide.data.model.User;
import com.watson.serendibtravelguide.models.Place;

import java.io.IOException;

public class PlaceRepository {
    private static volatile PlaceRepository instance;

    private PlaceDataSource dataSource;

    // If User credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private PlaceRepository(PlaceDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static PlaceRepository getInstance(PlaceDataSource dataSource) {
        if (instance == null) {
            instance = new PlaceRepository(dataSource);
        }
        return instance;
    }

//    public boolean isLoggedIn() {
//        return user != null;
//    }

//    public void logout() {
//        user = null;
//        dataSource.logout();
//    }

//    private void setLoggedInUser(User user) {
//        this.user = user;
//        // If User credentials will be cached in local storage, it is recommended it be encrypted
//        // @see https://developer.android.com/training/articles/keystore
//    }

    public Result<Place> addPlace(Place place) throws IOException {
        // handle login
        Result<Place> result = dataSource.addPlace(place);
//        if (result instanceof Result.Success) {
//            setLoggedInUser(((Result.Success<User>) result).getData());
//        }
        return result;
    }
}
