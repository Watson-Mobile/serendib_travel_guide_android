package com.watson.serendibtravelguide.ui.addPlace;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.watson.serendibtravelguide.data.PlaceDataSource;
import com.watson.serendibtravelguide.data.PlaceRepository;

class PlaceViewModelFactory implements ViewModelProvider.Factory{
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddPlaceViewModel.class)) {
            return (T) new AddPlaceViewModel(PlaceRepository.getInstance(new PlaceDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}


