package com.watson.serendibtravelguide.ui.userprofile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class userprofilevViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public userprofilevViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is HomeActivity fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
