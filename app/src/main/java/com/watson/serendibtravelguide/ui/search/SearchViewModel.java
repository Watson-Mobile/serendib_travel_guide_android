package com.watson.serendibtravelguide.ui.search;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private String cardTitle;
    private String cardTag;
   // private String

    public SearchViewModel(String cardTitle, String cardTag) {
        this.cardTitle = cardTitle;
        this.cardTag = cardTag;
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
}
