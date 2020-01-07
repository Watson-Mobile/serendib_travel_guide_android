package com.watson.serendibtravelguide.data;


import com.mapbox.geojson.Point;
import com.watson.serendibtravelguide.data.model.User;

import java.util.ArrayList;

public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;


    private User user = null;

    // private constructor : singleton access
    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        // If User credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<User> register(String[] telephone_number,String firstname, String lastname, String username, String email, String userType, String password, String[] guide_location) {
        // handle login
        Result<User> result = dataSource.register(telephone_number,firstname,lastname,username,email,userType,password,guide_location);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }
}
