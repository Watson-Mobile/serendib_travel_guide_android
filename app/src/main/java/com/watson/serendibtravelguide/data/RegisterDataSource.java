package com.watson.serendibtravelguide.data;

import com.watson.serendibtravelguide.data.model.User;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterDataSource {


    public Result<User> register(String firstname, String lastname, String username, String email,String userType, long telephone_number, String nic_num, ArrayList<String> guide_locations,String password) {

        try {
            // TODO: handle loggedInUser authentication
            ArrayList<String> guide_locationss = new ArrayList<>();
            guide_locations.add("Matara");
            guide_locations.add("Dondra");
            User fakeUser =
                    new User(
                            java.util.UUID.randomUUID().toString(),
                            "Piyumi","Sudusinghe","piyumisudusinghe","piyumi@gmail.com","admin",Long.parseLong("0717872513"),"956731267v",guide_locationss,"12345678");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error register in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
