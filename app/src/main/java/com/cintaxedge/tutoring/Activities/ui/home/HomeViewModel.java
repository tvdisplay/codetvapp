package com.cintaxedge.tutoring.Activities.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mTitle;

    public HomeViewModel() {
        mTitle = new MutableLiveData<>();
        mTitle.setValue("Home");
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }
}