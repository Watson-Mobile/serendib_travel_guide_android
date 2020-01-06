package com.watson.serendibtravelguide.ui.search;

import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private String cardTitle;
    private String cardTag;
    private String otherNames;
    private String image;

    public SearchViewModel(String cardTitle, String cardTag,String otherNames, String image) {
        this.cardTitle = cardTitle;
        this.cardTag = cardTag;
        this.otherNames = otherNames;
        this.image=image;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardTag() {
        return cardTag;
    }

    public void setCardTag(String cardTag) {
        this.cardTag = cardTag;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
