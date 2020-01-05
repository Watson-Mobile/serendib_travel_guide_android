package com.watson.serendibtravelguide.ui.Register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.watson.serendibtravelguide.data.LoginDataSource;
import com.watson.serendibtravelguide.data.LoginRepository;
import com.watson.serendibtravelguide.data.RegisterDataSource;
import com.watson.serendibtravelguide.data.RegisterRepository;

public class RegisterViewModelFactory implements ViewModelProvider.Factory{
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(RegisterRepository.getInstance(new RegisterDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

