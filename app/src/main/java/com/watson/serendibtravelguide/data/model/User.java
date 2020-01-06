package com.watson.serendibtravelguide.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.geojson.Point;

import java.util.ArrayList;

public class User {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("telephone_number")
    @Expose
    private ArrayList<String> telephone_number;
    @SerializedName("nic_num")
    @Expose
    private String nic_num;
    @SerializedName("guide_locations")
    @Expose
    private ArrayList<Point> guide_locations;
    @SerializedName("password")
    @Expose
    private String password;

    public User(){

    }

    public User(String userId, String firstname, String lastname, String username, String email, String userType, ArrayList<String> telephone_number, String nic_num, ArrayList<Point> guide_locations, String password) {
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

    public ArrayList<String> getTelephone_number() {
        return telephone_number;
    }

    public void setTelephone_number(ArrayList<String> telephone_number) {
        this.telephone_number = telephone_number;
    }

    public String getNic_num() {
        return nic_num;
    }

    public void setNic_num(String nic_num) {
        this.nic_num = nic_num;
    }

    public ArrayList<Point> getGuide_locations() {
        return guide_locations;
    }

    public void setGuide_locations(ArrayList<Point> guide_locations) {
        this.guide_locations = guide_locations;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


