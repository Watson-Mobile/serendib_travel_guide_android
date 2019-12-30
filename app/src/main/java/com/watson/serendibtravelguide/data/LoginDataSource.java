package com.watson.serendibtravelguide.data;

import com.watson.serendibtravelguide.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class LoginDataSource {
    public Result<User> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            ArrayList<String> guide_locations = new ArrayList<>();
            guide_locations.add("Matara");
            guide_locations.add("Dondra");
            User fakeUser =
                    new User(
                            java.util.UUID.randomUUID().toString(),
                            "Piyumi","Sudusinghe","piyumisudusinghe","piyumi@gmail.com","admin",Long.parseLong("0717872513"),"956731267v",guide_locations,"12345678");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
