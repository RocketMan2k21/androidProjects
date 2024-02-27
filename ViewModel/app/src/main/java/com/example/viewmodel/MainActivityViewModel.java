package com.example.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends androidx.lifecycle.ViewModel {

    private int counter = 0;

    // create object from mutablelive data
    private MutableLiveData<Integer> countLiveData = new MutableLiveData<>();



    // When the app first launch
    public MutableLiveData<Integer> getInitialCount(){
        countLiveData.setValue(counter);
        return countLiveData;
    }

    // when the user clicks the button
    public void getCounter(){
        counter++;
        countLiveData.setValue(counter);
    }


}
