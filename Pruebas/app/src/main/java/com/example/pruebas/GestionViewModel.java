package com.example.pruebas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GestionViewModel extends ViewModel {
    private MutableLiveData <String> mText;


    public GestionViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("Ejemplo geSTION");
    }
    public LiveData <String> getText() {return mText;}
}