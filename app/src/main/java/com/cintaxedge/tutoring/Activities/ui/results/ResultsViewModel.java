package com.cintaxedge.tutoring.Activities.ui.results;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mtitle;

    public ResultsViewModel() {
        mText = new MutableLiveData<>();
        mtitle = new MutableLiveData<>();
        mtitle.setValue("Results");
        mText.setValue("This is results fragment");
    }

    public LiveData<String> getTitle(){return  mtitle;}
    public LiveData<String> getText() {
        return mText;
    }
}