package com.watson.serendibtravelguide.ui.dashboard;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Button btnTest;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
//        btnTest.setText("test activity launch");


    }

    public LiveData<String> getText() {
        return mText;
    }
}