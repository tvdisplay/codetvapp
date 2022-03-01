package com.cintaxedge.tutoring.Activities.ui.alltests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class AlltestsViewModel extends ViewModel {

    private MutableLiveData<String> mTitle;


    public AlltestsViewModel() {
        mTitle = new MutableLiveData<>();
        mTitle.setValue("All Tests");
    }

    public LiveData<String> getTitle() {
        return mTitle;
    }
}