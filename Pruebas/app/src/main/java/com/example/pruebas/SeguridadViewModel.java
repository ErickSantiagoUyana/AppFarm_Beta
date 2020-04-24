package com.example.pruebas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeguridadViewModel extends ViewModel {
    private MutableLiveData <String> mText;


    public SeguridadViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Ejemplo");
    }
    public LiveData <String> getText() {return mText;}
}
