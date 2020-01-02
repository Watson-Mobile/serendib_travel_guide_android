package com.watson.serendibtravelguide.ui.dashboard;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Button btnTest;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
//        btnTest.setText("test activity launch");


    }

    public LiveData<String> getText() {
        return mText;
    }
}