package com.watson.serendibtravelguide.ui.home;

public class CardViewModel {
    private String title;
    private int image;
    private String distance;
    private String type;

    public CardViewModel(String title, int image, String distance, String type) {
        this.title = title;
        this.image = image;
        this.distance = distance;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
