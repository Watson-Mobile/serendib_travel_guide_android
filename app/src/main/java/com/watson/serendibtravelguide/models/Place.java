package com.watson.serendibtravelguide.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mapbox.geojson.Point;


import java.util.ArrayList;
import java.util.List;

public class Place {

    @SerializedName("other_names")
    @Expose
    private List<String> otherNames = new ArrayList<String>();

    @SerializedName("type")
    @Expose
    private List<String> type = new ArrayList<String>();

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("location")
    @Expose
    private Point location;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("_id")
    @Expose
    private String id;

    public Place(List<String> otherNames, List<String> type, String name, Point location, String description,
                 String id) {
        this.otherNames = otherNames;
        this.type = type;
        this.name = name;
        this.location = location;
        this.description = description;
        this.id = id;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(List<String> otherNames) {
        this.otherNames = otherNames;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
