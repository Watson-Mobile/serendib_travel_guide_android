package com.watson.serendibtravelguide.data.model;

import java.util.ArrayList;

public class User {

    private String userId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String userType;
    private long telephone_number;
    private String nic_num;
    private ArrayList<String> guide_locations;
    private String password;

    public User(String userId, String firstname, String lastname, String username, String email, String userType, long telephone_number, String nic_num, ArrayList<String> guide_locations, String password) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.userType = userType;
        this.telephone_number = telephone_number;
        this.nic_num = nic_num;
        this.guide_locations = guide_locations;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public long getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(long telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getNic_num() {
        return nic_num;
    }

    public void setNic_num(String nic_num) {
        this.nic_num = nic_num;
    }

    public ArrayList<String> getGuide_locations() {
        return guide_locations;
    }

    public void setGuide_locations(ArrayList<String> guide_locations) {
        this.guide_locations = guide_locations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


