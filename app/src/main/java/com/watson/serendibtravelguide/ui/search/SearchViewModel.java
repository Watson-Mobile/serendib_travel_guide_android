package com.watson.serendibtravelguide.ui.search;

import android.widget.TextView;

import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private TextView cardTitle;
    private TextView cardTag;

    public SearchViewModel(TextView cardTitle, TextView cardTag) {
        this.cardTitle = cardTitle;
        this.cardTag = cardTag;
    }

    public TextView getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(TextView cardTitle) {
        this.cardTitle = cardTitle;
    }

    public TextView getCardTag() {
        return cardTag;
    }

    public void setCardTag(TextView cardTag) {
        this.cardTag = cardTag;
    }


}
