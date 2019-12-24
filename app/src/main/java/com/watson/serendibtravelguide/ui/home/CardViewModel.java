package com.watson.serendibtravelguide.ui.home;

import android.media.Image;

public class CardViewModel {
    private String title;
    private int image;

    public CardViewModel(String title, int image) {
        this.title = title;
        this.image = image;
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
}
