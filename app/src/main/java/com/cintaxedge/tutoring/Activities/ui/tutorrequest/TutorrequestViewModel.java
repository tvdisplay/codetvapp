package com.cintaxedge.tutoring.Activities.ui.tutorrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TutorrequestViewModel extends ViewModel {

    private MutableLiveData<String> mtitle;
    public TutorrequestViewModel() {
        mtitle = new MutableLiveData<>();
        mtitle.setValue("Tutor Request");
    }

    public LiveData<String> getTitle() {
        return mtitle;
    }
}
